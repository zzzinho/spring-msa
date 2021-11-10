package com.example.zuulsvr;

import java.util.Collections;
import java.util.List;

import com.example.zuulsvr.utils.UserContextInterceptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ZuulsvrApplication {

	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate(){
		RestTemplate template = new RestTemplate();
		List interceptors = template.getInterceptors();
		if (interceptors == null){
			template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
		} else {
			interceptors.add(new UserContextInterceptor());
			template.setInterceptors(interceptors);
		}
		return template;
	}
	public static void main(String[] args) {
		SpringApplication.run(ZuulsvrApplication.class, args);
	}

}
