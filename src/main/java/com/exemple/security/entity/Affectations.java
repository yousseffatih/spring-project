package com.exemple.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "affectations")
public class Affectations extends ClassEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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



	@Size(max = 30)
	@Column(name="TYPE_AFFECTATION", length = 30)
	private String typeAffectation;


	@Size(max = 3)
	@Column(name="BLOQUE", length = 3)
	private String bloque;


}
