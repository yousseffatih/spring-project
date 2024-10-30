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
public class AffectationsDTO {

	private Long id;

	@NotNull(message = "Libelle est obligatoire !")
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

	@NotNull(message = "Orgnisme est obligatoire !")
	private Long idorganismes;

	private String libelleOrganisme;

	@NotNull(message = "Villes est obligatoire !")
    private Long idvilles;

	private String libelleVille;

	@NotNull(message = "Reseux est obligatoire !")
    private Long idreseaux;

	private String libelleReseaux;

	@NotNull(message = "Activite est obligatoire !")
    private Long idactivites;

	private String libelleActivites;

	private String adresse;

	private String email;

	private String telephone;

	private String fax;

	private String typeAffectation;

	private String bloque;
}
