package com.exemple.security.playload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SingInRequest {

	@NotNull(message = "Email est obligatoire !")
	@NotEmpty(message = "Email est obligatoire!")
	private String email;

	@NotNull(message = "Password est obligatoire !")
	@NotEmpty(message = "Password est obligatoire!")
	private String password;
}
