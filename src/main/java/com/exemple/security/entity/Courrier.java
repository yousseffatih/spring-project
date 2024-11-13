package com.exemple.security.entity;

import java.util.Date;

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


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Courrier extends ClassEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nCourrier;

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="orgExpDestSrc_Id")
	private OrgExpDest orgExpDestSrc;

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="orgExpDestCible_id")
	private OrgExpDest orgExpDestCible;

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="affectations_id")
	private Affectations affectations;

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="employes_id")
	private Employes employes;

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="typeCourriers_id")
	private TypeCourriers typeCourriers;

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="processusCourrier_id")
	private ProcessusCourrier processusCourrier;

	private String interne;


	private Date dateCourrirer;

	private Date dateEchuance;

}
