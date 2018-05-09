package com.epi.pfa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity( name="categories" )
public class Categorie implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	private String nom;
	
	@OneToMany( cascade=CascadeType.ALL, orphanRemoval = true, mappedBy = "categorie", fetch=FetchType.LAZY )
	private List<Produit> produits;
	
	@OneToMany( mappedBy="categorie" )
	private List<Recommandation> recommandations;
	
	public Categorie()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	public List<Recommandation> getRecommandations() {
		return recommandations;
	}

	public void setRecommandations(List<Recommandation> recommandations) {
		this.recommandations = recommandations;
	}

	@Override
	public String toString() {
		return "Categorie [id=" + id + ", nom=" + nom + ", produits=" + produits + ", recommandations="
				+ recommandations + "]";
	}	
}
