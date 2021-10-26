package com.wook.weather;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wook.model.Item;
import com.wook.model.SweatherRootRes;

import reactor.netty.http.client.HttpClient;

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
        
        String serviceKey = APK.getApiKey();
        String pageNo = "1";
        String numOfRows = "290";
        String dataType = "JSON";
        String base_date = "20211025";
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

        String response = wc.get()
                .uri(uriBuilder -> uriBuilder.path("/getVilageFcst")
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("numOfRows", numOfRows)
                        .queryParam("pageNo", pageNo)
                        .queryParam("dataType", dataType)
                        .queryParam("base_date", base_date)
                        .queryParam("base_time", base_time)
                        .queryParam("nx", nx)
                        .queryParam("ny", ny).build()) //위 쿼리들로 uri 빌드를 하고
                .retrieve() //http 요청하고
                .bodyToMono(String.class) //Mono로 값을 받고
                .block(); //동기식으로 받는다.

        System.out.println(response);
        
        ObjectMapper obm = new ObjectMapper(); //String 으로 된 json object를 class 형식으로 바꿀수 있게 해주는 ObjectMapper 클래스 인스턴스 생성
        
        SweatherRootRes res = obm.readValue(response, SweatherRootRes.class); //String으로 된 json을 SweatherRootRes 클래스 형식으로 변환함
        
        List<Item> item = res.getResponse().getBody().getItems().getItem();
        
        int count = 0; //최고 온도와 최저 온도만 받으면 되기 때문에 이를 체크할 flag를 만듬
        for(Item it : item) {
        	if(count>=2) { //최고 온도와 최저 온도를 확인했다면 반복문에서 탈출
        		break;
        	}
            if(it.getCategory().equals("TMN")){
            	System.out.println(it.toString()); //최저기온의 Item 객체 출력
            	count++; //count++ 해서 최저 기온 체크 표시
            }
            if(it.getCategory().equals("TMX")) {
            	System.out.println(it.toString()); //최고 기온의 Item 객체 출력
            	count++; //count++ 해서 최고 기온 체크 표시
            	//엥
            	//뭐지ㅋㅋㅋㅋㅋ
            }	
        }
        boolean check = false;
    }
    
}
