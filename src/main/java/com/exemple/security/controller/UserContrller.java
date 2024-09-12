package com.exemple.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserContrller {
	@GetMapping()
	public ResponseEntity<String> sayHello()
	{
		return ResponseEntity.ok("Hi USER");
	}
}
	