package com.epi.pfa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epi.pfa.model.Commande;
import com.epi.pfa.repository.CommandeRepository;

@Service
public class CommandeService
{
	@Autowired
	private CommandeRepository commandeRepository;
	
	public void addCommande(Commande commande)
	{
		commandeRepository.save(commande);
	}
	
	public List<Commande> getCommandeByIdClient(Long idC)
	{
		return commandeRepository.getCommandeByIdClient(idC);
	}
}
