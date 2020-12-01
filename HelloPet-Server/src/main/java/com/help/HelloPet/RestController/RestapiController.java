package com.help.HelloPet.RestController;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestapiController {
	
	//시도 코드조회 예제
	@GetMapping(value = "/sido" )
	public String petSido() {
		String myApiKey ="&serviceKey=BKC8cVQJZmzbzk760iM8pPU0%2B%2FMG35Y95n3SBqKnMgCEob9unhBApWNHXrC1qvgoM0vh3CvuWxWZdeyzF1PVNA%3D%3D";
		 String url = "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sido?numOfRows=20";
		// RestTemplate 생성
	        RestTemplate rt = new RestTemplate();
	        //header생성
	        //RestTemplate 는 default값으로 해당 header부분 설정되어있음
//	        HttpHeaders headers= new HttpHeaders();
//	        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
	        URI uri=null;
			try {
				uri = new URI(url+ myApiKey);
				System.out.println(uri);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
	        //post요청시 사용될 key value값
//	        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//	        params.add("serviceKey", myApiKey);
	        //http객체 생성 param값과 header값을 둘다들고있음
//	        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,headers);
//	        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
	        //요청주소,요청메서드,request,응답 받을 클래스
	        ResponseEntity<String> response =  rt.exchange(
	        		uri,
	        		HttpMethod.GET,
	        		null,
	        		String.class
    		);
		 //전달된 데이터 확인시 json!!!확인완료 정상적인 json데이터로 보내짐
		 return response.getBody().toString();
	}

	
	@GetMapping(value = "/sigungu" )
	public String petSigungu(@RequestParam(value = "orgCd") String orgCd) {
		String myApiKey ="&serviceKey=BKC8cVQJZmzbzk760iM8pPU0%2B%2FMG35Y95n3SBqKnMgCEob9unhBApWNHXrC1qvgoM0vh3CvuWxWZdeyzF1PVNA%3D%3D";
		 String url = "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu?upr_cd=";
		// RestTemplate 생성
	        RestTemplate rt = new RestTemplate();
	        URI uri=null;
			try {
				uri = new URI(url+orgCd+ myApiKey);
				System.out.println(uri);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
	        ResponseEntity<String> response =  rt.exchange(
	        		uri,
	        		HttpMethod.GET,
	        		null,
	        		String.class
    		);
		 //전달된 데이터 확인시 json!!!확인완료 정상적인 json데이터로 보내짐
		 return response.getBody().toString();
	}
	
	@GetMapping(value = "/shelter" )
	public String petShelter(@RequestParam(value = "orgCd") String orgCd,
			@RequestParam(value = "uprCd")String uprCd) {
		System.out.println(uprCd);
		System.out.println(orgCd);
		String myApiKey ="&ServiceKey=BKC8cVQJZmzbzk760iM8pPU0%2B%2FMG35Y95n3SBqKnMgCEob9unhBApWNHXrC1qvgoM0vh3CvuWxWZdeyzF1PVNA%3D%3D";
		String appendUpr_cd = "?upr_cd="+uprCd;
		String appenorcCd = "&org_cd="+orgCd;
		 String url = "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/shelter";
		// RestTemplate 생성
	        RestTemplate rt = new RestTemplate();
	        URI uri=null;
			try {
				uri = new URI(url+appendUpr_cd+appenorcCd+ myApiKey);
				System.out.println(uri);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
	        ResponseEntity<String> response =  rt.exchange(
	        		uri,
	        		HttpMethod.GET,
	        		null,
	        		String.class
    		);
		 //전달된 데이터 확인시 json!!!확인완료 정상적인 json데이터로 보내짐
		 return response.getBody().toString();
	}
		@GetMapping(value = "/abandonmentPublic" )
	public String findPet(@RequestParam(value = "bgnde") String bgnde,
			@RequestParam(value = "endde")String endde,
			@RequestParam(value = "pageNo",defaultValue ="1") String pageNo,
			@RequestParam(value = "numOfRows",defaultValue = "10") String numOfRows) {
//			http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?bgnde=20140301&endde=20140430&pageNo=1&numOfRows=10&ServiceKey=서비스키
		String myApiKey ="&ServiceKey=BKC8cVQJZmzbzk760iM8pPU0%2B%2FMG35Y95n3SBqKnMgCEob9unhBApWNHXrC1qvgoM0vh3CvuWxWZdeyzF1PVNA%3D%3D";
		 String url = "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?";
		 String appendbgnde = "?bgnde="+bgnde;
		 String appendendde = "&endde="+endde;
		String appendpageNo = "&pageNo="+pageNo;
		 String appendnumOfRows = "&numOfRows="+numOfRows;
		 // RestTemplate 생성
	        RestTemplate rt = new RestTemplate();
	        URI uri=null;
			try {
				uri = new URI(url+appendbgnde+appendendde+appendpageNo+appendnumOfRows+myApiKey);
				System.out.println(uri);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
	        ResponseEntity<String> response =  rt.exchange(
	        		uri,
	        		HttpMethod.GET,
	        		null,
	        		String.class
    		);
		 //전달된 데이터 확인시 json!!!확인완료 정상적인 json데이터로 보내짐
		 return response.getBody().toString();
	}
	
	
}
