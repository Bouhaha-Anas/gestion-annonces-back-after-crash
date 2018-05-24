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
	
	@Query( value="select p from produits p where p.nom like %:nomP% and p.categorie.nom = :nomC ")
	public List<Produit> searchByCategorie(@Param("nomP") String nomP, @Param("nomC") String nomC);
	
	@Query( value="select p from produits p where p.nom like %:nomP% and p.entrepreneur.id = :idE ")
	public List<Produit> searchByEntrepreneur(@Param("nomP") String nomP, @Param("idE") Long idE);
	
	@Query( value="select * from produits where date_debut <= :date and categorie_id = :idCat ", nativeQuery= true )
	public List<Produit> getByDateAndCategorieId(@Param("date") Date date, @Param("idCat") Long id);
	
	@Query( value="select count(*) from produits", nativeQuery= true )
	int totalProduits();
	
	@Query( value="select * from produits where est_active = true and categorie_id = :idC", nativeQuery= true )
	List<Produit> findByCategorieAndState(@Param("idC") Long idC);
	
	@Query( value="select p from produits p where p.entrepreneur.id = :idE and estActive = true ")
	List<Produit> findAllActivatedByEntrepreneur(@Param("idE") Long id);
	
	@Query( value="select p from produits p where p.entrepreneur.id = :idE and estActive = false ")
	List<Produit> findAllDisactivatedByEntrepreneur(@Param("idE") Long id);
	
	
}
