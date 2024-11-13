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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientsContactsDTO {

	private Long id;

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

	@NotNull(message = "Fonctions est obligatoire!")
	private Long idFonctions;

	private String libelleFonctions;

	@NotNull(message = "Affectations est obligatoire!")
	private Long idAffectations;

	private String libelleAffectations;

	@NotNull(message = "Affectations est obligatoire!")
	private Long idClient;
	private String libelleClient;

	@NotNull(message = "Nom est obligatoire!")
	@NotEmpty(message = "Nom est obligatoire!")
	private String nom;

	@NotNull(message = "Prénom est obligatoire!")
	@NotEmpty(message = "Prénom est obligatoire!")
	private String prenom;

	@NotNull(message = "Email est obligatoire!")
	@NotEmpty(message = "Email est obligatoire!")
	private String email;

	@NotNull(message = "Téléphone est obligatoire!")
	@NotEmpty(message = "Téléphone est obligatoire!")
	private String telephone;
}
