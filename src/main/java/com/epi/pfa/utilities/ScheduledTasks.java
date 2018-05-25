package com.epi.pfa.utilities;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epi.pfa.model.Categorie;
import com.epi.pfa.model.Client;
import com.epi.pfa.model.Notification;
import com.epi.pfa.model.NotificationPrimaryKey;
import com.epi.pfa.model.Produit;
import com.epi.pfa.model.Recommandation;
import com.epi.pfa.service.CategorieService;
import com.epi.pfa.service.ClientService;
import com.epi.pfa.service.NotificationService;
import com.epi.pfa.service.ProduitService;
import com.epi.pfa.service.RecommandationService;

@Component
public class ScheduledTasks 
{
	@Autowired
	private ProduitService produitService;
	
	@Autowired
	private CategorieService categorieService;
	
	@Autowired
	private RecommandationService recommandationService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private NotificationService notificationService;
	
	//@Scheduled(cron="0 0 0 * * *")
	public void verifierDateFinProduit()
	{
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);	
		date = calendar.getTime();
		
		List<Produit> produitsExipires = produitService.findAllByDateFin(date);
		if( produitsExipires != null )
		{
			for( int i=0 ; i< produitsExipires.size() ; i++ )
			{
				Produit produit = produitsExipires.get(i);
				produit.setEstActive(false);
				produitService.updateProduit(produit);
			}
		}
		
	}
	
	//@Scheduled( fixedDelay= 5000 )
	public void notificationSaver()
	{
		List<Categorie> categories = categorieService.findAllCategories();
		for(int i =0; i< categories.size(); i++)
		{
			List<Recommandation> recommandations = recommandationService.getByCategorieId(categories.get(i).getId());
			if( recommandations != null )
			{
				for( int j =0; j< recommandations.size(); j++ )
				{
					Client client = clientService.getClient(recommandations.get(j).getClient().getId());
					List<Produit> nvProduits = produitService.getByDateAndCategorieId(new Date(), categories.get(i).getId());
					for( int k =0; k< nvProduits.size(); k++ )
					{
						Calendar c = Calendar.getInstance();	   
						c.add(Calendar.DAY_OF_MONTH, 3);
						Notification notificationTemp = notificationService.findByClientIdAndProduitId( client.getId(), nvProduits.get(k).getId() );
						if( notificationTemp == null )
						{
							Notification notification = new Notification();
							NotificationPrimaryKey notificationPrimaryKey = new NotificationPrimaryKey();
							notificationPrimaryKey.setIdClient(client.getId());
							notificationPrimaryKey.setIdProduit(nvProduits.get(k).getId());
							
							notification.setNotificationPrimaryKey(notificationPrimaryKey);
							notification.setContenu("L'offre "+nvProduits.get(k).getNom()+" a été publiée par "+nvProduits.get(k).getEntrepreneur().getDenominationSociale()+" dans la catégorie "+categories.get(i).getNom());		
							notification.setDateEnregistrement(new Date());
							notification.setDateExpiration(c.getTime());
							notificationService.addNotification(notification);
						}
					}	
				}
			}
		}
	}
	
	//@Scheduled( fixedRate = 5000 )
	public void deleteExpiredNotification()
	{
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);	
		date = calendar.getTime();
		
		List<Notification> notificationExpires = notificationService.findByDateExpiration(date);
		for(int i =0; i< notificationExpires.size(); i++)
		{
			notificationService.deleteNotification(notificationExpires.get(i)); 
		}
	}
}
