package com.wook.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.wook.error.ApiCallError;
import com.wook.model.dto.ApiKey;
import com.wook.model.dto.GeoInfo;
import com.wook.model.dto.ShortWeatherReq;
import com.wook.model.dto.Temperature;
import com.wook.service.GeoService;
import com.wook.service.MailService;
import com.wook.service.ShortWeatherService;
import com.wook.service.TempService;

@Controller
public class ShortWeatherController{

	private final ApiKey APK;
	
	private GeoService gs; //To get GeoService
	private ShortWeatherService sws;
	private TempService ts; //To save TempService
	private MailService ms;
	
	private Logger logger = LoggerFactory.getLogger(ShortWeatherController.class); //로그를 찍기 위해서 사용하는 Class
	
	@Autowired
	public ShortWeatherController(ApiKey apk, GeoService gs, ShortWeatherService sws, TempService ts, MailService ms) {
		this.APK = apk;
		this.gs = gs;
		this.sws = sws;
		this.ts = ts;
		this.ms = ms;
	}
	
	@Retryable(value = {ApiCallError.class},maxAttempts = 3, backoff= @Backoff(delay = 900000))
	@Scheduled(cron="0 30 23 * * *", zone = "Asia/Seoul")
	public void callAPi() throws InterruptedException, ApiCallError {
		
		//내가 여기서 만들것은 이제 API 연결이 되지 않았을때 50건 이하라면 다시 시도해보고
		//아니라면 나에게 메일을 보내는것을 만들 예정이다.
		//그리고 위 과정을 테스트 코드를 통해서 검증을 해볼것이다.

		List<Temperature> temperList;
		List<ShortWeatherReq> swrList;
		
        swrList = new ArrayList<>();
        List<GeoInfo> giList = gs.getGeoXY();
        
        //DB에 저장된 GeoInfo 정보를 ShortWeatherReq List에 추가한다.
        for(GeoInfo gi : giList) {
        	
        	ShortWeatherReq swr = new ShortWeatherReq(APK.getApiKey(),"1",gi.getX(),gi.getY());
        	swr.setBaseDate(); //현재 날짜로 baseDate를 설정하는 메소드를 호출
        	swr.setNx(gi.getX()); //x좌표값 저장
        	swr.setNy(gi.getY()); //y좌표값 저장
        	
        	swrList.add(swr); //list에 swr 추가
        }
        
        for(GeoInfo gi : giList) {
        	
        	ShortWeatherReq swr = new ShortWeatherReq(APK.getApiKey(),"2",gi.getX(),gi.getY());
        	swr.setBaseDate(); //현재 날짜로 baseDate를 설정하는 메소드를 호출
        	swr.setNx(gi.getX()); //x좌표값 저장
        	swr.setNy(gi.getY()); //y좌표값 저장
        	
        	swrList.add(swr); //list에 swr 추가
        }
        
        for(GeoInfo gi : giList) {
        	
        	ShortWeatherReq swr = new ShortWeatherReq(APK.getApiKey(),"3",gi.getX(),gi.getY());
        	swr.setBaseDate(); //현재 날짜로 baseDate를 설정하는 메소드를 호출
        	swr.setNx(gi.getX()); //x좌표값 저장
        	swr.setNy(gi.getY()); //y좌표값 저장
        	
        	swrList.add(swr); //list에 swr 추가
        }
        
        //위 객체를 가지고 이제 API를 호출할수 있게 Service에게 전해줘야 함
        sws.setSwrList(swrList);
        
        temperList = sws.callSW(); // API 통신 Service 호출
        
        //Confirming API call failure
        if(temperList.isEmpty() || temperList.size()<swrList.size()) { //온도 리스트가 비어있다면 진입
        	logger.warn("TemperList is empty");
        	//이제 여기서 메일을 보내주는 서비스를 만들어서 메일 전송을 해줘야 함
        	String emailMessage = "API connection failed";
        	ms.sendErrorMail(emailMessage);
            logger.info("-------------------");
            logger.error("API Connection Fail");
            //이제 이걸 1분 뒤에 다시 호출하는 메소드를 만들어야 함
            logger.info("retry");
            throw new ApiCallError("API call server side error");
            
        }else {
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
	
	@Recover
	public void recoverApi(ApiCallError error){
		logger.error("API connection failed no more retry");
    	String emailMessage = "API connection failed please check your code";
    	ms.sendErrorMail(emailMessage);
	}
}
