package com.exemple.security.controller.parametrage;

import java.util.List;
import java.io.IOException;

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
			List<GedDTO> geds = gedServices.saveImages(saveGedBodyDTO);
			return new ResponseEntity<>(new MessageResponse("Ged ajoutée." , "success"),HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(new MessageResponse("Problem in saving Data" , "warning"),HttpStatus.BAD_REQUEST);
		}
	
	}
	
	@GetMapping("/{nameId}")
	public ResponseEntity<?> getAllByNameId(@PathVariable String nameId)
	{
		List<GedDTO> list = gedServices.getAllGed(nameId);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deletedGed(@PathVariable Long id) {
		gedServices.deleteGed(id);
		return new ResponseEntity<>(new MessageResponse("Ged supprimée.","success") , HttpStatus.OK);
	}
}
