package fr.bougly.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import fr.bougly.model.enumeration.RoleCompteEnum;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable();
		
		http.authorizeRequests()
		.antMatchers("/css/**","/js/**","/fonts/**").permitAll() 
        .anyRequest().authenticated()
        .and()
        .authorizeRequests().antMatchers("/responsable/**").access("hasRole("+RoleCompteEnum.RESPONSABLE+")").and()
        .authorizeRequests().antMatchers("/enseignant/**").access("hasRole("+RoleCompteEnum.ENSEIGNANT+")").and()
        .authorizeRequests().antMatchers("/etudiant/**").access("hasRole("+RoleCompteEnum.ETUDIANT+")").and()
        .authorizeRequests().antMatchers("/administrateur/**").access("hasRole("+RoleCompteEnum.ADMINISTRATEUR+")").and()
        .formLogin()
        .loginPage("/login.html")
        .defaultSuccessUrl("/accueilEtudiant.html",true)
        .successHandler(customAuthenticationSuccessHandler)
        .permitAll()
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login.html");
	}

}
