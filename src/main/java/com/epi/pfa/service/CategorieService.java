package com.epi.pfa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epi.pfa.model.Categorie;
import com.epi.pfa.repository.CategorieRepository;

@Service
public class CategorieService 
{
	@Autowired
	CategorieRepository categorieRepository;
	
	public List<Categorie> findAllCategories()
	{
		return categorieRepository.findAll();
	}
	
	public List<Categorie> findNotYetRecommanded(Long id)
	{
		return categorieRepository.findNotYetRecommanded(id);
	}
	
	public List<Categorie> findRecommanded(Long id)
	{
		return categorieRepository.findRecommanded(id);
	}
	
	public Categorie findOneByNom(String nom)
	{
		return categorieRepository.findOneByNom(nom);
	}

	public Categorie findOne(Long id) 
	{
		return categorieRepository.findOne(id);
	}
}
