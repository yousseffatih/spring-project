package com.exemple.security.playload.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveGedBodyDTO {

	@NotNull(message = "Files est obligatoire!")
	private List<Documents> files;

	@NotNull(message = "id User est obligatoire!")
	private Long idUser;

	@NotNull(message = "id est obligatoire!")
	private Long id;

	@NotNull(message = "name est obligatoire!")
	private String name;
}