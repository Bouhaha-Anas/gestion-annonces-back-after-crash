package com.epi.pfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestionAnnoncesApplication 
{	
	public static void main(String[] args) 
	{
		SpringApplication.run(GestionAnnoncesApplication.class, args);
	}
}
