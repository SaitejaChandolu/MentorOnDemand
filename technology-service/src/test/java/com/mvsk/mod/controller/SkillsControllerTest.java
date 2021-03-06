package com.mvsk.mod.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mvsk.Application;
import com.mvsk.mod.model.SkillDtls;
import com.mvsk.mod.model.UserDtls;
import com.mvsk.mod.security.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SkillsControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private String token;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

		UserDtls userDtlsObj = new UserDtls();
		userDtlsObj.setUserName("dummyUser");
		userDtlsObj.setPassword("dummyUser");
		userDtlsObj.setRole("ADMIN");
		token = createGWTToken(userDtlsObj);
	}



	
	

	@Test
	public void findAllSkills() throws Exception {

		String queryString = "?orderBy=id&direction=ASC&page=0&size=10";

		mockMvc.perform(
				MockMvcRequestBuilders
				.get("/skills/findAllSkills" + queryString)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").exists());

	}

	

	
	

	private static String createGWTToken(UserDtls userDtlsObj) {

		String token = null;
		TokenProvider jwtTokenUtil = new TokenProvider();
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + userDtlsObj.getRole()));
		final Authentication authentication = new UsernamePasswordAuthenticationToken(userDtlsObj, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		token = jwtTokenUtil.generateToken(authentication);

		return token;
	}
}