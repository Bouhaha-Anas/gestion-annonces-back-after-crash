package com.epi.pfa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.epi.pfa.model.Compte;
import com.epi.pfa.model.Entrepreneur;
import com.epi.pfa.repository.EntrepreneurRepository;

@Service
public class EntrepreneurService 
{
	@Autowired
	EntrepreneurRepository entrepreneurRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void addEntrepreneur(Entrepreneur entrepreneur)
	{
		//entrepreneur.getCompte().setMotDePasse(bCryptPasswordEncoder.encode(entrepreneur.getCompte().getMotDePasse()));
		entrepreneur.getCompte().setRole("ENTREPRENEUR");
		
		entrepreneurRepository.save(entrepreneur);
	}

	public List<Entrepreneur> getAllEntrepreneurs() 
	{
		return (List<Entrepreneur>) entrepreneurRepository.findAll();
	}

	public Entrepreneur getEntrepreneur(Long id) 
	{
		return entrepreneurRepository.findOne(id);
	}

	public void updateEntrepreneur(Entrepreneur entrepreneur) 
	{
		entrepreneurRepository.save(entrepreneur);
	}

	public void deleteEntrepreneur(Long id) 
	{
		entrepreneurRepository.delete(id);
	}

	public Entrepreneur findOneByCompte(Compte compte)
	{
		return entrepreneurRepository.findOneByCompte(compte);
	}
	
	public Entrepreneur findOneByAdresseMail( String adresseMail )
	{
		return entrepreneurRepository.findOneByAdresseMail(adresseMail);
	}

	public int totalEntrepreneurs()
	{
		return entrepreneurRepository.totalEntrepreneurs();
	}

	public List<Entrepreneur> findAll() 
	{
		return entrepreneurRepository.findAll();
	}
}
