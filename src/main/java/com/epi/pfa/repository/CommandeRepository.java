package com.epi.pfa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epi.pfa.model.Commande;
import com.epi.pfa.model.CommandePK;

public interface CommandeRepository extends JpaRepository<Commande, CommandePK> 
{
	@Query(value="select * from commandes where id_client = :idC", nativeQuery= true)
	public List<Commande> getCommandeByIdClient( @Param("idC") Long idC );
}
