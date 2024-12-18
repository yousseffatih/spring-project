package com.exemple.security.entity;


import jakarta.persistence.Column;
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
public class Numero extends ClassEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	@Column(name="STATUS", length=45)
	private String status;

	@Column(name="PFIX", length=10)
	private String pfix;

	@Column(name="veleur", length=10)
	private String veleur;

	@Column(name="POSITION", length=10)
	private Integer position;

	@Column(name="SFIX", length=10)
	private String sfix;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ORGANISME")
	private Organisme organisme;


	@Column(name="DEFAULTS", length=50)
	private String defaults;

}
