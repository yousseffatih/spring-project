package com.exemple.security.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Agences {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ORGANISMES_ID")
    private Organisme organismes;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="VILLES_ID")
    private Villes villes;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RESEAUX_ID")
    private Reseaux reseaux;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ACTIVITES_ID")
    private Activites activites;

	@Size(max = 20)
	@Column(name="CODE", length = 20)
	private String code;
	
	@Size(max = 100)
	@Column(name="LIBELLE", length = 100)
	private String libelle;
	
	@Size(max = 100)
	@Column(name="ADRESSE", length = 100)
	private String adresse;
	
	@Size(max = 50)
	@Column(name="EMAIL", length = 50)
	private String email;
	
	@Size(max = 30)
	@Column(name="TELEPHONE", length = 30)
	private String telephone;
	
	@Size(max = 30)
	@Column(name="FAX", length = 30)
	private String fax;
	
	@NotBlank
	@Size(max = 3)
	@Column(name="STATUT", length = 3)
	private String statut;
	
	@Size(max = 30)
	@Column(name="TYPE_AGENCE", length = 30)
	private String typeAgence;
	
	
	@Size(max = 3)
	@Column(name="BLOQUE", length = 3)
	private String bloque;
	
	 
	
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
