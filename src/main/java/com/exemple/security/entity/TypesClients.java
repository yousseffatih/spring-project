package com.exemple.security.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TypesClients extends ClassEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public TypesClients(Long id, String libelle, String code, String statut, Date dateCreation, Date dateModification, Date dateDesactivation) {
        super(libelle, code, statut, dateCreation, dateModification, dateDesactivation);
        this.id = id;
    }
}
