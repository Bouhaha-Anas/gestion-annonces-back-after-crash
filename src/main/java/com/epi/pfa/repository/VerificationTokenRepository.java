package com.epi.pfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epi.pfa.model.Compte;
import com.epi.pfa.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>
{
	VerificationToken findByToken(String token);
	VerificationToken findByCompte(Compte compte);
}
