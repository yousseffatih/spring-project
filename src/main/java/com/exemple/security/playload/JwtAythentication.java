package com.exemple.security.playload;


import java.util.List;

import lombok.Data;

@Data
public class JwtAythentication {

	private String token;
	private String refrechToken;
	private Long idUser;
	private Long idEmploye;
	private String employe;
	private String emailEmploye;
	private Long idAgence;
	private String agence;
	private String first;
	private List<String> roles;
//	private UserPrincipal user;

}
