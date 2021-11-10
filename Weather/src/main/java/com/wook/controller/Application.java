package com.wook.controller;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.wook.dao.GeoDao;
import com.wook.model.ApiKey;
import com.wook.model.Item;
import com.wook.model.Items;
import com.wook.model.SweatherRootRes;
import com.wook.model.Temperature;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication(scanBasePackages= {"com.wook.model","com.wook.weather","com.wook.dao"})
public class Application implements CommandLineRunner{

	private final static String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0";
	private final ApiKey APK;
	private SweatherRootRes result;
	private Temperature temp;
	private GeoDao geodao;
	
	private Logger logger = LoggerFactory.getLogger(Application.class); //로그를 찍기 위해서 사용하는 Class
	
	@Autowired
	public Application(ApiKey apk, SweatherRootRes result,Temperature temp,GeoDao geodao) {
		this.APK = apk;
		this.result = result;
		this.temp = temp;
		this.geodao = geodao;
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
        String base_date = "20211109";
        String base_time = "2300";
        String nx = "55";
        String ny = "127";

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL); //UriBuilder를 생성하는 옵션을 설정하는 DefaultUriBuilderFactory 인스턴스 생성
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY); //encoding 모드 설정

        HttpClient client = HttpClient.create()
        		.responseTimeout(Duration.ofSeconds(60)); //http response timeout 설정을 60초로 설정함
        
        WebClient wc = WebClient.builder()
        		.uriBuilderFactory(factory) //위에서 만든 uri 인코딩 설정으로 uribuilder 설정을 함
        		.baseUrl(BASE_URL) //baseURI 설정하고
        		.clientConnector(new ReactorClientHttpConnector(client)) //위에서 만든 타임아웃 설정을 적용시키고
        		.build(); //빌드한다.
        
        Mono<SweatherRootRes> response = wc.get()
                .uri(uriBuilder -> uriBuilder.path("/getVilageFcst")
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("numOfRows", numOfRows)
                        .queryParam("pageNo", pageNo)
                        .queryParam("dataType", dataType)
                        .queryParam("base_date", base_date)
                        .queryParam("base_time", base_time)
                        .queryParam("nx", nx) //지역정보
                        .queryParam("ny", ny) //지역정보
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
        		logger.info("http request success");
        		getTemp(result.getResponse().getBody().getItems());
            	logger.info(temp.toString());
        	}else {
        		logger.error("http reqeust has failed");
        	}
        });
        
        logger.info(geodao.getGeoData().toString());
        
    }
    
    public void getTemp(Items items) {
    	
    	int count = 0;
    	for(Item item : items.getItem()) {
    		if(count >= 2 ) 
    			break;
    		if(item.getCategory().equals("TMX")) {
    			temp.setBestMax(item.getFcstValue());
    			temp.setNx(item.getNx());
    			temp.setNy(item.getNy());
    			temp.setDate(item.getBaseDate());
    			count++;
    		}
    		if(item.getCategory().equals("TMN")){
    			temp.setBestMin(item.getFcstValue());
    			count++;
    		}
    			
    	}
    	
    }
    
}
