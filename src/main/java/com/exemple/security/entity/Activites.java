package com.exemple.security.entity;



import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Activites {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String libelle;
	
	@NotBlank
	@Size(max = 3)
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
