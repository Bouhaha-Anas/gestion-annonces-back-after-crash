package com.epi.pfa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epi.pfa.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long>
{
	List<Produit> findAllByEstActiveTrue();
	
	List<Produit> findAllByEstActiveFalse();
	
	List<Produit> findAllByDateFin(Date date);
	
	List<Produit> findAllByNomContainingIgnoreCaseAndCategorieNomOrderByEstActive(String nomP, String nomC);
	
	List<Produit> findAllByNomContainingIgnoreCaseAndEntrepreneurIdOrderByEstActive(String nomP, Long idE);
	
	List<Produit> findAllByDateDebutBeforeAndCategorieIdIn(Date date, Long id);
	
	List<Produit> findAllByCategorieIdInAndEstActiveTrue(Long idC);
	
	List<Produit> findAllByEntrepreneurIdInAndEstActiveTrue(Long id);
	
	List<Produit> findAllByEntrepreneurIdInAndEstActiveFalse(Long id);
	
	@Query( value="select count(*) from produits", nativeQuery= true )
	int totalProduits();
}
