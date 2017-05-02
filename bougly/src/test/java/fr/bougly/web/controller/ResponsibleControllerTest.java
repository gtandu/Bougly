package fr.bougly.web.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ResponsibleControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	private final String URL_RESPONSIBLE_CONTROLLER = "/responsable";

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).alwaysDo(MockMvcResultHandlers.print())
				.apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(authorities = "Responsible")
	public void testShowPageGestionFiliere() throws Exception {
		mockMvc.perform(get(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_COURSE_MANAGEMENT)
				.accept(MediaType.TEXT_HTML)).andExpect(status().isOk()).andExpect(view().name("gestionFiliere"));
	}

	@Test
	public void testCreerClasse() throws Exception {
	}
}