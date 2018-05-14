package com.epi.pfa.utilities;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	DataSource dataSource;
	
	@Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.jdbcAuthentication()
				.usersByUsernameQuery("select login, mot_de_passe, enabled from comptes where login=?")
				.authoritiesByUsernameQuery("select login, role from comptes where login=?")
				.dataSource(dataSource);	
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http
			.authorizeRequests()
			.antMatchers("/resultatRecherche").permitAll()
			.antMatchers("/inscriptionEntrepreneur").permitAll()
			.antMatchers("/confirmationInscription").permitAll()
			.antMatchers("/inscriptionConfirm**").permitAll()		
			.antMatchers("/inscriptionClient").permitAll()
			.antMatchers("/entrepreneur/{id}").permitAll()
			.antMatchers("/categorie/{id}/offres").permitAll()
			.antMatchers("/inscription").permitAll()
			.antMatchers("/offre/**").permitAll()
			.antMatchers("/accueil").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/validerCommande/promotion/{id}", "/profilClient/informationsPersonnelles", "/profilClient/mesCommandes", "/profilClient/mesRecommandations").hasAnyAuthority("CLIENT")
			.antMatchers("/nouvelleOffre", "/profilEntrepreneur/informationsPersonnelles","/profilEntrepreneur/informationsPersonnelles/modification").hasAnyAuthority("ENTREPRENEUR")
			.anyRequest()
				.authenticated()
			.and()
			.csrf()
				.disable().formLogin()
			.loginPage("/login")
				.failureHandler(authenticationFailureHandler)
				.successHandler(customAuthenticationSuccessHandler)
				.usernameParameter("login")
				.passwordParameter("motDePasse")
			.and()
			.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/accueil")
			.and()
			.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
		
//		http.sessionManagement()
//        	.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//        	.maximumSessions(2)
//        	.expiredUrl("/sessionExpired.html")
//        	.and()
//        	.invalidSessionUrl("/invalidSession.html");
			
		
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() 
	{
	    return new HttpSessionEventPublisher();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception 
	{
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**");
	}
	
}
