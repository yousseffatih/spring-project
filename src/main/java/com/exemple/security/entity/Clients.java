package com.exemple.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Clients extends ClassEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="VILLES_ID")
	private Villes villes;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ACTIVITES_ID")
	private Activites activites;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TYPES_CLIENTS_ID")
	private TypesClients typesClients;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EMPLOYES_ID")
	private Employes Employes;

	@Size(max = 50)
	private String nom;

	@Size(max = 50)
	private String prenom;

	@Size(max = 20)
	private String telephone;

	@Size(max = 20)
	private String fax;

	private String email;

	@Size(max = 200)
	private String adresse;

	private String ice;

	private String rc;

	@Size(max = 10)
	private String nature;

}
