package com.epi.pfa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epi.pfa.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long>
{
	@Query( value="select * from produits where est_active = true", nativeQuery= true ) 
	public List<Produit> getAllActiveProducts();
	
	@Query( value="select * from produits where est_active = false", nativeQuery= true )
	public List<Produit> getAllPassedProducts();
	
	@Query( value="select count(*) from produits where categorie_id = :id", nativeQuery= true )
	public int getTotalByCategorie(@Param("id") Long id);
	
	public List<Produit> findByNomLike(String nom);
	
	public List<Produit> findByDateFin(Date date);
	
	@Query( value="select p from produits p, categories c "
			+ " where p.categorie_id = c.id and "
			+ " p.nom = :nomP and "
			+ " c.nom = :nomC ", nativeQuery= true )
	public List<Produit> searchByCategorie(@Param("nomP") String nomP, @Param("nomC") String nomC);
	
	@Query( value="select * from produits where date_debut <= :date and categorie_id = :idCat ", nativeQuery= true )
	public List<Produit> getByDateAndCategorieId(@Param("date") Date date, @Param("idCat") Long id);
}
