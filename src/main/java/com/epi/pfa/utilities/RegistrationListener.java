package com.epi.pfa.utilities;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.epi.pfa.model.Client;
import com.epi.pfa.model.Compte;
import com.epi.pfa.model.Entrepreneur;
import com.epi.pfa.service.ClientService;
import com.epi.pfa.service.EntrepreneurService;
import com.epi.pfa.service.VerificationTokenService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>
{
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	EntrepreneurService entrepreneurService;
	
	@Autowired
	VerificationTokenService verificationTokenService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	Environment env;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) 
	{
		this.confirmRegistration(event);
	}
	
	private void confirmRegistration(final OnRegistrationCompleteEvent event) 
	{
        Compte compte = event.getCompte();
        String token = UUID.randomUUID().toString();
        verificationTokenService.createVerificationToken(compte, token);
        
        final SimpleMailMessage email = constructEmailMessage(event, compte, token);
        
        mailSender.send(email);
    }
	
	private final SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final Compte compte, final String token) 
	{
		String recipientAddress = null;
		if(event.getAppUrl().equals("inscriptionClient"))
		{
			Client client = clientService.findOneByCompte(compte);
			recipientAddress = client.getAdresseMail();
		}
		else if(event.getAppUrl().equals("inscriptionEntrepreneur"))
		{
			Entrepreneur entrepreneur = entrepreneurService.findOneByCompte(compte);
			recipientAddress = entrepreneur.getAdresseMail();
		}
		        
        final String subject = "Activation de Votre Compte";
        final String confirmationUrl = "/inscriptionConfirm.html?token=" + token;
        
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("univers.promo18@gmail.com");
        email.setSubject(subject);
        email.setText( "Votre compte est bien créé, cliquer sur ce lien : "+ "http://localhost:8080" + confirmationUrl + " pour l'activer" );
        email.setTo(recipientAddress);
        
        return email;
    }

}
