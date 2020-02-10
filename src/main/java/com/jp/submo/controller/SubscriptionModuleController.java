package com.jp.submo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class SubscriptionModuleController {

	@GetMapping(value="/health")
	public ResponseEntity<String> getIngressHealth(){
		System.out.println("Hello , welcome to subscription Module.");
		return new ResponseEntity<String>("Hi, I am subscription Module. Up and running",HttpStatus.OK);
	}
}
