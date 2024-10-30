package com.exemple.security.playload.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class VillesDTO {

	private Long id;

	@NotNull(message = "Libelle est obligatoire!")
	@NotEmpty(message = "Libelle est obligatoire !")
	private String libelle;


	private String code;

	private String statut;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateCreation;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateModification;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateDesactivation;
}
