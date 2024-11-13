package com.exemple.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class ClientsContacts extends ClassEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="FONCTIONS_ID")
	private Fonctions fonctions;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CLIENTS_ID")
	private Clients clients;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AFFECTATIONS_ID")
	private Affectations affectations;

	private String nom;

	private String prenom;

	private String email;

	private String telephone;

}
