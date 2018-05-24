package com.epi.pfa.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epi.pfa.model.Compte;
import com.epi.pfa.model.Entrepreneur;
import com.epi.pfa.model.Produit;
import com.epi.pfa.service.CompteService;
import com.epi.pfa.service.EntrepreneurService;
import com.epi.pfa.service.ProduitService;


@RestController
public class ProfilEntrepreneurController 
{
	
	@Autowired
	EntrepreneurService entrepreneurService;
	
	@Autowired
	CompteService compteService;
	
	@Autowired
	private ProduitService produitService;
	
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
		Compte compte = compteService.findOneButNotMe(entrepreneur.getCompte().getLogin(), entrepreneur.getCompte().getId());
		
		if(compte == null)
		{
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
		}
		else
		{
			String errorMessage = "Le nom d'utilisateur existe déjà, réessayer";
			modelAndView.addObject("errorMessage", errorMessage );
		}
		
		modelAndView.setViewName("profilEntrepreneur");
		return modelAndView;
	}
	
	@RequestMapping( value= "/profilEntrepreneur/mesOffres", method= RequestMethod.GET )
	public ModelAndView profilEntrepreneurMesOffres()
	{
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Entrepreneur entrepreneur = entrepreneurService.findOneByCompte(compte);
		List<Produit> produitsActifs = produitService.findAllActivatedByEntrepreneur(entrepreneur.getId());
		List<Produit> produitsNonActifs = produitService.findAllDisactivatedByEntrepreneur(entrepreneur.getId());
		modelAndView.addObject("produitsNonActifs", produitsNonActifs);
		modelAndView.addObject("produitsActifs", produitsActifs);
		modelAndView.setViewName("mesOffres");
		
		return modelAndView;
	}
	
	@RequestMapping( value= "/modification/offre/{id}", method= RequestMethod.GET )
	public ModelAndView pageModificationOffre(@PathVariable("id") Long id)
	{
		ModelAndView modelAndView = new ModelAndView();
		Produit produit = produitService.getProduit(id);
		modelAndView.addObject("produit", produit);
		modelAndView.setViewName("modificationOffre");
		
		return modelAndView;
	}
	
	@RequestMapping( value= "/modification/offre/{id}", method= RequestMethod.PUT ) 
	public ModelAndView modificationOffre(Produit produit, HttpServletRequest request) throws IOException
	{
		ModelAndView modelAndView = new ModelAndView();
		String date = request.getParameter("dateFinS");
		String dateDeb = request.getParameter("dateDeb");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFin = null;
		Date dateDebut = null;
		try
		{
			dateFin = sdf.parse(date);
			dateDebut = sdf.parse(dateDeb);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		if( dateFin.after(new Date()) == true )
		{
			produit.setDateFin(dateFin);
			produit.setDateDebut(dateDebut);
			produitService.updateProduit(produit);
			modelAndView.addObject("successMessage", "Votre offre est modifiée avec succès !");
		}
		else
		{
			modelAndView.addObject("errorMessage", "La date d'échéance doit être supérieure à la date actuelle !");
			
		}

		modelAndView.setViewName("modificationOffre");	
		return modelAndView;
	}
	
	@RequestMapping( value= "/desactiverOffre/{id}", method= RequestMethod.PUT )
	public void desactivationOffre(@PathVariable("id") Long id, HttpServletResponse response) throws IOException
	{
		Produit produit = produitService.getProduit(id);
		produit.setEstActive(false);
		produitService.updateProduit(produit);
		response.sendRedirect("/profilEntrepreneur/mesOffres");
	}
	
	@RequestMapping( value= "/entrepreneur/{id}", method= RequestMethod.GET )
	public ModelAndView profilEntrepreneur(@PathVariable("id") Long id)
	{
		ModelAndView modelAndView = new ModelAndView();
		Entrepreneur entrepreneur = entrepreneurService.getEntrepreneur(id);
		Date date = new Date();
		
		modelAndView.addObject("date", date);
		modelAndView.addObject("entrepreneur", entrepreneur);
		modelAndView.setViewName("entrepreneur");
		
		return modelAndView;
	}
}
