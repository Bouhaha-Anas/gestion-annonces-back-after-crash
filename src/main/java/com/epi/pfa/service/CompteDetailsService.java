package com.epi.pfa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epi.pfa.model.Compte;

@Service
public class CompteDetailsService implements UserDetailsService
{
	@Autowired
	CompteService compteService;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException 
	{
		Compte compte = compteService.findOneByLogin(login);
		UserDetails details = (UserDetails) compte;
		return details;
	}
	
	
}
