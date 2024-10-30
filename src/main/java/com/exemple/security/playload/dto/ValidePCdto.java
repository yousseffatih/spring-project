package com.exemple.security.playload.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ValidePCdto {
	@NotNull(message = "id user est obligatoire!")
	private Long idUser;

	@NotNull(message = "id user est obligatoire!")
	private Long idCourrier;

	@NotNull(message = "id processus courrier est obligatoire!")
	private Long idProcessusCourrier;

	@NotNull(message = "Commentaire est obligatoire!")
	@NotEmpty(message = "Commentaire est obligatoire!")
	private String commentaire;

	@NotNull(message = "Pv est obligatoire!")
	@NotEmpty(message = "Pv est obligatoire!")
	private String pv;

	private Long idEmployer;

	private Long idAffectation;
}
