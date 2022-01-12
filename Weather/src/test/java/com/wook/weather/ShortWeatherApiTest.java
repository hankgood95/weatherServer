package com.wook.weather;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.wook.model.dao.ShortWeatherDao;

@RunWith(SpringRunner.class)
public class ShortWeatherApiTest {
	@Autowired
	private ShortWeatherDao swd;
	
	@Test
	public void apiTest() {
		
	}
}
