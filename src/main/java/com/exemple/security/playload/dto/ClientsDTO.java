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

	@NotNull(message = "Ville est obligatoire!")
	private Long idVilles;

	private String libelleVilles;

	@NotNull(message = "Activite est obligatoire!")
	private Long idActivites;

	private String libelleActivites;

	@NotNull(message = "Type Client est obligatoire!")
	private Long idTypesClients;

	private String libelleTypesClients;

	@NotNull(message = "Employe est obligatoire!")
	private Long idEmployes;

	private String LibelleEmployes;

	private String nom;

	private String prenom;

	private String email;

	private String telephone;

	private String fax;

	private String adresse;

	private String ice;

	private String rc;

	private String nature;
}
