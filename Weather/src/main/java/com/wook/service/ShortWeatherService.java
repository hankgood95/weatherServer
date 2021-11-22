package com.wook.service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.wook.model.dao.ShortWeatherDao;
import com.wook.model.dto.GeoInfo;
import com.wook.model.dto.ShortWeatherReq;
import com.wook.model.dto.Temperature;

@Service
public class ShortWeatherService {

	//여기서는 이제 ShortWeatherDao Thread를 Thread Pool을 이용해서 500개의 Thread를 만들어 API를 호출할 예정
	
	private ShortWeatherDao swd;
	private List<ShortWeatherReq> swrList;
	private List<GeoInfo> geoList;
	
	
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
		int listSize = geoList.size();
		
		CountDownLatch cdl = new CountDownLatch(listSize);
		ExecutorService exs = Executors.newFixedThreadPool(500);
		
		for(int i = 0; i<listSize;i++) {
			exs.submit(new ShortWeatherDao("Thread"+i,exs,cdl,swrList.get(i)));
		}
		
		exs.shutdown();
		cdl.await();
		
		//문제 발생 Thread에서 호출해서 API 호출이 완료 되었을때 Temperature 클래스를 반환하려고 했으나
		//우리가 Service에서 반환해야 하는것은 Temperature List인대 우리가 정작 DAO로부터 반환 받는것은 Temperature 클래스 하나임
		//각각의 Thread에서 받은 Temperature를 List에 합칠 방법을 생각해야함
		
		return null;
	}
}
