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
public class CourrierDTO {


	private Long id;

	@NotNull(message = "Libelle  est obligatoire!")
	@NotEmpty(message = "Libelle est obligatoire!")
	private String libelle;


	private String code;

	private String statut;

	@JsonFormat(pattern = "dd/mm/yyyy")
	private Date dateCreation;

	@JsonFormat(pattern = "dd/mm/yyyy")
	private Date dateModification;

	@JsonFormat(pattern = "dd/mm/yyyy")
	private Date dateDesactivation;

	@NotNull(message = "N°Courrie  est obligatoire!")
	private String nCourrier;

	@NotNull(message = "Id orgExpDest - Src est obligatoire !")
	private Long idOrgExpDestSrc;
	private String libelleOrgExpDestSrc;


	@NotNull(message = "Id orgExpDest - cible est obligatoire !")
	private Long idOrgExpDestCible;
	private String libelleOrgExpDestCible;


	@NotNull(message = "Id affectations est obligatoire !")
	private Long idAffectations;
	private String libelleAffectations;


	@NotNull(message = "Id employes est obligatoire !")
	private Long idEmployes;
	private String libelleEmployes;


	@NotNull(message = "id type Courrier est obligatoire !")
	private Long idTypeCourriers;
	private String libelleTypeCourriers;


	private Long idTypeProcessusCourrier;
	private String libelleTypeProcessusCourrier;


	private Long idProcessusCourrier;


	@NotNull(message = "Interne Courrier est obligatoire !")
	private String interne;


	@NotNull(message = "Interne Courrier est obligatoire !")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateCourrirer;

	@NotNull(message = "Interne Courrier est obligatoire !")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateEchuance;
}
