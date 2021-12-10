package com.wook.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.wook.model.dto.ApiKey;
import com.wook.model.dto.GeoInfo;
import com.wook.model.dto.ShortWeatherReq;
import com.wook.model.dto.Temperature;
import com.wook.service.GeoService;
import com.wook.service.ShortWeatherService;
import com.wook.service.TempService;

@Controller
@SpringBootApplication(scanBasePackages= {"com.wook.model","com.wook.weather","com.wook.service"})
public class ShortWeatherController{

	private final ApiKey APK;
	
	private GeoService gs; //To get GeoService
	private ShortWeatherService sws;
	private List<Temperature> temperList;
	private List<ShortWeatherReq> swrList;
	private ShortWeatherReq swr;
	private TempService ts; //To save TempService
	
	
	private Logger logger = LoggerFactory.getLogger(ShortWeatherController.class); //로그를 찍기 위해서 사용하는 Class
	
	@Autowired
	public ShortWeatherController(ApiKey apk, GeoService gs, ShortWeatherService sws,ShortWeatherReq swr, TempService ts) {
		this.APK = apk;
		this.gs = gs;
		this.sws = sws;
		this.swr = swr;
		this.ts = ts;
	}
	
	@Scheduled(cron="0 0 22 * * *")
	public void callAPi() throws InterruptedException {
        swrList = new ArrayList<>();
        
        //DB에 저장된 GeoInfo 정보를 ShortWeatherReq List에 추가한다.
        for(GeoInfo gi : gs.getGeoXY()) {
        	swr = new ShortWeatherReq(APK.getApiKey(),gi.getX(),gi.getY());
        	swr.setBaseDate(); //현재 날짜로 baseDate를 설정하는 메소드를 호출
        	swr.setNx(gi.getX()); //x좌표값 저장
        	swr.setNy(gi.getY()); //y좌표값 저장
        	
        	swrList.add(swr); //list에 swr 추가
        }
        
        //위 객체를 가지고 이제 API를 호출할수 있게 Service에게 전해줘야 함
        sws.setSwrList(swrList);
        
        temperList = sws.callSW(); // API 통신 Service 호출
        
        logger.info("-------------------");
        
        logger.info("API ConnectionSuccess");
        
        logger.info("-------------------");
        
        //List에 담긴 온도 DB에 저장
        for(int i = 0;i<temperList.size();i++) {
        	Temperature temp = temperList.get(i);
        	ts.saveTemp(temp);
        }
        
        logger.info("DB Store Success");
	}
	

    
}
