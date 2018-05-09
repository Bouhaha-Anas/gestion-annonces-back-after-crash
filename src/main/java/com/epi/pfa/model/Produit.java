package com.epi.pfa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity( name="produits" )
public class Produit implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	private String nom;
	private String description;
	private Float prixAvantRed;
	private Float prixApresRed;
	private String image;
	
	@Temporal( TemporalType.DATE )
	private Date dateDebut;
	@Temporal( TemporalType.DATE )
	private Date dateFin;
	
	@Column( columnDefinition="boolean default true" )
	private Boolean estActive;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "entrepreneur_id" )
	private Entrepreneur entrepreneur;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "categorie_id" )
	private Categorie categorie;
	
	@OneToMany( mappedBy="produit" )
	private List<Commande> commandes;
	
	@OneToMany( mappedBy="produit" )
	private List<Notification> notifications;

	public Produit()
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Boolean getEstActive() {
		return estActive;
	}

	public void setEstActive(Boolean estActive) {
		this.estActive = estActive;
	}

	public Entrepreneur getEntrepreneur() {
		return entrepreneur;
	}

	public void setEntrepreneur(Entrepreneur entrepreneur) {
		this.entrepreneur = entrepreneur;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public List<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(List<Commande> commandes) {
		this.commandes = commandes;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Float getPrixAvantRed() {
		return prixAvantRed;
	}

	public void setPrixAvantRed(Float prixAvantRed) {
		this.prixAvantRed = prixAvantRed;
	}

	public Float getPrixApresRed() {
		return prixApresRed;
	}

	public void setPrixApresRed(Float prixApresRed) {
		this.prixApresRed = prixApresRed;
	}
	
	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	@Override
	public String toString() {
		return "Produit [id=" + id + ", nom=" + nom + ", description=" + description + ", prixAvantRed=" + prixAvantRed
				+ ", prixApresRed=" + prixApresRed + ", image=" + image + ", dateDebut=" + dateDebut + ", dateFin="
				+ dateFin + ", estActive=" + estActive + ", entrepreneur=" + entrepreneur + ", categorie=" + categorie
				+ ", commandes=" + commandes + ", notifications=" + notifications + "]";
	}	
}
