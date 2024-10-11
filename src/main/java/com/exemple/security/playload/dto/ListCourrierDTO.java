package com.exemple.security.playload.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public interface ListCourrierDTO {
	
	 Long getId();
	
	 String getLibelle();
	
	 String getCode();
	
	 String getStatut();
	 
	 @JsonFormat(pattern = "dd/MM/yyyy")
	 Date getDateCreation();
	 
	 @JsonFormat(pattern = "dd/MM/yyyy")
	 Date getDateModification();
	 
	 @JsonFormat(pattern = "dd/MM/yyyy")
	 Date getDateDesactivation();
	
	 String getNCourrier();
	
	 Long  getIdOrgExpDestSrc();
	
	 String  getLibelleOrgExpDestSrc();
	
	 Long getIdOrgExpDestCible();
	
	 String  getLibelleOrgExpDestCible();
	
	 Long getIdAffectations();
	
	 String  getLibelleAffectations();
	
	 Long getIdEmployes();
	
	 String  getNomEmployes();
	
	 Long getIdTypeCourriers();
	
	 String  getLibelleEmployes();
	
	 Long getIdProcessusCourrier();
	
	 String getLibelleProcessusCourrier();
	
	 String getInterne();
	
	 @JsonFormat(pattern = "dd/MM/yyyy")
	 Date getDateCourrirer();
	 
	 @JsonFormat(pattern = "dd/MM/yyyy")
	 Date getDateEchuance();
}
