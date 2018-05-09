package com.epi.pfa.utilities;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableAutoConfiguration
public class WebConfig extends WebMvcConfigurerAdapter
{
	@Bean
	public BCryptPasswordEncoder passwordEncoder() 
	{
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver, SpringSecurityDialect sec) 
	{
	    final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver);
	    templateEngine.addDialect(sec);
	    return templateEngine;
	}
	
	@Bean  
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf)
	{  
        return hemf.getSessionFactory();  
    }
	
	@Bean
    public JavaMailSender javaMailService() 
	{
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("univers.promo18@gmail.com"); 
        javaMailSender.setPassword("11856627");
        
        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp"); 
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        
        return javaMailSender;
    }
	
//	@Bean
//	public MessageSource messageSource() 
//	{
//	     ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//	     //messageSource.setBasename("/WEB-INF/classes/messages");
//	     return messageSource;
//	}
}
