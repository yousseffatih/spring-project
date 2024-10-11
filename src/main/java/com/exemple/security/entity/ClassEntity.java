package com.exemple.security.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class ClassEntity {
	
	@Size(max = 100)
	@Column(name="LIBELLE", length = 100)
	protected String libelle;
	
	@Size(max = 20)
	@Column(name="CODE", length = 20)
	protected String code;
	

	@Size(max = 3)
	@Column(name="STATUT", length = 3)
	protected String statut;
	
	
	@Column(name = "DATE_CREATION")
	protected Date dateCreation;
	
	@Column(name = "DATE_MODIFICATION")
	protected Date dateModification;
	
	@Column(name = "DATE_DESACTIVATION")
	protected Date dateDesactivation;
	
	
}
