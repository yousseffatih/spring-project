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
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Ged extends ClassEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date dateUpload;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
	private User user;

	private String fileName;

	private String contextFile;

	private String type;

	public Ged(Long id, String libelle, String code, String statut, Date dateCreation, Date dateModification, Date dateDesactivation,Date dateUploa , User user , String fileName , String contextFile ) {
        super(libelle, code, statut, dateCreation, dateModification, dateDesactivation);
        this.id = id;
        this.dateUpload = dateUploa;
        this.fileName = fileName;
        this.contextFile = contextFile;
    }
}
