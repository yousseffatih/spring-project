package com.exemple.security.controller.parametrage;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.GedDTO;
import com.exemple.security.playload.dto.SaveGedBodyDTO;
import com.exemple.security.services.parametrage.Ged.InGedServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ged")
@RequiredArgsConstructor
public class GedController {

	@Autowired
	private InGedServices gedServices;

	@PostMapping()
	public ResponseEntity<?> saveGed(@Valid @RequestBody SaveGedBodyDTO saveGedBodyDTO) throws IOException
	{
		try {
			gedServices.saveImages(saveGedBodyDTO);
			return new ResponseEntity<>(new MessageResponse("Document ajouté." , "success"),HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(new MessageResponse("Problème d'enregistrement des documents." , "warning"),HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/{conetxt}")
	public ResponseEntity<?> getAllByNameId(@PathVariable String conetxt) throws IOException
	{
		List<GedDTO> list = gedServices.getAllGed(conetxt);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deletedGed(@PathVariable Long id) {
		gedServices.deleteGed(id);
		return new ResponseEntity<>(new MessageResponse("Document supprimé.","success") , HttpStatus.OK);
	}
}
