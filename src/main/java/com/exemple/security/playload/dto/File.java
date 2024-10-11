package com.exemple.security.playload.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class File {
	@NotNull(message = "extention est obligatoire !")
	@NotEmpty(message = " extention est obligatoire !")
	private String extention ;
	
	@NotNull(message = "File data est obligatoire !")
	@NotEmpty(message = "File est obligatoire !")
	private String base64File;
}
