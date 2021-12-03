package com.wook.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wook.model.dto.ApiKey;
import com.wook.model.dto.GeoInfo;
import com.wook.model.dto.ShortWeatherReq;
import com.wook.model.dto.Temperature;
import com.wook.service.GeoService;
import com.wook.service.ShortWeatherService;

@SpringBootApplication(scanBasePackages= {"com.wook.model","com.wook.weather","com.wook.service"})
public class Application implements CommandLineRunner{

	private final ApiKey APK;
	
	private GeoService gs; //To get GeoService
	private ShortWeatherService sws;
	private List<Temperature> temperList;
	private List<ShortWeatherReq> swrList;
	private ShortWeatherReq swr;
	
	
	private Logger logger = LoggerFactory.getLogger(Application.class); //로그를 찍기 위해서 사용하는 Class
	
	@Autowired
	public Application(ApiKey apk, GeoService gs, ShortWeatherService sws,ShortWeatherReq swr) {
		this.APK = apk;
		this.gs = gs;
		this.sws = sws;
		this.swr = swr;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
    @Override
    public void run( String... args ) throws Exception {
        
        String serviceKey = APK.getApiKey();
        String pageNo = "1";
        String numOfRows = "290";
        String dataType = "JSON";
        String base_date = "20211202";
        String base_time = "2300";

        swrList = new ArrayList<>();
        
        //DB에 저장된 GeoInfo 정보를 ShortWeatherReq List에 추가한다.
        for(GeoInfo gi : gs.getGeoXY()) {
        	swr = new ShortWeatherReq(serviceKey,pageNo,numOfRows,dataType,base_date,base_time,gi.getX(),gi.getY());
        	swrList.add(swr);
        }
        
        //위 객체를 가지고 이제 API를 호출할수 있게 Service에게 전해줘야 함
        sws.setSwrList(swrList);
        
        temperList = sws.callSW();
        
        logger.info("-------------------");
        
        //List에 담긴 온도 출력
        for(Temperature temp : temperList) {
        	logger.info(temp.toString());
        }
        
        logger.info("temperList Size : "+String.valueOf(temperList.size()));
        logger.info("Main Thread End");
        
        //이제 여기서 해야할일은 만든 온도 List를 DB에 저장시키는것을 하면 된다.
        //이건 별로 안 힘들것 같다. 왜냐하면 이미 DB에서 값을 가져오는것을 했기때문
        
        
        
    }
    
}
