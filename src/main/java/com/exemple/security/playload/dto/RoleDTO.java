package com.exemple.security.playload.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleDTO {
	
	private Long id;
	
	@NotNull(message = "Libelle est obligatoire!")
	@NotEmpty(message = "Libelle est obligatoire!")
	private String libelle;
	
	@NotNull(message = "Code est obligatoire !")
	@NotEmpty(message = "Code est obligatoire!")
	private String code;
	
	@NotNull(message = "Nom est obligatoire !")
	@NotEmpty(message = "Nom  est obligatoire!")
	private String nom;
	
	private String statut;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateCreation;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateModification;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateDesactivation;
}
