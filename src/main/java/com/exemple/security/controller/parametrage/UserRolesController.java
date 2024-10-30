package com.exemple.security.controller.parametrage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.security.exception.CustomException;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.services.parametrage.UsersRoles.InUserRolesServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/userRoles")
@RequiredArgsConstructor
public class UserRolesController {

	@Autowired
	private InUserRolesServices userRolesServices;

	@GetMapping("/affecteRoles")
	public ResponseEntity<?> affectRoleToUser(
			@RequestParam(value = "idUser" , required = true) Long idUser,
			@RequestParam(value = "idRole"  , required = true) Long idRole) {
		try {
			userRolesServices.affectRoleToUser(idUser, idRole);
			return new ResponseEntity<>(new MessageResponse("Rôle affecté","Success"),HttpStatus.OK);
		} catch (CustomException e) {
			return new ResponseEntity<>(new MessageResponse(e.getMessage(),"warning"),HttpStatus.BAD_REQUEST);
		}
	}


	@GetMapping("/unAffecteRoles")
	public ResponseEntity<?> unAffectRoleToUser(
			@RequestParam(value = "idUser" , required = true) Long idUser,
			@RequestParam(value = "idRole"  , required = true) Long idRole) {
		try {
			userRolesServices.unaffectRoleToUser(idUser, idRole);
			return new ResponseEntity<>(new MessageResponse("Rôle non affecté.","Success"),HttpStatus.OK);
		} catch (CustomException e) {
			return new ResponseEntity<>(new MessageResponse(e.getMessage(),"warning"),HttpStatus.BAD_REQUEST);
		}
	}
}
