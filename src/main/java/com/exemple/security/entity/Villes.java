package com.exemple.security.entity;

import java.util.Date;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Villes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@NotBlank
	@Size(max = 100)
	@Column(name="LIBELLE")
	private String libelle;
	
	@NotBlank
	@Size(max = 20)
	@Column(name="CODE")
	private String code;
	
	@NotBlank
	@Size(max = 3)
	@Column(name="STATUT")
	private String statut;
	
	
	@Column(name = "DATE_CREATION")
	//@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;
	
	@Column(name = "DATE_MODIFICATION")
	//@Temporal(TemporalType.TIMESTAMP)
	private Date dateModification;
	
	@Column(name = "DATE_DESACTIVATION")
	//@Temporal(TemporalType.TIMESTAMP)
	private Date dateDesactivation;
}
