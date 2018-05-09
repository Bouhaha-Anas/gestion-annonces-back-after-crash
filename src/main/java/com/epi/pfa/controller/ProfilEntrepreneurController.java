package com.epi.pfa.controller;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epi.pfa.model.Compte;
import com.epi.pfa.model.Entrepreneur;
import com.epi.pfa.service.CompteService;
import com.epi.pfa.service.EntrepreneurService;

@RestController
public class ProfilEntrepreneurController 
{
	
	@Autowired
	EntrepreneurService entrepreneurService;
	
	@Autowired
	CompteService compteService;
	
	@RequestMapping( value= "/profilEntrepreneur/informationsPersonnelles", method= RequestMethod.GET )
	public ModelAndView profilEntrepreneurParametresGeneraux()
	{
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Entrepreneur entrepreneur = entrepreneurService.findOneByCompte(compte);
		Date date = new Date();
		
		modelAndView.addObject("date", date);
		modelAndView.addObject("entrepreneur", entrepreneur);
		modelAndView.setViewName("profilEntrepreneur");
		
		return modelAndView;
	}
	
	@RequestMapping( value="/profilEntrepreneur/informationsPersonnelles/modification", method= RequestMethod.GET )
	public ModelAndView pageModifierProfilEntrepreneur()
	{
		ModelAndView modelAndView = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Entrepreneur entrepreneur = entrepreneurService.findOneByCompte(compte);
		
		modelAndView.addObject("entrepreneur", entrepreneur );		
		modelAndView.setViewName("profilEntrepreneur");
		
		return modelAndView;
	}
	
	@RequestMapping( value="/profilEntrepreneur/informationsPersonnelles/modification", method= RequestMethod.PUT )
	public ModelAndView modifierProfilEntrepreneur( Entrepreneur entrepreneur, HttpServletRequest request) throws ServletException, ParseException
	{
		ModelAndView modelAndView = new ModelAndView();
		String mdp = request.getParameter("mdp");
		
		if( mdp.equals(entrepreneur.getCompte().getMotDePasse()) )
		{
			entrepreneurService.updateEntrepreneur(entrepreneur);
			String successMessage = "Vos informations sont mises à jour avec succés";
			modelAndView.addObject("successMessage", successMessage );		
		}
		else
		{
			String errorMessage = "Vérifier votre mor de passe.";
			modelAndView.addObject("errorMessage", errorMessage );
		}
		
		modelAndView.setViewName("profilEntrepreneur");
		return modelAndView;
	}
}
