package com.epi.pfa.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class NotificationPrimaryKey implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idClient;
	private Long idProduit;

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}

	public Long getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}

}
