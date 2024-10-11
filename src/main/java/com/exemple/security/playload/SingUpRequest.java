package com.exemple.security.playload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SingUpRequest {
	
	@NotNull(message = "Username est obligatoire!")
	@NotEmpty(message = "Username est obligatoire!")
	private String username;
	
	@NotNull(message = "Email est obligatoire!")
	@NotEmpty(message = "Email est obligatoire!")
	private String email;
	
	@NotNull(message = "Password est obligatoire!")
	@NotEmpty(message = "Password est obligatoire!")
	private  String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
