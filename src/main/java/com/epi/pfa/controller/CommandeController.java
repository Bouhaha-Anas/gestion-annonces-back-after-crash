package com.epi.pfa.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epi.pfa.model.Client;
import com.epi.pfa.model.Commande;
import com.epi.pfa.model.Compte;
import com.epi.pfa.model.Produit;
import com.epi.pfa.service.ClientService;
import com.epi.pfa.service.CommandeService;
import com.epi.pfa.service.CompteService;
import com.epi.pfa.service.ProduitService;


@RestController
public class CommandeController 
{
	@Autowired
	private CompteService compteService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ProduitService produitService;
	
	@Autowired
	private CommandeService commandeService;
	
	@RequestMapping( value="/validerCommande/promotion/{id}", method= RequestMethod.GET )
	public ModelAndView pageValiderCommande(@PathVariable("id") Long id)
	{
		ModelAndView modelAndView = new ModelAndView();
		Produit produit = produitService.getProduit(id);
		
		modelAndView.addObject("produit", produit);
		modelAndView.setViewName("validerCommande");
		return modelAndView;
	}
	
	@RequestMapping( value="/validerCommande/promotion/{id}", method= RequestMethod.POST )
	public ModelAndView validerCommande( @PathVariable("id") Long id , HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Client client = clientService.findOneByCompte(compte);
		Produit produit = produitService.getProduit(id);
		int quantite = Integer.parseInt(request.getParameter("quantite"));
		
		Commande commande = new Commande();
		
		commande.setQuantite(quantite);
		commande.setDateCommande(new Date());
		commande.setClient(client);
		commande.setProduit(produit);
		commandeService.addCommande(commande);
		
		modelAndView.addObject("successMessage", "Votre commande est effectuée avec succés" );
		modelAndView.setViewName("accueil");
		return modelAndView;
	}
}
