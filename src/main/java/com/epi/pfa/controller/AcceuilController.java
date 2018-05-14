package com.epi.pfa.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epi.pfa.model.Categorie;
import com.epi.pfa.model.Entrepreneur;
import com.epi.pfa.model.Produit;
import com.epi.pfa.service.CategorieService;
import com.epi.pfa.service.ClientService;
import com.epi.pfa.service.CommandeService;
import com.epi.pfa.service.EntrepreneurService;
import com.epi.pfa.service.ProduitService;

@RestController
public class AcceuilController 
{
	@Autowired
	private CategorieService categorieService;
	
	@Autowired
	private ProduitService produitService;
	
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	private EntrepreneurService entrepreneurService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private CommandeService commandeService;
	
	@RequestMapping( value= "/accueil", method = RequestMethod.GET )
	public ModelAndView accueil(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView();
		
		List<Categorie> categories = categorieService.findAllCategories();		
		servletContext.setAttribute("categories", categories);
		List<Produit> produitsActifs = produitService.getAllActiveProducts();
		List<Produit> produitsPasses = produitService.getAllPassedProducts();
		List<Entrepreneur> entrepreneurs = entrepreneurService.findAll();
		int totalEntrepreneur = entrepreneurService.totalEntrepreneurs();
		int totalClients = clientService.totalClients();
		int totalProduits = produitService.totalProduits();
		int totalCommandes = commandeService.totalCommandes();
		
		modelAndView.addObject("totalCommandes", totalCommandes);
		modelAndView.addObject("totalProduits", totalProduits);
		modelAndView.addObject("totalClients", totalClients);
		modelAndView.addObject("totalEntrepreneur", totalEntrepreneur);
		modelAndView.addObject("entrepreneurs", entrepreneurs);
		modelAndView.addObject("produitsActifs", produitsActifs);
		modelAndView.addObject("produitsPasses", produitsPasses);
		modelAndView.setViewName("accueil");
		
		return modelAndView;
	}	
}
