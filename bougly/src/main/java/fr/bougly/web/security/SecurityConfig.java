package fr.bougly.web.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/css/**","/js/**","/fonts/**").permitAll() 
        .anyRequest().authenticated()
        .and()
        .authorizeRequests().antMatchers("/responsable/**").access("hasRole('RESPONSABLE')").and()
        .formLogin()
        .loginPage("/login.html")
        .defaultSuccessUrl("/accueil.html",true)
        .permitAll()
        .and()
        .logout()                                    
        .permitAll();
	}

}
