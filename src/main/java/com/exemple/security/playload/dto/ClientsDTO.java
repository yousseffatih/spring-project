package com.exemple.security.playload.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientsDTO {

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

	private Long idVilles;

	private String libelleVilles;

	private Long idActivites;

	private String libelleActivites;

	private Long idTypesClients;

	private String libelleTypesClients;

	private Long idEmployes;

	private String LibelleEmployes;

	private String nom;

	private String prenom;

	private String telephone;

	private String fax;

	private String adresse;

	private String ice;

	private String rc;

	private String nature;
}
