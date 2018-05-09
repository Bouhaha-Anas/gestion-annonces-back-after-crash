package com.epi.pfa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epi.pfa.model.Compte;
import com.epi.pfa.repository.CompteRepository;

@Service
public class CompteService 
{
	@Autowired
	CompteRepository compteRepository;
	
	public void addCompte(Compte compte)
	{
		compteRepository.save(compte);
	}
	
	public Compte findOneByLogin(String login) 
	{
		return compteRepository.findOneByLogin(login);
	}
	
	public void updateCompte(Compte compte)
	{
		compteRepository.save(compte);
	}
}
