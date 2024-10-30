package com.exemple.security.playload.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetCourrierBody {

	private String nCourrier;

	private Long idOrgExpDestSrc;

	private Long idOrgExpDestCible;

	private Long typeCourriersId;

	@NotNull(message = "Date debut  est obligatoire!")
	@NotEmpty(message = "Date debut  est obligatoire!")
	private String dateDebut;

	@NotNull(message = "Date fin  est obligatoire!")
	@NotEmpty(message = "Date fin  est obligatoire!")
	private String dateFin;

	private Long idProcessusModel;

	private Long idTypeCourriers;
}
