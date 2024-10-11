package com.exemple.security.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "processus_model")
public class ProcessusModel extends ClassEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="typeCourriers_id")
    private TypeCourriers typeCourriers;
	
	private Integer duree;
	
	@Column(unique = true)
	private Integer orderPM;
	
	
	public ProcessusModel(Long id,TypeCourriers typeCourriers,Integer duree,Integer order, String libelle, String code, String statut, Date dateCreation, Date dateModification, Date dateDesactivation) {
        super(libelle, code, statut, dateCreation, dateModification, dateDesactivation); 
        this.id = id;
        this.typeCourriers = typeCourriers;
        this.orderPM = order;
        this.duree = duree;
    }
}
