package com.epi.pfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epi.pfa.model.Client;
import com.epi.pfa.model.Compte;

public interface ClientRepository extends JpaRepository<Client, Long>
{
	Client findOneByCompte(Compte compte);
	Client findOneById(Long id);
	Client findOneByAdresseMail(String adresseMail);
}
