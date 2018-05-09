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
import com.epi.pfa.model.Produit;
import com.epi.pfa.service.CategorieService;
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
	
	
	
	@RequestMapping( value= "/accueil", method = RequestMethod.GET )
	public ModelAndView accueil(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView();
		
		List<Categorie> categories = categorieService.findAllCategories();		
		servletContext.setAttribute("categories", categories);
		
		List<Produit> produitsActifs = produitService.getAllActiveProducts();
		List<Produit> produitsPasses = produitService.getAllPassedProducts();
		
		modelAndView.addObject("produitsActifs", produitsActifs);
		modelAndView.addObject("produitsPasses", produitsPasses);
		modelAndView.setViewName("accueil");
		
		return modelAndView;
	}
	
	@RequestMapping( value= "/accueil/resultatRecherche", method = RequestMethod.GET )
	public ModelAndView accueilSearch(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView();
		String nomP = request.getParameter("nomP");
		String nomC = request.getParameter("nomC");
		List<Produit> rechercheProduits = produitService.searchByCategorie(nomP, nomC);
		System.out.println("RESULTAAAT : "+rechercheProduits);
		List<Produit> produitsActifs = produitService.getAllActiveProducts();
		List<Produit> produitsPasses = produitService.getAllPassedProducts();
		
		modelAndView.addObject("rechercheProduits", rechercheProduits);
		modelAndView.addObject("produitsActifs", produitsActifs);
		modelAndView.addObject("produitsPasses", produitsPasses);
		modelAndView.setViewName("accueil");
		
		return modelAndView;
	}
}
