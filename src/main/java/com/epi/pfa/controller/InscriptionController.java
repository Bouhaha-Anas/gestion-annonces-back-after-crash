package com.epi.pfa.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.epi.pfa.model.Client;
import com.epi.pfa.model.Compte;
import com.epi.pfa.model.Entrepreneur;
import com.epi.pfa.model.VerificationToken;
import com.epi.pfa.repository.VerificationTokenRepository;
import com.epi.pfa.service.ClientService;
import com.epi.pfa.service.CompteService;
import com.epi.pfa.service.EntrepreneurService;
import com.epi.pfa.utilities.OnRegistrationCompleteEvent;
import com.epi.pfa.utilities.UploadingTask;

@RestController
public class InscriptionController 
{
	public static final String CHEMIN_FICHIERS_CLIENTS = "D:/Ingenieurie/Semestre2/PFA/Work-Space/gestion-annonces/src/main/resources/static/images/uploaded-images/images-clients/";
	public static final String CHEMIN_FICHIERS_ENTREPRENEURS = "D:/Ingenieurie/Semestre2/PFA/Work-Space/gestion-annonces/src/main/resources/static/images/uploaded-images/images-entrepreneurs/";
	public static final String CHEMIN_FICHIERS_CLIENTS_BACK_OFFICE = "D:/Ingenieurie/Semestre2/PFA/Work-Space/gestion-annonces-back-office/src/main/resources/static/uploaded-images/images-clients/";
	public static final String CHEMIN_FICHIERS_ENTREPRENEURS_BACK_OFFICE = "D:/Ingenieurie/Semestre2/PFA/Work-Space/gestion-annonces-back-office/src/main/resources/static/uploaded-images/images-entrepreneurs/";
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	EntrepreneurService entrepreneurService;
	
	@Autowired
	ApplicationEventPublisher applicationEventPublisher;
	
	@Autowired
	VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	CompteService compteService;
	
	@RequestMapping( value="/inscription", method= RequestMethod.GET )
	public ModelAndView inscription()
	{
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("inscription");
		
		return modelAndView;
	}
	
