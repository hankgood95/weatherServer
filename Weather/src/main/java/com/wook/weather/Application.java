package com.wook.weather;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@SpringBootApplication
public class Application implements CommandLineRunner{

	private final static String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0";
	private final ApiKey APK;
	
	
	public Application(ApiKey apk) {
		this.APK = apk;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
    @Override
    public void run( String... args ) throws Exception {
        
    	String key = "shortweather.key";
    	
        String serviceKey = APK.getApiKey();
        String pageNo = "1";
        String numOfRows = "12";
        String dataType = "JSON";
        String base_date = "20211020";
        String base_time = "2300";
        String nx = "55";
        String ny = "127";

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL); //UriBuilder를 생성하는 옵션을 설정하는 DefaultUriBuilderFactory 인스턴스 생성
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY); //encoding 모드 설정

        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();

        String response = wc.get()
                .uri(uriBuilder -> uriBuilder.path("/getVilageFcst")
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("numOfRows", numOfRows)
                        .queryParam("pageNo", pageNo)
                        .queryParam("dataType", dataType)
                        .queryParam("base_date", base_date)
                        .queryParam("base_time", base_time)
                        .queryParam("nx", nx)
                        .queryParam("ny", ny).build())
                .retrieve().bodyToMono(String.class).block();

        System.out.println(response);
        System.out.println(APK.getApiKey()); //한번 주석을 달아볼게
        
    }
    
}
