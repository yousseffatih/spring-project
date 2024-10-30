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

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Interaction extends ClassEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CLIENTS_ID")
	private Clients clients;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TYPES_INTERACTION_ID")
	private TypesInteraction typesInteraction;

	private String notes;

	private Date dateActions;

}
