package com.epi.pfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epi.pfa.model.Compte;

public interface CompteRepository extends JpaRepository<Compte , Long>
{
	public Compte findOneByLogin(String login);
}
