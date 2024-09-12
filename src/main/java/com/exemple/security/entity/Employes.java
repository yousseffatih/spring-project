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
import jakarta.validation.constraints.Email;
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
public class Employes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AGENCES_ID", nullable=false)
    private Agences agences;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="FONCTIONS_ID", nullable=false)
    private Fonctions fonctions;

	@Size(max = 50)
	@Column(name="NOM", length = 50)
	private String nom;
	
	@Size(max = 50)
	@Column(name="PRENOM", length = 50)
	private String prenom;
	
	@Size(max = 20)
	@Column(name="MATRICULE", length = 20)
	private String matricule;
	
	@Size(max = 20)
	@Column(name="CIN", length = 20)
	private String cin;
	
	@Size(max = 100)
	@Column(name="ADRESSE", length = 100)
	private String adresse;
	
	@Size(max = 30)
	@Column(name="TELEPHONE", length = 30)
	private String telephone;

	@Size(max = 50)
	@Email
	@Column(name="EMAIL", length = 50)
	private String email;
	
	@Size(max = 3)
	@Column(name="STATUT", length = 3)
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
