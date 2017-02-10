package fr.bougly.web.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
public class AdministrateurControllerTest {

	private MockMvc mockMvc;
	
	private static final String URL_CONTROLLEUR_ADMIN = "/administrateur";

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.alwaysDo(MockMvcResultHandlers.print())
				.apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testShowPageGestionCompte() throws Exception {
		
        this.mockMvc.perform(get(URL_CONTROLLEUR_ADMIN+AdministrateurController.URL_GESTION_COMPTE_PAGE).accept(MediaType.TEXT_HTML))
        .andExpect(status().isOk()).andExpect(view().name("gestionCompte"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testShowCreerCompte() throws Exception {
        this.mockMvc.perform(get(URL_CONTROLLEUR_ADMIN+AdministrateurController.URL_CREER_COMPTE).accept(MediaType.TEXT_HTML))
        .andExpect(status().isOk()).andExpect(view().name("creerCompte"));
	}

}
