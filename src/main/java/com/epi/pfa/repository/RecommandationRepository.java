package com.epi.pfa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epi.pfa.model.Categorie;
import com.epi.pfa.model.Recommandation;
import com.epi.pfa.model.RecommandationPrimaryKey;

public interface RecommandationRepository extends JpaRepository<Recommandation, RecommandationPrimaryKey>
{
	List<Categorie> findByRecommandationPrimaryKeyIdClient(Long id);
	
	@Query( value="delete from recommandations "
			   + " where id_client = :idCli and id_categorie = :idCat", nativeQuery= true )
	public void deleteRecommandation(@Param("idCli") Long idCli, @Param("idCat") Long idCat);
	
	@Query( value="select * from recommandations where id_categorie = :idCat", nativeQuery= true )
	public List<Recommandation> getByCategorieId(@Param("idCat") Long idCat);
}
