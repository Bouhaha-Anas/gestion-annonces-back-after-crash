package com.epi.pfa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epi.pfa.model.Compte;
import com.epi.pfa.model.VerificationToken;
import com.epi.pfa.repository.VerificationTokenRepository;

@Service
public class VerificationTokenService
{
	@Autowired
	VerificationTokenRepository verificationTokenRepository;
	
	public void createVerificationToken(Compte compte, String token)
	{
		VerificationToken myToken = new VerificationToken(compte, token);
		verificationTokenRepository.save(myToken);
	}
}
