package com.epi.pfa.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epi.pfa.model.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> 
{	
	@Query( value="select * from commandes where id_client = :idC ", nativeQuery = true )
	List<Commande> findCommandeByClient(@Param("idC") Long idC);
	
	@Query( value="select count(*) from commandes", nativeQuery= true )
	int totalCommandes();
}
