package com.exemple.security.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "fonctions")
public class Fonctions extends ClassEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public Fonctions(Long id, String libelle, String code, String statut, Date dateCreation, Date dateModification, Date dateDesactivation) {
        super(libelle, code, statut, dateCreation, dateModification, dateDesactivation);
        this.id = id;
    }
}
