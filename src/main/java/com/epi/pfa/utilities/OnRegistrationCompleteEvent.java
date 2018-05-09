package com.epi.pfa.utilities;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.epi.pfa.model.Compte;

public class OnRegistrationCompleteEvent extends ApplicationEvent
{
	private static final long serialVersionUID = 1L;
	
	private String appUrl;
	private Locale locale;
	private Compte compte;
	
	public OnRegistrationCompleteEvent( Compte compte, Locale locale, String appUrl )
	{
		super(compte);
		this.compte = compte;
		this.locale = locale;
		this.appUrl = appUrl;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

}
