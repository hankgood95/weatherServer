package com.wook.service;

import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.wook.controller.ShortWeatherController;
import com.wook.error.ApiCallError;
import com.wook.model.dao.ShortWeatherDao;
import com.wook.model.dto.GeoInfo;
import com.wook.model.dto.ShortWeatherReq;
import com.wook.model.dto.Temperature;

import ch.qos.logback.classic.Logger;

@Service
public class ShortWeatherService {

	//여기서는 이제 ShortWeatherDao Thread를 Thread Pool을 이용해서 500개의 Thread를 만들어 API를 호출할 예정
	
	private ShortWeatherDao swd;
	private MailService ms;
	
	private List<ShortWeatherReq> swrList;
	private List<GeoInfo> geoList;
	
	private Logger logger = (Logger) LoggerFactory.getLogger(ShortWeatherController.class);
	
	@Autowired
	public ShortWeatherService(ShortWeatherDao swd, MailService ms) {
		super();
		this.swd = swd;
		this.ms = ms;
	}
	
	public List<ShortWeatherReq> getSwr() {
		return swrList;
	}
	public void setSwrList (List<ShortWeatherReq> swrList) {
		this.swrList = swrList;
	}

	public List<GeoInfo> getGeoList() {
		return geoList;
	}

	public void setGeoList(List<GeoInfo> geoList) {
		this.geoList = geoList;
	}

	//여기서 이제 전달받은 GeoInfo List를 가지고 API를 호출하는 부분
	@Retryable(value = {ApiCallError.class},maxAttempts = 3, backoff= @Backoff(delay = 900000))
	public List<Temperature> callSW() throws InterruptedException, ApiCallError {
		// TODO Auto-generated method stub
		int swrListSize = swrList.size();
		logger.info(String.valueOf(swrListSize));
		List<Temperature> tl = new ArrayList<>();
		
		int count = 0;
		int fifty = 50;
		while(count < swrListSize) { //count가 1668보다 작으면 진입
			
			int beforeCall = tl.size();
			
			int fPlus = count+50; //세고 있는 숫자의 수를 50씩 늘리는거임 그래야 fPlus까지를 반복
			
			if(fPlus > swrListSize) { //만약 50더한게 List 사이즈보다 크면 진입 fPlus의 수를 swrListSize로 수정
				fPlus = swrListSize;
			}
			
			if(fifty > swrListSize - tl.size()) { //남아 있는 request 의 수가 50보다 작으면 fifty를 줄임
				fifty = swrListSize - tl.size();
			}
			
			CountDownLatch cdl = new CountDownLatch(fifty);
			ExecutorService exs = Executors.newFixedThreadPool(50);
			
			//콜백 메소드
			CompletionHandler<Temperature,Void> callBack = 
					new CompletionHandler<Temperature,Void>(){

						//성공했을떄
						@Override
						public void completed(Temperature result, Void attachment) {
							// TODO Auto-generated method stub
							tl.add(result); //전달받은 객체 인자를 list에 추가함
						}
						
						//API 연결 실패했을때
						@Override
						public void failed(Throwable exc, Void attachment){
							// TODO Auto-generated method stub
							logger.error("Api called failed.");
							//이제 여기서 메일을 보내야함
							exs.shutdownNow();
							logger.error("ExecutorService shut down now");
							
							cdl.countDown();
						}
				
			};
			

			//50개씩 threadPool 안에 있는 애들을 실행시킨다.
			for(int i = count; i<fPlus;i++) {
				exs.submit(new ShortWeatherDao(cdl,swrList.get(i),callBack));
			}
			
			exs.shutdown();
			cdl.await();
			
			//아예 실패되어 아무것도 담지 못했거나 가져와야되는 숫자만큼 가져오질 못할때 진입
			if((exs.isShutdown() && tl.isEmpty()) || tl.size() < fPlus) {
				logger.warn("ExecutorService is shut down");
				ms.sendErrorMail("API call server side error. \n We will retry calling api.");
				//1분뒤 retry
				throw new ApiCallError("API call server side error");
			}
			//에러를 발생하면 현재 여기를 넘어오지 못하고 있음
			
			int afterCall = tl.size(); //호출하고 났을때의 size 
			int differ = afterCall - beforeCall; //몇 차이나는지 호출이랑 호출하기전이랑 계산.
			count = count + differ; //시작점을 바꿈
			logger.info("Success API : "+String.valueOf(tl.size()));
			
			logger.info("-------------------");
			Thread.sleep(500);
			
		}
		
		logger.info(String.valueOf(tl.size()));
		
		return tl;
	}
	
	@Recover
	public List<Temperature> recoverApi(ApiCallError error){
		logger.error("API connection failed no more retry");
    	String emailMessage = "API connection failed please check your code";
    	ms.sendErrorMail(emailMessage);
    	
    	return null;
	}
}
