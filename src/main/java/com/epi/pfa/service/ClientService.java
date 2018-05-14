package com.epi.pfa.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epi.pfa.model.Client;
import com.epi.pfa.model.Compte;
import com.epi.pfa.repository.ClientRepository;

@Service
public class ClientService 
{
	static Session session = null;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	SessionFactory sessionFactory;
	
	public List<Client> getAllClients()
	{
		return (List<Client>) clientRepository.findAll();
	}
	
	public void addClient(Client client)
	{
		//client.getCompte().setMotDePasse(bCryptPasswordEncoder.encode(client.getCompte().getMotDePasse()));
		client.getCompte().setEnabled(false);
		client.getCompte().setRole("CLIENT");
		clientRepository.save(client);
	}
	
	public Client getClient(Long id)
	{
		return clientRepository.findOne(id);
	}
	
	@Transactional
	public void updateClient(Client client) 
	{
		clientRepository.save(client);
	}
	
	public void deleteClient(Long id) 
	{
		clientRepository.delete(id);
	}
	
	public Client findOneByCompte(Compte compte)
	{
		return clientRepository.findOneByCompte(compte);
	}
	
	public Client findOneByAdresseMail( String adresseMail )
	{
		return clientRepository.findOneByAdresseMail(adresseMail);
	}
	
	public int totalClients()
	{
		return clientRepository.totalClients();
	}
}
