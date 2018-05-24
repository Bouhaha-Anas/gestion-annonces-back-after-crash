package com.epi.pfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epi.pfa.model.Compte;

public interface CompteRepository extends JpaRepository<Compte , Long>
{
	Compte findOneByLogin(String login);
	
	@Query("select c from comptes c where login = :login and id != :id")
	Compte findOneButNotMe(@Param("login") String login, @Param("id") Long id );
}
