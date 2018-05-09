package com.epi.pfa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.epi.pfa.model.Categorie;


public interface CategorieRepository extends JpaRepository<Categorie, Long>
{
	@Query( value="select * from categories "
			   + " where id not in ( select id_categorie from recommandations "
							     + " where id_client = :id )", nativeQuery= true )
	public List<Categorie> findNotYetRecommanded(@Param("id") Long id);
	
	@Query( value="select * from categories "
			   + " where id in ( select id_categorie from recommandations "
							     + " where id_client = :id )", nativeQuery= true ) 
	public List<Categorie> findRecommanded(@Param("id") Long id);
	
	public Categorie findOneByNom(String nom);
}
