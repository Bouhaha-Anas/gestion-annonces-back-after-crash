package com.epi.pfa.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epi.pfa.model.Produit;
import com.epi.pfa.repository.ProduitRepository;

@Service
public class ProduitService 
{
	@Autowired
	private ProduitRepository produitRepository;
	
	public void addProduit(Produit produit)
	{
		produitRepository.save(produit);
	}
	
	public List<Produit> getAllActiveProducts()
	{
		return produitRepository.getAllActiveProducts();
	}
	
	public List<Produit> getAllPassedProducts()
	{
		return produitRepository.getAllPassedProducts();
	}
	
	public int getTotalByCategorie(Long id)
	{
		return produitRepository.getTotalByCategorie(id);
	}
	
	public List<Produit> findByNomLike(String nom)
	{
		return produitRepository.findByNomLike(nom);
	}
	
	public Produit getProduit(Long id)
	{
		return produitRepository.findOne(id);
	}
	
	public List<Produit> findByDateFin(Date date)
	{
		return produitRepository.findByDateFin(date);
	}
	
	public void updateProduit(Produit produit)
	{
		produitRepository.save(produit);
	}
	
	public List<Produit> searchByCategorie(String nomP, String nomC)
	{
		return produitRepository.searchByCategorie(nomP, nomC);
	}
	
	public List<Produit> searchByEntrepreneur(String nomP, Long idE)
	{
		return produitRepository.searchByEntrepreneur(nomP, idE);
	}
	
	public List<Produit> getByDateAndCategorieId(Date date, Long id)
	{
		return produitRepository.getByDateAndCategorieId(date, id);
	}
	
	public int totalProduits()
	{
		return produitRepository.totalProduits();
	}

	public List<Produit> findByCategorieAndState(Long idC) 
	{
		return produitRepository.findByCategorieAndState(idC);
	}
	
	public List<Produit> findAllActivatedByEntrepreneur(Long idE)
	{
		return produitRepository.findAllActivatedByEntrepreneur(idE);
	}
	
	public List<Produit> findAllDisactivatedByEntrepreneur(Long idE)
	{
		return produitRepository.findAllDisactivatedByEntrepreneur(idE);
	}
	
}
