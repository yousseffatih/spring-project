package com.exemple.security.controller.parametrage;

import java.util.List;

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

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.exception.CustomException;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.CourrierDTO;
import com.exemple.security.playload.dto.GetCourrierBody;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.CourrierRepository;
import com.exemple.security.services.parametrage.courrier.InCourrierServcies;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/courriers")
@RequiredArgsConstructor
public class CourriersController{

	@Autowired
	private InCourrierServcies courrierServcies;

	@Autowired
	private CourrierRepository courrierRepository;



	@GetMapping("/{id}")
	private ResponseEntity<CourrierDTO> getVille(@PathVariable Long id)
	{
		CourrierDTO courrierDTO = courrierServcies.getCourrierId(id);

		return new ResponseEntity<>(courrierDTO, HttpStatus.OK);
	}

	@GetMapping("/all")
	public List<CourrierDTO> getAllActivites() {
		return courrierServcies.getAllCourrier();
	}

	 @GetMapping("/allPagable")
		public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
				@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
				@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
		{
			PageableResponseDTO affectations = courrierServcies.getAllCourriersPagebal(pageNo,pageSize);
			return new ResponseEntity<>(affectations , HttpStatus.OK);
		}

	 @PostMapping("/allFiltred")
		public ResponseEntity<?> getAllAll(@RequestBody GetCourrierBody getCourrierBody,
				@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
				@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize
		)
		{
		 System.out.println(getCourrierBody.toString());
			PageableResponseDTO list = courrierServcies.filterCourriers(
					getCourrierBody.getNCourrier(),
					getCourrierBody.getIdOrgExpDestSrc(),
					getCourrierBody.getIdOrgExpDestCible(),
					getCourrierBody.getDateDebut(),
					getCourrierBody.getDateFin(),
					getCourrierBody.getIdProcessusModel(),
					getCourrierBody.getTypeCourriersId(),
					pageNo,pageSize
					);
			return new ResponseEntity<>(list , HttpStatus.OK);
		}


	@PostMapping("/add")
	public ResponseEntity<?> addCourries( @Valid @RequestBody CourrierDTO courrierDTO)
	{
		courrierServcies.addCourrier(courrierDTO);
		return  ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Courrier ajouté.","success"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCourries(@PathVariable Long id, @Valid @RequestBody CourrierDTO courrierDTO)
	{
		try {

			if(courrierRepository.existsByLibelleModif(courrierDTO.getLibelle() , id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
			}
			courrierServcies.updateCourrir(courrierDTO,id);
			return  ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Courrier modifié.","success"));

		} catch (CustomException e) {
			return new ResponseEntity<>(new MessageResponse(e.getMessage(),"warning"),HttpStatus.BAD_REQUEST);
		}

	}

	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteCourrierStatus(@PathVariable Long id)
	 {
		  courrierServcies.deleteCourirrerStatut(id);
		 return new ResponseEntity<>(new MessageResponse("Courrier supprimé.","success") , HttpStatus.OK);
	 }

}
