package com.wook.weather;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.wook.controller.TestController;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TestController.class)
public class TestPrac {
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testCheck() throws Exception{
		String test = "test";
		mvc.perform(get("/test")).andExpect(status().isOk()).andExpect(content().string(test));
	}
}
