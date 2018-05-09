package com.epi.pfa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity( name="entrepreneurs" )
public class Entrepreneur implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	private String denominationSociale;
	private String secteurActivite;
	private String siegeSocial;
	private String telephone;
	private String siteWeb;
	private String adresseMail;
	private String logo;
	
	@OneToOne( cascade = {CascadeType.ALL} , orphanRemoval = true )
	@JoinColumn( unique=true )
	private Compte compte;
	
	@OneToMany( cascade=CascadeType.ALL, mappedBy = "entrepreneur")
	private List<Produit> produits;
	
	public Entrepreneur()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenominationSociale() {
		return denominationSociale;
	}

	public void setDenominationSociale(String denominationSociale) {
		this.denominationSociale = denominationSociale;
	}

	public String getSecteurActivite() {
		return secteurActivite;
	}

	public void setSecteurActivite(String secteurActivite) {
		this.secteurActivite = secteurActivite;
	}

	public String getSiegeSocial() {
		return siegeSocial;
	}

	public void setSiegeSocial(String siegeSocial) {
		this.siegeSocial = siegeSocial;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getSiteWeb() {
		return siteWeb;
	}

	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}

	public String getAdresseMail() {
		return adresseMail;
	}

	public void setAdresseMail(String adresseMail) {
		this.adresseMail = adresseMail;
	}

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	
}
