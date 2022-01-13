package com.wook.model.dao;

import java.nio.channels.CompletionHandler;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.wook.controller.ShortWeatherController;
import com.wook.model.dto.Item;
import com.wook.model.dto.Items;
import com.wook.model.dto.ShortWeatherReq;
import com.wook.model.dto.SweatherRootRes;
import com.wook.model.dto.Temperature;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

//여기서 API를 호출하는 Thread를 만들 생각임
@Component
public class ShortWeatherDao implements Runnable{
	
	
	private final static String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0";
	
	private CountDownLatch cdl;
	private ShortWeatherReq swr;
	private SweatherRootRes result;
	private Temperature temp;
	private CompletionHandler<Temperature,Void> callBack;
	
	private Logger logger = LoggerFactory.getLogger(ShortWeatherController.class); //로그를 찍기 위해서 사용하는 Class
	
	@Autowired
	public ShortWeatherDao(SweatherRootRes result,Temperature temp) {
		
		super();
		this.result = result;
		this.temp = temp;
	}
	
	
	public ShortWeatherDao(CountDownLatch cdl, ShortWeatherReq swr, 
			CompletionHandler<Temperature,Void> callBack) {
		super();
		this.cdl = cdl;
		this.swr = swr;
		this.callBack = callBack;
	}



	public Temperature getTemp() {
		return temp;
	}



	public void setTemp(Temperature temp) {
		this.temp = temp;
	}



	@Override
	public void run() {
		//여기서 API를 호출할거임
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL); //UriBuilder를 생성하는 옵션을 설정하는 DefaultUriBuilderFactory 인스턴스 생성
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY); //encoding 모드 설정

        
        HttpClient client = HttpClient.create()
        		.responseTimeout(Duration.ofSeconds(60)); //http response timeout 설정을 60초로 설정함
        
        WebClient wc = WebClient.builder()
        		.uriBuilderFactory(factory) //위에서 만든 uri 인코딩 설정으로 uribuilder 설정을 함
        		.baseUrl(BASE_URL) //baseURI 설정하고
        		.clientConnector(new ReactorClientHttpConnector(client)) //위에서 만든 타임아웃 설정을 적용시키고
        		.build(); //빌드한다.
        
        result = new SweatherRootRes();
        
        //여기서 type mismatch 되는 부분을 찾아야함
        Mono<SweatherRootRes> response = wc.get()
                .uri(uriBuilder -> uriBuilder.path("/getVilageFcst")
                        .queryParam("serviceKey", 123)
                        .queryParam("numOfRows", swr.getNumOfRows())
                        .queryParam("pageNo", swr.getPageNo())
                        .queryParam("dataType", swr.getDataType())
                        .queryParam("base_date", swr.getBase_date())
                        .queryParam("base_time", swr.getBase_time())
                        .queryParam("nx", swr.getNx()) //지역정보
                        .queryParam("ny", swr.getNy()) //지역정보
                        .build()) //위 쿼리들로 uri 빌드를 하고
                .retrieve() //http 요청하고
    			.onStatus(HttpStatus::is4xxClientError,
    					error -> Mono.error(new RuntimeException("API not found")))
    			.onStatus(HttpStatus::is5xxServerError,
    					error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(SweatherRootRes.class);//Mono로 값을 받고
        
        //비동기 방식으로 약간 콜백 메소드와 같은 역할을 하는것 같다.그래서  이부분은 api 연결이 성공했을때 들어오는 부분인것 같다.
        response.subscribe(res -> {
        	result.setResponse(res.getResponse());
        	if(result.getResponse().getBody()!= null) {
        		getTemp(result.getResponse().getBody().getItems());
        		logger.info(temp.toString());
            	callBack.completed(temp, null);
            	cdl.countDown();
        	}else {
        		logger.error("http reqeust has failed");
        	}
        });
	}
	
    public void getTemp(Items items) {
    	
    	int count = 0;
    	temp = new Temperature();
    	
    	for(Item item : items.getItem()) {
    		if(count >= 2 ) 
    			break;
    		if(item.getCategory().equals("TMX")) {
    			String tempKey = item.getFcstDate()+item.getNx()+item.getNy();
    			
    			temp.setTempKey(tempKey);
    			int max = Math.round(Float.parseFloat(item.getFcstValue()));
    			temp.setBestMax(max);
    			temp.setNx(item.getNx());
    			temp.setNy(item.getNy());
    			temp.setDate(item.getFcstDate());
    			count++;
    			
    		}
    		if(item.getCategory().equals("TMN")){
    			int min = Math.round(Float.parseFloat(item.getFcstValue()));
    			temp.setBestMin(min);
    			count++;
    		}
    			
    	}

    	
    }

}
