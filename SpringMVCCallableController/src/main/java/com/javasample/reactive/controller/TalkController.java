package com.javasample.reactive.controller;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javasample.reactive.service.Services;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TalkController {
	
	@Resource
	Services services;
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@RequestMapping("/callablerequest")
	public Callable<String> callable(){
		log.info("#CallableRequest Received!");
		log.info("Thread running: " + Thread.currentThread().getName());
		Callable<String> result = new Callable<String>() {

			@Override
			public String call() throws Exception {
				return services.doProcess("callable");
			}
		};
		
		log.info("#CallableRequest Finish!");
		return result;
	}
	
	@RequestMapping("/normalrequest")
	public String normalRequest(){
		log.info("#NormalRequest: Request Received!");
		log.info("Thread running: " + Thread.currentThread().getName());
		String result = services.doProcess("normal");
		
		log.info("#NormalRequest: Request Finish!");
		return result;
	}
	
	@RequestMapping("/reactiverequest")
	private Flux<String> reactive() {
		log.info("#ReactiveRequest: Request Received!");
		log.info("Thread running: " + Thread.currentThread().getName());
		
		Flux<String> data = Flux.just("Java", "Sample", "Approach", ".com");
		data
			.parallel(2)
			.runOn(Schedulers.parallel())
			.subscribe(i -> services.doProcess(i));
		
		log.info("#NormalRequest: Request Finish!");
		return data.cache();
		
	    
	}
	
}
