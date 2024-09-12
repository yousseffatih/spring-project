package com.exemple.security.playload;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class VillesDTO {
	
	private Long id;
	

	@NotNull
	@Size(max = 100)
	private String libelle;
	
	@NotNull
	@Size(max = 20)
	private String code;
	
	@NotNull
	@Size(max = 3)
	private String statut;
	
	

	//@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;
	

	//@Temporal(TemporalType.TIMESTAMP)
	private Date dateModification;
	

	//@Temporal(TemporalType.TIMESTAMP)
	private Date dateDesactivation;
}
