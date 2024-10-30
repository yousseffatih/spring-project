package com.exemple.security.playload.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProcessusModelDTO {
	private Long id;

	@NotNull(message = "TypeCourriers est obligatoire!")
	private Long idTypeCourriers;
	private String typeCourries;

	@NotNull(message = "Libelle est obligatoire!")
	@NotEmpty(message = "Libelle est obligatoire!")
	private String libelle;


	private String code;


	private String statut;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateCreation;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateModification;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateDesactivation;

	@NotNull(message = "Duree est obligatoire !")
	private Integer duree;

	@NotNull(message = "Order est obligatoire !")
	private Integer orderPM;
}
