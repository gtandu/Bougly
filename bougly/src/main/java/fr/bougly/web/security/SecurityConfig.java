package fr.bougly.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import fr.bougly.model.enumeration.RoleCompteEnum;
import fr.bougly.service.security.CustomUserDetailsService;
import fr.bougly.web.controller.GestionCompteController;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
	CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable();
		
		http
		.authorizeRequests().antMatchers(GestionCompteController.URL_CONFIRM_ACCOUNT, GestionCompteController.URL_CREER_MDP,"/error/**").permitAll().and()
		.authorizeRequests().antMatchers("/responsable/**").hasAuthority(RoleCompteEnum.Responsable.toString()).and()
		.authorizeRequests().antMatchers("/enseignant/**").hasAuthority(RoleCompteEnum.Enseignant.toString()).and()
		.authorizeRequests().antMatchers("/etudiant/**").hasAuthority(RoleCompteEnum.Etudiant.toString()).and()
		.authorizeRequests().antMatchers("/administrateur/**").hasAuthority(RoleCompteEnum.Administrateur.toString()).and()
		.authorizeRequests().antMatchers("/css/**","/js/**","/fonts/**").permitAll() 
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login.html")
        .defaultSuccessUrl("/accueilEtudiant.html",true)
        .successHandler(customAuthenticationSuccessHandler)
        .failureHandler(customAuthenticationFailureHandler)
        .permitAll()
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login.html")
		.and()
		.exceptionHandling().accessDeniedPage("/403");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(customUserDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}


}
