package fr.bougly.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import fr.bougly.service.AdministrateurService;
import fr.bougly.service.EnseignantService;
import fr.bougly.service.EtudiantService;
import fr.bougly.service.ResponsableService;
import fr.bougly.web.security.CustomAuthenticationSuccessHandler;
import fr.bougly.web.security.SecurityConfig;


@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
@Import({SecurityConfig.class,CustomAuthenticationSuccessHandler.class})
public class LoginControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private AdministrateurService administrateurService;
	
	@MockBean
	private EtudiantService etudiantService;
	
	@MockBean
	@Qualifier(value="enseignantService")
	private EnseignantService enseignantService;
	
	@MockBean
	@Qualifier(value="responsableService")
	private ResponsableService responsableService;

    @Test
    public void test_show_login_page() throws Exception {
    	
        this.mockMvc.perform(get(LoginController.URL_LOGIN_PAGE).accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andExpect(view().name("login"));
        // PRINT HTTP
    }

}
