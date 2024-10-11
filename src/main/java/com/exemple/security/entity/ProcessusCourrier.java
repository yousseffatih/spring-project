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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProcessusCourrier extends ClassEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="courrier_id")
	private Courrier courrier;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="employes_id")
	private Employes employes;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="processusModel_id")
	private ProcessusModel processusModel;	
	
	private Date dateDebut;
	
	private Date dateFin;
	
	private String commentaire;
	
	private String pv;
	
	public ProcessusCourrier(Long id, Courrier courrier,User user,Employes employes,ProcessusModel processusModel,Date dateDebut,Date dateFin,String libelle, String code, String statut, Date dateCreation, Date dateModification, Date dateDesactivation,String commentaire , String pv ) {
        super(libelle, code, statut, dateCreation, dateModification, dateDesactivation); 
        this.id = id;
        this.courrier = courrier;
        this.user = user;
        this.employes = employes;
        this.processusModel = processusModel;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.commentaire = commentaire;
        this.pv = pv;
    }
}
