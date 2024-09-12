package com.exemple.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.security.entity.User;
import com.exemple.security.playload.JwtAythentication;
import com.exemple.security.playload.RefreshTokenRequest;
import com.exemple.security.playload.SingInRequest;
import com.exemple.security.playload.SingUpRequest;
import com.exemple.security.playload.UserDTO;
import com.exemple.security.security.AuthentificationServices;
import com.exemple.security.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private AuthentificationServices authentificationServices;
	
	@Autowired
    public void Authentication(AuthentificationServices authentificationServices) {
        this.authentificationServices = authentificationServices;
    }
	
	@PostMapping("/singup")
	public ResponseEntity<UserPrincipal> singUp(@RequestBody SingUpRequest singUpRequest)
	{
		
		return ResponseEntity.ok(authentificationServices.singup(singUpRequest));
	}
	
	@PostMapping("/signIn")
	public ResponseEntity<JwtAythentication> singIn(@RequestBody SingInRequest singInRequest)
	{
		return ResponseEntity.ok(authentificationServices.singIn(singInRequest));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtAythentication> singIn(@RequestBody RefreshTokenRequest refreshTokenRequest)
	{
		return ResponseEntity.ok(authentificationServices.refreshToken(refreshTokenRequest));
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> getUser(@RequestBody SingInRequest singInRequest)
	{
		User user = authentificationServices.getUser(singInRequest.getEmail());
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
}
