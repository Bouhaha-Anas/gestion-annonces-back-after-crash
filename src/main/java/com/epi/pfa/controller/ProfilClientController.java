package com.epi.pfa.controller;

import java.util.Date;
import java.util.List;

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

import com.epi.pfa.model.Categorie;
import com.epi.pfa.model.Client;
import com.epi.pfa.model.Commande;
import com.epi.pfa.model.Compte;
import com.epi.pfa.model.Recommandation;
import com.epi.pfa.model.RecommandationPrimaryKey;
import com.epi.pfa.service.CategorieService;
import com.epi.pfa.service.ClientService;
import com.epi.pfa.service.CommandeService;
import com.epi.pfa.service.CompteService;
import com.epi.pfa.service.RecommandationService;

@RestController
public class ProfilClientController 
{
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private CompteService compteService;
	
	@Autowired
	private CategorieService categorieService;
	
	@Autowired
	private RecommandationService recommandationService;
	
	@Autowired
	private CommandeService commandeService;
	
	@RequestMapping( value= "/profilClient/informationsPersonnelles", method= RequestMethod.GET )
	public ModelAndView profilClientParametresGeneraux()
	{
		ModelAndView modelAndView = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Client client = clientService.findOneByCompte(compte);
		Date date = new Date();
		
		modelAndView.addObject("date", date);
		modelAndView.addObject("client", client);
		modelAndView.setViewName("profilClient");
		
		return modelAndView;
	}
	
	@RequestMapping( value="/profilClient/mesCommandes", method= RequestMethod.GET )
	public ModelAndView pageMesCommandes()
	{
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Client client = clientService.findOneByCompte(compte);
		List<Commande> mesCommandes = commandeService.findCommandeByClient(client.getId());
		modelAndView.addObject("mesCommandes", mesCommandes );
		modelAndView.setViewName("commandes");
		return modelAndView;
	}
	
	@RequestMapping( value= "/profilClient/mesRecommandations", method= RequestMethod.GET )
	public ModelAndView profilClientRecommandations()
	{
		ModelAndView modelAndView = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("AUTH : "+auth);
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Client client = clientService.findOneByCompte(compte);
		
		List<Categorie> mesFavoris = categorieService.findRecommanded(client.getId());
		List<Categorie> remainCategories = categorieService.findNotYetRecommanded(client.getId());
		
		modelAndView.addObject("remainCategories", remainCategories);
		modelAndView.addObject("mesFavoris", mesFavoris);
		modelAndView.addObject("client", client);
		modelAndView.setViewName("recommandations");
		
		return modelAndView;
	}
	
	@RequestMapping( value= "/profilClient/mesRecommandations", method= RequestMethod.POST )
	public ModelAndView profilClientRecommandationsAjout(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Client client = clientService.findOneByCompte(compte);
		
		String[] categories =  request.getParameterValues("cats");
		
		for(int i=0; i < categories.length; i++)
		{
			Categorie categorie = categorieService.findOneByNom(categories[i]);
			
			RecommandationPrimaryKey recommandationPrimaryKey = new RecommandationPrimaryKey();
			recommandationPrimaryKey.setIdCategorie(categorie.getId());
			recommandationPrimaryKey.setIdClient(client.getId());
			
			Recommandation recommandation = new Recommandation();		
			recommandation.setRecommandationPrimaryKey(recommandationPrimaryKey);
			recommandation.setClient(client);
			recommandation.setCategorie(categorie);
			recommandationService.addRecommandation(recommandation);		
		}
		
		modelAndView.setViewName("recommandations");
		
		return modelAndView;
	}
	
	@RequestMapping( value= "/profilClient/mesRecommandations", method= RequestMethod.DELETE )
	public ModelAndView profilClientRecommandationsSupprimer(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Client client = clientService.findOneByCompte(compte);
		
		String[] remoCat =  request.getParameterValues("remoCat");
		
		for( int i=0; i < remoCat.length; i++ )
		{
			Categorie categorie = categorieService.findOneByNom(remoCat[i]);
			recommandationService.deleteRecommandation(client.getId(), categorie.getId());
		}
		
		modelAndView.setViewName("recommandations");
		
		return modelAndView;
	}
	
	@RequestMapping( value="/profilClient/informationsPersonnelles/modification", method= RequestMethod.GET )
	public ModelAndView pageModifierProfilClient()
	{
		ModelAndView modelAndView = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Client client = clientService.findOneByCompte(compte);
		
		modelAndView.addObject("client", client );		
		modelAndView.setViewName("profilClient");
		
		return modelAndView;
	}
	
	@RequestMapping( value="/profilClient/informationsPersonnelles/modification", method= RequestMethod.PUT )
	public ModelAndView modifierProfilClient( Client client, HttpServletRequest request) throws ServletException
	{
		ModelAndView modelAndView = new ModelAndView();
		String mdp = request.getParameter("mdp");
		
		if( mdp.equals(client.getCompte().getMotDePasse()) )
		{
			clientService.updateClient(client);
			String successMessage = "Vos informations sont mises à jour avec succés";
			modelAndView.addObject("successMessage", successMessage );
		}
		else
		{
			String errorMessage = "Vérifier votre mot de passe saisi";
			modelAndView.addObject("errorMessage", errorMessage );
		}		
		modelAndView.setViewName("profilClient");
		
		return modelAndView;
	}
}
