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
public class ProcessusCourrierDTO {
	
	private Long id;
	
	
	@NotNull(message = "Libelle est obligatoire !")
	@NotEmpty(message = "Libelle est obligatoire !")
	private String libelle;
	
	@NotNull(message = "code est obligatoire !")
	@NotEmpty(message = "code est obligatoire !")
	private String code;
	
	private String statut;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateCreation;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateModification;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateDesactivation;
	
	@NotNull(message = "Id courrier est obligatoire !")
	private Long idCourrier;
	
	private String libelleIdCourrier;
	
	@NotNull(message = "Id user est obligatoire !")
	private Long idUser;
	private String user;
	
	@NotNull(message = "Id emplyes est obligatoire !")
	private Long idEmployes;
	private String employe;
	
	@NotNull(message = "Id processus Model est obligatoire !")
	private Long idProcessusModel;	
	private String libelleProcessusModel;
	
	private Date dateDebut;
	
	private Date dateFin;
	
	private String commentaire;
	
	private String pv;
}
