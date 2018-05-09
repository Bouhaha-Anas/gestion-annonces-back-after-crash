package com.epi.pfa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epi.pfa.model.Client;
import com.epi.pfa.model.Compte;
import com.epi.pfa.model.Notification;
import com.epi.pfa.service.ClientService;
import com.epi.pfa.service.CompteService;
import com.epi.pfa.service.NotificationService;

@RestController
public class NotificationController 
{
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private CompteService compteService;
	
	@Autowired
	private ClientService clientService;
	
	@RequestMapping( value="/notifications", method= RequestMethod.GET )
	public ModelAndView notificationPage()
	{
		ModelAndView modelAndView = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if( auth != null)
		{	
			String login = auth.getName();
			Compte compte = compteService.findOneByLogin(login);
			Client client = clientService.findOneByCompte(compte);
			List<Notification> notifications = notificationService.findByIdClient(client.getId());
			if( notifications != null )
			{
				modelAndView.addObject("notifications", notifications);
			}
		}
		
		modelAndView.setViewName("notifications");
		return modelAndView;
	}
}
