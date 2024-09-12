package com.exemple.security.playload;

import lombok.Data;

@Data
public class SingInRequest {

	private String email;
	private String password;
}
