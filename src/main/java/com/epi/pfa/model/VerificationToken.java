package com.epi.pfa.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class VerificationToken 
{
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Long id;
    
	private String token;
	
	@OneToOne( targetEntity = Compte.class, fetch = FetchType.EAGER  )
	@JoinColumn( nullable = false )
	private Compte compte;
	
	public VerificationToken()
	{
		
	}
	
	public VerificationToken(Compte compte, String token)
	{
		this.compte = compte;
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	@Override
	public String toString() {
		return "VerificationToken [id=" + id + ", token=" + token + ", compte=" + compte + "]";
	}
}
