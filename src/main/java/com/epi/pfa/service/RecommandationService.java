package com.epi.pfa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epi.pfa.model.Categorie;
import com.epi.pfa.model.Recommandation;
import com.epi.pfa.repository.RecommandationRepository;

@Service
public class RecommandationService
{
	@Autowired
	RecommandationRepository recommandationRepository;
	
	public List<Categorie> findByClient(Long id)
	{
		return recommandationRepository.findByRecommandationPrimaryKeyIdClient(id);
	}
	public void addRecommandation(Recommandation recommandation)
	{
		recommandationRepository.save(recommandation);
	}
	
	public Recommandation findByIdClientIdAndIdCategorie(Long idCli, Long idCat)
	{
		return recommandationRepository.findByRecommandationPrimaryKeyIdClientIsAndRecommandationPrimaryKeyIdCategorieIs(idCli, idCat);
	}
	
	public void deleteRecommandation(Recommandation recommandation)
	{
		recommandationRepository.delete(recommandation);
	}
	
	public List<Recommandation> getByCategorieId(Long idCat)
	{
		return recommandationRepository.getByCategorieId(idCat);
	}
}