	@RequestMapping( value="/inscriptionSuccess", method= RequestMethod.GET )
	public ModelAndView inscriptionSuccess()
	{
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("confirmationInscription");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/inscriptionConfirm", method = RequestMethod.GET)
	public ModelAndView confirmRegistration(WebRequest request, @RequestParam("token") String token) 
	{
	    VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
	     
	    Compte compte = verificationToken.getCompte();
	    compte.setEnabled(true);
	    
	    compteService.updateCompte(compte);

	  
	    return new ModelAndView("login");
	}
	
	
	
	
	@RequestMapping( value="/inscriptionClient", method= RequestMethod.GET )
	public ModelAndView inscriptionClient()
	{
		ModelAndView modelAndView = new ModelAndView();
		
		Client client = new Client();
		
		modelAndView.addObject("client", client);
		modelAndView.setViewName("inscription");
		
		return modelAndView;
	}
	
//	@RequestMapping( value="/inscriptionClient", method= RequestMethod.POST )
//	public ModelAndView addClient( Client client, WebRequest request, HttpServletRequest req) throws ServletException,IOException
//	{
//		ModelAndView modelAndView = new ModelAndView();
//		String errorMessage = null;
//		Client tempClient = clientService.findOneByAdresseMail(client.getAdresseMail());
//		String mdp = req.getParameter("mdp");
//		
//		if(tempClient != null)
//		{
//			errorMessage = "L'adresse Mail est déjà utilisée, veuillez réessayer";
//			modelAndView.setViewName("inscription");
//			modelAndView.addObject("client", new Client());
//			modelAndView.addObject("errorMessage", errorMessage);
//			return modelAndView;
//		}
//		else
//		{
//			if( mdp.equals(client.getCompte().getMotDePasse()) )
//			{
//				Part part = req.getPart("imageC");
//				String nomFichier = UploadingTask.getNomFichier(part);
//				if (nomFichier != null && !nomFichier.isEmpty()) 
//				{	   
//		            nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1).substring(nomFichier.lastIndexOf('\\') + 1);
//		            UploadingTask.ecrireFichier(part, nomFichier, CHEMIN_FICHIERS_CLIENTS);
//		            client.setImage(nomFichier);
//		            client.getCompte().setEnabled(false);
//		            clientService.addClient(client);
//					try
//					{
//						String appUrl = "inscriptionClient";
//						applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(client.getCompte(), request.getLocale(), appUrl) );
//						modelAndView.setViewName("confirmationInscription");				
//					}
//					catch(Exception e)
//					{
//						errorMessage = "Erreur lors de l'envoi de l'email d'activation, veuillez réessayer";
//						clientService.deleteClient(client.getId());
//						modelAndView.addObject("errorMessage", errorMessage);
//						modelAndView.addObject("client", new Client());
//						modelAndView.setViewName("inscription");
//						e.printStackTrace();
//					}
//					return modelAndView;
//		        }
//				else
//				{
//					errorMessage = "Veuillez choisir une photo de votre profil.";
//					modelAndView.addObject("errorMessage", errorMessage);
//					modelAndView.addObject("client", new Client());
//					modelAndView.setViewName("inscription");
//					return modelAndView;
//				}
//			}
//			else
//			{
//				errorMessage = "Vérifier votre mot de passe.";
//				modelAndView.addObject("errorMessage", errorMessage);
//				modelAndView.addObject("client", new Client());
//				modelAndView.setViewName("inscription");
//				return modelAndView;
//			}
//		}
//	}
	
	
	@RequestMapping( value="/inscriptionClient", method= RequestMethod.POST )
	public ModelAndView addClient( Client client, WebRequest request, HttpServletRequest req) throws ServletException,IOException
	{
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = null;
		String mdp = req.getParameter("mdp");
		Compte compte = compteService.findOneByLogin(client.getCompte().getLogin());
		
		if(compte == null)
		{
			if( mdp.equals(client.getCompte().getMotDePasse()) )
			{
				Part part = req.getPart("imageC");
				String nomFichier = UploadingTask.getNomFichier(part);
				if (nomFichier != null && !nomFichier.isEmpty()) 
				{	   
		            nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1).substring(nomFichier.lastIndexOf('\\') + 1);
		            UploadingTask.ecrireFichier(part, nomFichier, CHEMIN_FICHIERS_CLIENTS);
		            UploadingTask.ecrireFichier(part, nomFichier, CHEMIN_FICHIERS_CLIENTS_BACK_OFFICE);
		            client.setImage(nomFichier);
		            client.getCompte().setEnabled(false);
		            clientService.addClient(client);
					try
					{
						String appUrl = "inscriptionClient";
						applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(client.getCompte(), request.getLocale(), appUrl) );
						modelAndView.setViewName("confirmationInscription");				
					}
					catch(Exception e)
					{
						errorMessage = "Erreur lors de l'envoi de l'email d'activation, veuillez réessayer";
						clientService.deleteClient(client.getId());
						modelAndView.addObject("errorMessage", errorMessage);
						modelAndView.addObject("client", new Client());
						modelAndView.setViewName("inscription");
						e.printStackTrace();
					}
					return modelAndView;
		        }
				else
				{
					errorMessage = "Veuillez choisir une photo de votre profil.";
					modelAndView.addObject("errorMessage", errorMessage);
					modelAndView.addObject("client", new Client());
					modelAndView.setViewName("inscription");
					return modelAndView;
				}
			}
			else
			{
				errorMessage = "Vérifier votre mot de passe.";
				modelAndView.addObject("errorMessage", errorMessage);
				modelAndView.addObject("client", new Client());
				modelAndView.setViewName("inscription");
				return modelAndView;
			}
		}
		else
		{
			errorMessage = "Le nom d'utilisateur existe déjà, réessayer";
			modelAndView.addObject("errorMessage", errorMessage);
			modelAndView.addObject("client", new Client());
			modelAndView.setViewName("inscription");
			return modelAndView;
		}
	}
	
	@RequestMapping( value="/inscriptionEntrepreneur", method= RequestMethod.GET )
	public ModelAndView inscriptionEntrepreneur()
	{
		ModelAndView modelAndView = new ModelAndView();
		
		Entrepreneur entrepreneur = new Entrepreneur();
		
		modelAndView.addObject("entrepreneur", entrepreneur);
		modelAndView.setViewName("inscription");
		
		return modelAndView;
	}
	
