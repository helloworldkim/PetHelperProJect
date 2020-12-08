package com.help.HelloPet.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Myservice {

	
		public String getAccessToken(String apikey) {
//			https://api.themoviedb.org/3/authentication/token/new?api_key=<<api_key>> //엑세스토큰 받기
			String url= "https://api.themoviedb.org/3/authentication/token/new?";
			String api_key ="api_key=2daa7f8ee3c810361492a3382e06545d";
			// RestTemplate 생성
	        RestTemplate rt = new RestTemplate();
	        URI uri=null;
			try {
				uri = new URI(url+api_key);
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
			
			return response.getBody().toString();
		}
}
