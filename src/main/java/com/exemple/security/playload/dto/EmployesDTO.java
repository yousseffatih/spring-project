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
public class EmployesDTO {
	
	private Long id;
	
	
	@NotNull(message = "Affectations est obligatoire !")
    private Long idAffectations;
	private String libelleAfficatation;
	
	@NotNull(message = "Fonctions est obligatoire !")
    private Long idFonctions;
	private String libelleFonction;
	
	
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
	
	
	@NotNull(message = "Nom est obligatoire !")
	@NotEmpty(message = "Nom est obligatoire !")
	private String nom;
	
	@NotNull(message = "Prenom est obligatoire !")
	@NotEmpty(message = "Prenom est obligatoire !")
	private String prenom;

	private String matricule; 
	
	private String cin;

	private String adresse;
	
	private String telephone;

	private String email;
}
