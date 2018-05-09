package com.epi.pfa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity( name="contacts" )
public class Contact implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String objet;
	
	@Column( length = 3000 )
	private String message;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "client_id" )
	private Client client;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "entrepreneur_id" )
	private Entrepreneur entrepreneur;
	
	public Contact()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", objet=" + objet + ", message=" + message + ", client=" + client
				+ ", entrepreneur=" + entrepreneur + "]";
	}
}
