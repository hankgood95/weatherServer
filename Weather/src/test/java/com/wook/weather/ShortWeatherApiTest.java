package com.wook.weather;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.wook.controller.ShortWeatherController;
import com.wook.model.dto.ApiKey;
import com.wook.model.dto.ShortWeatherReq;
import com.wook.service.GeoService;
import com.wook.service.ShortWeatherService;
import com.wook.service.TempService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ShortWeatherController.class)
public class ShortWeatherApiTest {
	
	@Autowired
	private ShortWeatherController swc;
	
	@MockBean
	private ApiKey apk;
	@MockBean
	private GeoService gs;
	@MockBean
	private ShortWeatherService sws;
	@MockBean
	private ShortWeatherReq swr;
	@MockBean
	private TempService ts;


	@Test
	public void apiTest() throws InterruptedException {
		swc.callAPi();
	}
}
