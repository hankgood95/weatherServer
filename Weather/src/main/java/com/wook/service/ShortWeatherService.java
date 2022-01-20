package com.wook.service;

import static org.junit.Assert.assertThrows;

import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wook.controller.ShortWeatherController;
import com.wook.model.dao.ShortWeatherDao;
import com.wook.model.dto.GeoInfo;
import com.wook.model.dto.ShortWeatherReq;
import com.wook.model.dto.Temperature;

import ch.qos.logback.classic.Logger;

@Service
public class ShortWeatherService {

	//여기서는 이제 ShortWeatherDao Thread를 Thread Pool을 이용해서 500개의 Thread를 만들어 API를 호출할 예정
	
	private ShortWeatherDao swd;
	private List<ShortWeatherReq> swrList;
	private List<GeoInfo> geoList;
	
	private Logger logger = (Logger) LoggerFactory.getLogger(ShortWeatherController.class);
	
	
	public ShortWeatherService(ShortWeatherDao swd) {
		super();
		this.swd = swd;
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
	public List<Temperature> callSW() throws InterruptedException {
		// TODO Auto-generated method stub
		int swrListSize = swrList.size();
		logger.info(String.valueOf(swrListSize));
		List<Temperature> tl = new ArrayList<>();
		
		int count = 0;
		int fifty = 50;
		while(count < swrListSize) { //count가 1668보다 작으면 진입
			
			int beforeCall = tl.size();
			
			int fPlus = count+50; //세고 있는 숫자의 수를 50씩 늘리는거임
			
			if(fPlus > swrListSize) { //만약 50더한게 List 사이즈보다 크면 진입
				fPlus = swrListSize;
			}
			
			if(fifty > swrListSize - tl.size()) { //만약 50이 현재 남아있는 데이터보다 크다면 진입해서 현재 남아있는 데이터수로 바꿔줌
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
							cdl.countDown();
						}
				
			};
			

			//50개씩 threadPool 안에 있는 애들을 실행시킨다.
			for(int i = count; i<fPlus;i++) {
				exs.submit(new ShortWeatherDao(cdl,swrList.get(i),callBack));
			}
			
			exs.shutdown();
			cdl.await();
			
			//50번씩 반복을 하고 ExecutorService는 shutdown 되어 있고 오류때문에 List에 추가되지 못했다면 여기 진입
			if(exs.isShutdown()&& tl.isEmpty()) {
				logger.info("ExecutorService ShutDown");
				break;
			}
			//에러를 발생하면 현재 여기를 넘어오지 못하고 있음
			
			int afterCall = tl.size();
			int differ = afterCall - beforeCall;
			count = count + differ;
			logger.info("Success API : "+String.valueOf(tl.size()));
			
			logger.info("-------------------");
			Thread.sleep(500);
			
		}
		
		logger.info(String.valueOf(tl.size()));
		
		return tl;
	}
}
