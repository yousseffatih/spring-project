package com.exemple.security.playload.dto;

import java.util.Date;
import java.util.Set;

import com.exemple.security.entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Long id;

	@NotNull(message = "Libelle est obligatoire!")
	@NotEmpty(message = "Libelle est obligatoire!")
	private String libelle;

	private String code;

	private String statut;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateCreation;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateModification;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateDesactivation;

	@NotNull(message = "Username est obligatoire!")
	@NotEmpty(message = "Username est obligatoire!")
    private String username;

    private String password;

    private String email;

    private String first;

    @NotNull(message = "Employer est obligatoire!")
    private Long idEmployer;
    private String libelleEmployer;
    private String employer;

    private Set<Role> roles;
}
