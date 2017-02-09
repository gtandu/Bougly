package fr.bougly.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
        .authorizeRequests().antMatchers("/responsable/**").access("hasRole('RESPONSABLE')").and()
        .authorizeRequests().antMatchers("/etudiant/**").access("hasRole('ETUDIANT')").and()
        .authorizeRequests().antMatchers("/administrateur/**").access("hasRole('ADMIN')").and()
        .formLogin()
        .loginPage("/login.html")
        .successHandler(customAuthenticationSuccessHandler)
        .permitAll()
        .and()
        .logout()                                    
        .permitAll();
	}

}
