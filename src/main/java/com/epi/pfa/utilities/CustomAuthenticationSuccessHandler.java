package com.epi.pfa.utilities;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.epi.pfa.model.Client;
import com.epi.pfa.model.Compte;
import com.epi.pfa.model.Entrepreneur;
import com.epi.pfa.service.ClientService;
import com.epi.pfa.service.CompteService;
import com.epi.pfa.service.EntrepreneurService;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler 
{
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private EntrepreneurService entrepreneurService;
	
	@Autowired
	private CompteService compteService;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication auth) throws IOException, ServletException 
    {
        HttpSession session = httpServletRequest.getSession();
        auth =  SecurityContextHolder.getContext().getAuthentication();  
        session.setAttribute("username", auth.getName());
        session.setAttribute("authorities", auth.getAuthorities());
        String beforeURL = httpServletRequest.getHeader("referer");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        Object[] role =  auth.getAuthorities().toArray();
        
        if( role[0].toString().equals("CLIENT") )
        {
        	Compte compte = compteService.findOneByLogin(auth.getName());
    		Client client = clientService.findOneByCompte(compte);
    		
    		session.setAttribute("client", client);
    		
    		if( beforeURL.contains("/inscriptionConfirm") )
            {
            	httpServletResponse.sendRedirect("profilClient/mesRecommandations");
            }
    		else
    		{
    			httpServletResponse.sendRedirect("accueil");
    		}
        }
        else if(role[0].toString().equals("ENTREPRENEUR"))
        {
        	Compte compte = compteService.findOneByLogin(auth.getName());
    		Entrepreneur entrepreneur = entrepreneurService.findOneByCompte(compte);
    		session.setAttribute("entrepreneur", entrepreneur);
    		httpServletResponse.sendRedirect("accueil");
        }        
    }
}
