package com.epi.pfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epi.pfa.model.Compte;
import com.epi.pfa.model.Entrepreneur;

public interface EntrepreneurRepository extends JpaRepository<Entrepreneur, Long>
{
	Entrepreneur findOneByCompte(Compte compte);
	Entrepreneur findOneByAdresseMail(String adresseMail);
}
