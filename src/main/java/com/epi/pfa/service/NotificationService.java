package com.epi.pfa.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epi.pfa.model.Notification;
import com.epi.pfa.repository.NotificationRepository;

@Service
public class NotificationService 
{
	@Autowired
	private NotificationRepository notificationRepository;
	
	public void addNotification(Notification notification)
	{
		notificationRepository.save(notification);
	}
	
	public void deleteNotification(Notification notification)
	{
		notificationRepository.delete(notification);
	}
	
	public List<Notification> findByDateExpiration(Date date)
	{
		return notificationRepository.findByDateExpiration(date);
	}
	
	public Notification findByClientIdAndProduitId(Long idC, Long idP)
	{
		return notificationRepository.findByClientIdAndProduitId(idC, idP);
	}
	
	public List<Notification> findByIdClient(Long idC)
	{
		return notificationRepository.findByIdClient(idC);
	}
}
