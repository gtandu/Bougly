package fr.bougly.web.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import fr.bougly.model.enumeration.RoleCompteEnum;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
        /* Redirect on the successful authentication of the user */
            
		Collection<? extends GrantedAuthority> auths = authResult.getAuthorities();
            for(GrantedAuthority currentRole : auths)
            {
            	if(currentRole.getAuthority().equalsIgnoreCase(RoleCompteEnum.Etudiant.toString())){
                    response.sendRedirect(response.encodeURL("/etudiant/accueilEtudiant.html"));
                }
                else if(currentRole.getAuthority().equalsIgnoreCase(RoleCompteEnum.Administrateur.toString()))
                {
                	response.sendRedirect(response.encodeURL("/administrateur/gestionCompte.html"));
                }
                else if(currentRole.getAuthority().equalsIgnoreCase(RoleCompteEnum.Enseignant.toString()))
                {
                	response.sendRedirect(response.encodeURL("/enseignant/gestionMatiere.html"));
                }
                else
                {
                	response.sendRedirect(response.encodeURL("/responsable/gestionClasse.html"));
                }
            }
            
    }
}
