package com.javasampleapproach.callablecontroller.ser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Services{
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName()); 
	
	public String doProcess(String param){
		// Simulation the slowly process by taking time
		try{
			Thread.sleep(2000);	
			log.info("Thread: " + Thread.currentThread().getName());
		}catch(Exception e){}
		
		log.info("Service finishes the process!" + param+"Done");
		return param+"Done";
	}
	
}
