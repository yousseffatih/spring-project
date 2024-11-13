package com.exemple.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.security.entity.User;
import com.exemple.security.exception.CustomException;
import com.exemple.security.playload.ChangePassword;
import com.exemple.security.playload.JwtAythentication;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.RefreshTokenRequest;
import com.exemple.security.playload.SingInRequest;
import com.exemple.security.playload.SingUpRequest;
import com.exemple.security.security.AuthentificationServices;
import com.exemple.security.security.UserPrincipal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	@Autowired
	private AuthentificationServices authentificationServices;

	@Autowired
    public void Authentication(AuthentificationServices authentificationServices) {
        this.authentificationServices = authentificationServices;
    }

	@PostMapping("/singup")
	public ResponseEntity<UserPrincipal> singUp(@Valid @RequestBody SingUpRequest singUpRequest)
	{

		return ResponseEntity.ok(authentificationServices.singup(singUpRequest));
	}

	@PostMapping("/signIn")
	public ResponseEntity<?> singIn(@Valid @RequestBody SingInRequest singInRequest)
	{
//		try {
//			return ResponseEntity.ok(authentificationServices.singIn(singInRequest));
//		} catch (CustomException e)
//		{
//			//return new ResponseEntity<>(new MessageResponse(e.getMessage(), "warning"), HttpStatus.BAD_REQUEST);
//			return  ResponseEntity.status(210).body(new MessageResponse(e.getMessage(),"warning"));
//		}

		JwtAythentication jwtAythentication = authentificationServices.singIn(singInRequest);
		if(jwtAythentication == null)
		{
			return  ResponseEntity.status(451).body(new MessageResponse("Mot de passe réinitialisé !!","warning"));
		} else {
			return ResponseEntity.ok(jwtAythentication);
		}
	}

	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassword changePassword)
	{
		try {
			authentificationServices.changePassword(changePassword);
			 return new ResponseEntity<>(new MessageResponse("Mot de passe changé avec succès.","success") , HttpStatus.OK);
		} catch (CustomException e) {
			return new ResponseEntity<>(new MessageResponse(e.getMessage(),"warning") , HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/refresh")
	public ResponseEntity<JwtAythentication> singIn(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest)
	{
		return ResponseEntity.ok(authentificationServices.refreshToken(refreshTokenRequest));
	}

	@PostMapping("/user")
	public ResponseEntity<User> getUser(@Valid @RequestBody SingInRequest singInRequest)
	{
		User user = authentificationServices.getUser(singInRequest.getEmail());
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
}
