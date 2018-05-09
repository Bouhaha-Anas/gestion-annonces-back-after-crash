package com.epi.pfa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="notifications")
public class Notification implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private NotificationPrimaryKey notificationPrimaryKey;
	
	@Column( length = 800 )
	private String contenu;
	
	@Temporal( TemporalType.DATE )
	private Date dateEnregistrement;
	
	@Temporal( TemporalType.DATE )
	private Date dateExpiration;
	
	@ManyToOne
	@JoinColumn( name="idProduit", referencedColumnName="id", insertable=false, updatable=false )
	private Produit produit;
	
	@ManyToOne
	@JoinColumn( name="idClient", referencedColumnName="id", insertable=false, updatable=false )
	private Client client;
	
	public Notification()
	{
		
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public Date getDateEnregistrement() {
		return dateEnregistrement;
	}

	public void setDateEnregistrement(Date dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}

	public Date getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public NotificationPrimaryKey getNotificationPrimaryKey() {
		return notificationPrimaryKey;
	}

	public void setNotificationPrimaryKey(NotificationPrimaryKey notificationPrimaryKey) {
		this.notificationPrimaryKey = notificationPrimaryKey;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "Notification [notificationPrimaryKey=" + notificationPrimaryKey + ", contenu=" + contenu
				+ ", dateEnregistrement=" + dateEnregistrement + ", dateExpiration=" + dateExpiration + ", produit="
				+ produit + ", client=" + client + "]";
	}
			
}
