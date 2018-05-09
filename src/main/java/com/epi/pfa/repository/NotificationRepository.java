package com.epi.pfa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epi.pfa.model.Notification;
import com.epi.pfa.model.NotificationPrimaryKey;

public interface NotificationRepository extends JpaRepository<Notification, NotificationPrimaryKey>
{
	List<Notification> findByDateExpiration(Date date);
	
	@Query( value= "select * from notifications where id_client = :idC and id_produit = :idP", nativeQuery= true )
	Notification findByClientIdAndProduitId( @Param("idC") Long idC, @Param("idP") Long idP );
	
	@Query( value="select * from notifications where id_client = :idC", nativeQuery= true )
	List<Notification> findByIdClient(@Param("idC") Long idC);
}