//	@RequestMapping( value="/inscriptionEntrepreneur", method= RequestMethod.POST ) 
//	public ModelAndView addEntrepreneur( Entrepreneur entrepreneur, WebRequest request, HttpServletRequest req) throws ServletException,IOException
//	{
//		ModelAndView modelAndView = new ModelAndView();
//		String errorMessage = null;
//		String mdp = req.getParameter("mdp");
//		Entrepreneur tempEntrepreneur = entrepreneurService.findOneByAdresseMail(entrepreneur.getAdresseMail());
//
//		if(tempEntrepreneur != null)
//		{
//			errorMessage = "L'adresse Mail est déjà utilisée, veuillez réessayer";
//			modelAndView.setViewName("inscription");
//			modelAndView.addObject("entrepreneur", new Entrepreneur());
//			modelAndView.addObject("errorMessage", errorMessage);
//			return modelAndView;
//		}
//		else
//		{
//			if( mdp.equals(entrepreneur.getCompte().getMotDePasse()) )
//			{
//				Part part = req.getPart("logoE");
//				String nomFichier = UploadingTask.getNomFichier(part);
//				if (nomFichier != null && !nomFichier.isEmpty()) 
//				{	   
//		            nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1).substring(nomFichier.lastIndexOf('\\') + 1);
//		            UploadingTask.ecrireFichier(part, nomFichier, CHEMIN_FICHIERS_ENTREPRENEURS);
//		            entrepreneur.setLogo(nomFichier);
//		            entrepreneur.getCompte().setEnabled(false);
//					entrepreneurService.addEntrepreneur(entrepreneur);
//					try
//					{
//						String appUrl = "inscriptionEntrepreneur";
//						applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(entrepreneur.getCompte(), request.getLocale(), appUrl) );
//						modelAndView.setViewName("confirmationInscription");			
//					}
//					catch(Exception e)
//					{
//						entrepreneurService.deleteEntrepreneur(entrepreneur.getId());
//						errorMessage = "Erreur lors de l'envoi de l'email d'activation, veuillez réessayer";
//						modelAndView.addObject("errorMessage", errorMessage);
//						modelAndView.addObject("entrepreneur", new Entrepreneur());
//						modelAndView.setViewName("inscription");
//						e.printStackTrace();
//					}
//					return modelAndView;
//		        }
//				else
//				{
//					errorMessage = "Veuillez choisir un logo pour votre entreprise.";
//					modelAndView.addObject("errorMessage", errorMessage);
//					modelAndView.addObject("entrepreneur", new Entrepreneur());
//					modelAndView.setViewName("inscription");
//					return modelAndView;
//				}
//			}
//			else
//			{
//				errorMessage = "Vérifier votre mot de passe.";
//				modelAndView.addObject("errorMessage", errorMessage);
//				modelAndView.addObject("entrepreneur", new Entrepreneur());
//				modelAndView.setViewName("inscription");
//				return modelAndView;
//			}
//		}
//	}
	
	@RequestMapping( value="/inscriptionEntrepreneur", method= RequestMethod.POST ) 
	public ModelAndView addEntrepreneur( Entrepreneur entrepreneur, WebRequest request, HttpServletRequest req) throws ServletException,IOException
	{
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = null;
		String mdp = req.getParameter("mdp");
		Compte compte = compteService.findOneByLogin(entrepreneur.getCompte().getLogin());
		
		if(compte == null)
		{
			if( mdp.equals(entrepreneur.getCompte().getMotDePasse()) )
			{
				Part part = req.getPart("logoE");
				String nomFichier = UploadingTask.getNomFichier(part);
				if (nomFichier != null && !nomFichier.isEmpty()) 
				{	   
		            nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1).substring(nomFichier.lastIndexOf('\\') + 1);
		            UploadingTask.ecrireFichier(part, nomFichier, CHEMIN_FICHIERS_ENTREPRENEURS);
		            UploadingTask.ecrireFichier(part, nomFichier, CHEMIN_FICHIERS_ENTREPRENEURS_BACK_OFFICE);
		            entrepreneur.setLogo(nomFichier);
		            entrepreneur.getCompte().setEnabled(false);
					entrepreneurService.addEntrepreneur(entrepreneur);
					try
					{
						String appUrl = "inscriptionEntrepreneur";
						applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(entrepreneur.getCompte(), request.getLocale(), appUrl) );
						modelAndView.setViewName("confirmationInscription");			
					}
					catch(Exception e)
					{
						entrepreneurService.deleteEntrepreneur(entrepreneur.getId());
						errorMessage = "Erreur lors de l'envoi de l'email d'activation, veuillez réessayer";
						modelAndView.addObject("errorMessage", errorMessage);
						modelAndView.addObject("entrepreneur", new Entrepreneur());
						modelAndView.setViewName("inscription");
						e.printStackTrace();
					}
					return modelAndView;
		        }
				else
				{
					errorMessage = "Veuillez choisir un logo pour votre entreprise.";
					modelAndView.addObject("errorMessage", errorMessage);
					modelAndView.addObject("entrepreneur", new Entrepreneur());
					modelAndView.setViewName("inscription");
					return modelAndView;
				}
			}
			else
			{
				errorMessage = "Vérifier votre mot de passe.";
				modelAndView.addObject("errorMessage", errorMessage);
				modelAndView.addObject("entrepreneur", new Entrepreneur());
				modelAndView.setViewName("inscription");
				return modelAndView;
			}
		}
		else
		{
			errorMessage = "Le nom d'utilisateur existe déjà, réessayer";
			modelAndView.addObject("errorMessage", errorMessage);
			modelAndView.addObject("entrepreneur", new Entrepreneur());
			modelAndView.setViewName("inscription");
			return modelAndView;
		}
	}
}
