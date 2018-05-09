package com.epi.pfa.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RecommandationPrimaryKey implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long idClient;
	private Long idCategorie;

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}

	public Long getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(Long idCategorie) {
		this.idCategorie = idCategorie;
	}

}
