package com.exemple.security.controller.parametrage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.security.exception.CustomException;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.ProcessusCourrierDTO;
import com.exemple.security.playload.dto.ValidePCdto;
import com.exemple.security.repository.ProcessusCourrierRepository;
import com.exemple.security.services.parametrage.processusCourrier.InProcessusCourrierServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/processusCourriers")
@RequiredArgsConstructor
public class ProcessusCourriersController {
	

	@Autowired
	private InProcessusCourrierServices processusCourrierServices;
	
	@Autowired
	private ProcessusCourrierRepository processusCourrierRepository;
	
	
	@GetMapping("/{id}")
	private ResponseEntity<ProcessusCourrierDTO> getProcessusCourrier(@PathVariable Long id)
	{
		ProcessusCourrierDTO processusCourrierDTO = processusCourrierServices.getProcessusCourrier(id);
		
		return new ResponseEntity<ProcessusCourrierDTO>(processusCourrierDTO, HttpStatus.OK);
	}
	
	@GetMapping("/allPagable/idC={id}")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize,
			@PathVariable Long id)
	{
		PageableResponseDTO processusModel = processusCourrierServices.getAllProcessusCourrier(pageNo,pageSize, id);
		return new ResponseEntity<>(processusModel , HttpStatus.OK);
	}
	
	@PostMapping("/validePC")
	public ResponseEntity<?> validateProcessusC(@Valid @RequestBody ValidePCdto validePCdto)
	{
		try {
			processusCourrierServices.validateProcessusCourrier(validePCdto);
			return new ResponseEntity<>(new MessageResponse("Validation success.","success"),HttpStatus.OK);
		} catch (CustomException e) {
			return new ResponseEntity<>(new MessageResponse(e.getMessage(),"warning"),HttpStatus.BAD_REQUEST);
		}
	}
}
	