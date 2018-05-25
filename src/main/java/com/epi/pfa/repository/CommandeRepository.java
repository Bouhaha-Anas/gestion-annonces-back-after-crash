package com.epi.pfa.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epi.pfa.model.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> 
{	
	List<Commande> findAllByClientIdIn(Long idC);
	
	@Query( value="select count(*) from commandes", nativeQuery= true )
	int totalCommandes();
}
