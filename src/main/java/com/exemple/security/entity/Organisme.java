package com.exemple.security.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organisme")
public class Organisme extends ClassEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	@Size(max = 200)
	private String adresse;


	@Size(max = 30)
	private String telephone;


	@Size(max = 50)
	private String email;


	@Size(max = 30)
	private String fax;

	public Organisme(Long id, String adresse , String telephone, String email, String fax,String libelle,String statut,String code,Date dateCreation,Date dateModification, Date dateDesactivation)
	{
		super(libelle, code, statut, dateCreation, dateModification, dateDesactivation);
		this.id = id;
		this.adresse = adresse;
		this.telephone = telephone;
		this.email = email;
		this.fax = fax;
	}

}
