package com.epi.pfa.controller;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epi.pfa.model.Categorie;
import com.epi.pfa.model.Compte;
import com.epi.pfa.model.Entrepreneur;
import com.epi.pfa.model.Produit;
import com.epi.pfa.service.CategorieService;
import com.epi.pfa.service.CompteService;
import com.epi.pfa.service.EntrepreneurService;
import com.epi.pfa.service.ProduitService;
import com.epi.pfa.utilities.UploadingTask;

@RestController
public class ProduitController 
{
	
	public static final String CHEMIN_FICHIERS_PRODUITS = "D:/Ingenieurie/Semestre2/PFA/Work-Space/gestion-annonces/src/main/resources/static/images/uploaded-images/images-produits/";
	
	@Autowired
	private ProduitService produitService;
	
	@Autowired
	EntrepreneurService entrepreneurService;
	
	@Autowired
	CategorieService categorieService;
	
	@Autowired
	CompteService compteService;
	
	
	@RequestMapping( value="/nouvelleOffre", method = RequestMethod.GET )
	public ModelAndView pageAjoutPromotion()
	{
		ModelAndView modelAndView = new ModelAndView();
		
		Produit produit = new Produit();
		
		modelAndView.addObject("produit", produit);
		modelAndView.setViewName("nouvelleOffre");
		
		return modelAndView;
	}
	
	@RequestMapping( value="/nouvelleOffre", method = RequestMethod.POST )
	public ModelAndView ajoutPromotion(Produit produit, HttpServletRequest request) throws ServletException, IOException
	{
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = null;
		String successMessage = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Entrepreneur entrepreneur = entrepreneurService.findOneByCompte(compte);
		
		String choixCategorie = request.getParameter("choixCategorie");
		String date = request.getParameter("dateFinS");
		
		Categorie categorie = categorieService.findOneByNom(choixCategorie);
		Date dateFin = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			dateFin = sdf.parse(date);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		Part part = request.getPart("imageP");
		String nomFichier = UploadingTask.getNomFichier(part);
		if (nomFichier != null && !nomFichier.isEmpty()) 
		{	   
            nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1).substring(nomFichier.lastIndexOf('\\') + 1);
            UploadingTask.ecrireFichier(part, nomFichier, CHEMIN_FICHIERS_PRODUITS);
            
            produit.setImage(nomFichier);
            produit.setEstActive(true);
    		produit.setDateFin(dateFin);
    		produit.setDateDebut(new Date());
    		produit.setCategorie(categorie);
    		produit.setEntrepreneur(entrepreneur);
    		produitService.addProduit(produit);
    		successMessage = "Votre promotion est ajouté avec succés.";
        }
		else
		{
			errorMessage = "Veuillez choisir une photo pour votre offre.";
		}
		
		modelAndView.addObject("successMessage", successMessage);
		modelAndView.addObject("errorMessage", errorMessage);
		modelAndView.setViewName("nouvelleOffre");
		return modelAndView;
	}
	
	@RequestMapping( value="/offre/{id}", method= RequestMethod.GET )
	public ModelAndView getProduitDetails(@PathVariable("id") Long id)
	{
		ModelAndView modelAndView = new ModelAndView();
		Produit produit = produitService.getProduit(id);
		
		modelAndView.addObject("produit", produit);
		modelAndView.setViewName("offreDetails");
		
		return modelAndView;
	}
		
}
