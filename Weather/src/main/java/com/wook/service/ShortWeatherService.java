package com.wook.service;

import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wook.controller.Application;
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
	
	private Logger logger = (Logger) LoggerFactory.getLogger(Application.class);
	
	
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
		
		CountDownLatch cdl = new CountDownLatch(swrListSize);
		ExecutorService exs = Executors.newFixedThreadPool(500);
		
		List<Temperature> tl = new ArrayList<>();
		
		CompletionHandler<Temperature,Void> callBack = 
				new CompletionHandler<Temperature,Void>(){

					//성공했을떄
					@Override
					public void completed(Temperature result, Void attachment) {
						// TODO Auto-generated method stub
						if(result == null) {
							System.out.println("found it");
							System.exit(0);
						}
						System.out.println("CallBack : "+result.toString());
						tl.add(result); //전달받은 객체 인자를 list에 추가함
					}
					
					//실패했을때
					@Override
					public void failed(Throwable exc, Void attachment) {
						// TODO Auto-generated method stub
						System.out.println("failed");
					}
			
		};
		
		for(int i = 0; i<swrListSize;i++) {
			exs.submit(new ShortWeatherDao(cdl,swrList.get(i),callBack));
		}
		
		exs.shutdown();
		cdl.await();
		
		logger.info(String.valueOf(tl.size()));
		
		//문제 발생 Thread에서 호출해서 API 호출이 완료 되었을때 Temperature 클래스를 반환하려고 했으나
		//우리가 Service에서 반환해야 하는것은 Temperature List인대 우리가 정작 DAO로부터 반환 받는것은 Temperature 클래스 하나임
		//각각의 Thread에서 받은 Temperature를 List에 합칠 방법을 생각해야함
		
		return null;
	}
}
