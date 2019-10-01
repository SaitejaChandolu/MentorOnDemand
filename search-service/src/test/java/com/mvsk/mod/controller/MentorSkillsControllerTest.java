package com.mvsk.mod.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

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
import com.mvsk.mod.model.MentorSkillsDtls;
import com.mvsk.mod.model.UserDtls;
import com.mvsk.mod.security.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = Application.class) 
@SpringBootTest 
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 

public class MentorSkillsControllerTest {

	@Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	} 

	@Test
	public void findBySkillIdDateRange() throws Exception {

		Long skillId = 1L;
		String queryString = "?orderBy=id&direction=ASC&page=0&size=10";
		String startDate = "2019-01-12", endDate = "2019-01-20";
		String startTime = "12:00", endTime = "16:00";
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/search/findBySkillIdDateRange/{skillId}/{startDate}/{endDate}/{startTime}/{endTime}" + queryString, skillId, startDate, endDate, startTime, endTime)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").exists());
	}

	@Test
	public void findByMentorId() throws Exception{

		Long mentorId = 1L;
		String queryString = "?orderBy=id&direction=ASC&page=0&size=10";
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/search/findByMentorId/{mentorId}" + queryString, mentorId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").exists());

	}


	
	@Test
	public void updateMentorTrainingCount() throws Exception {

		UserDtls userDtlsObj = new UserDtls();
		userDtlsObj.setUserName("admin");
		userDtlsObj.setPassword("admin");
		userDtlsObj.setRole("MENTOR");
		String token = createGWTToken(userDtlsObj);
		
		Long mentorId = 5L, skillId = 6L;
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/search/updateMentorTrainingCount/" + mentorId + "/" + skillId)
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		        .andDo(print())
				.andExpect(status().isOk());
		
	}

	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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