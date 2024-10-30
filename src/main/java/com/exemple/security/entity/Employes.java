package com.exemple.security.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employes")
public class Employes extends ClassEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AFFECTATIONS_ID")
    private Affectations affectations;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="FONCTIONS_ID")
    private Fonctions fonctions;

	@Size(max = 50)
	@Column(name="NOM", length = 50)
	private String nom;

	@Size(max = 50)
	@Column(name="PRENOM", length = 50)
	private String prenom;

	@Size(max = 20)
	@Column(name="MATRICULE", length = 20 , unique = true)
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

	private String fax;


	public Employes(Long id, Affectations affectations,Fonctions fonctions,String nom, String prenom, String matricule,String cin, String adresse, String telephone, String email,String fax,String libelle,String code,String statut,Date dateCreation,Date dateModification,Date dateDesactivation)
	{
		super(libelle, code, statut, dateCreation, dateModification, dateDesactivation);
		this.id=id;
		this.affectations = affectations;
		this.fonctions = fonctions;
		this.nom = nom;
		this.prenom=prenom;
		this.matricule = matricule;
		this.cin =cin;
		this.adresse = adresse;
		this.telephone = telephone;
		this.email = email;
		this.fax = fax;
	}
}
