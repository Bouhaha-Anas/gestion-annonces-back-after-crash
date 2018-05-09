package com.epi.pfa.utilities;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler
{ 
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException 
    { 
    	setDefaultFailureUrl("/login?error=true"); 
        super.onAuthenticationFailure(request, response, exception);
 
        String errorMessage= null;
 
        if (exception.getMessage().equalsIgnoreCase("User is disabled")) 
        {
            errorMessage = "votre compte est désactivé, consulter votre boite e-mail pour l'activer";
        } 
        else if (exception.getMessage().equalsIgnoreCase("User account has expired"))
        {
            errorMessage = "Votre compte est expiré";
        }
        else if ( exception.getMessage().equalsIgnoreCase("Bad credentials"))
        {
        	errorMessage = "Login ou mot de passe incorrects, veuillez réessayer";
        }
        
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage); 
    }
}
