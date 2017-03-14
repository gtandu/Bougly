package fr.bougly.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import fr.bougly.model.enumeration.RoleCompteEnum;
import fr.bougly.web.controller.LoginController;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
	CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable();
		
		http
		.authorizeRequests().antMatchers(LoginController.URL_CONFIRM_ACCOUNT, LoginController.URL_CREATE_PASSWORD,"/error/**").permitAll().and()
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

}
