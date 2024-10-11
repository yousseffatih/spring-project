package com.exemple.security.playload.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrganismeDTO {
	
	private Long id;
	
	@NotNull(message = "Libelle est obligatoire!")
	@NotEmpty(message = "Libelle est obligatoire !")
	private String libelle;
	
	@NotNull(message = "Code est obligatoire !")
	@NotEmpty(message = "Code est obligatoire !")
	private String code;
	
	private String statut;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateCreation;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateModification;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateDesactivation;
	
	private String adresse;
	
	private String telephone;
	
	private String email;
	
	private String fax;
	
}
