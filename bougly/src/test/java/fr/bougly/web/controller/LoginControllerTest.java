package fr.bougly.web.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.bougly.service.AdministrateurService;
import fr.bougly.service.EnseignantService;
import fr.bougly.service.EtudiantService;
import fr.bougly.service.ResponsableService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
public class LoginControllerTest {
	
    private MockMvc mockMvc;
    
	@Autowired
	private WebApplicationContext wac;
	
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
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.alwaysDo(MockMvcResultHandlers.print())
				.apply(springSecurity())
				.build();
	}


    @Test
    public void testShowLoginPage() throws Exception {
    	
        this.mockMvc.perform(get(LoginController.URL_LOGIN_PAGE).accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andExpect(view().name("login")).andDo(MockMvcResultHandlers.print());
    }

}
