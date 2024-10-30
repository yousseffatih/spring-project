package com.exemple.security.controller.parametrage;

import java.util.List;
import java.util.stream.Collectors;

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
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.AffectationsDTO;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.AffectationsRepository;
import com.exemple.security.services.parametrage.Affectation.InAffectationServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/affectations")
@RequiredArgsConstructor
public class AffectationsController {


	@Autowired
	private InAffectationServices affectationServices;

	@Autowired
	private AffectationsRepository affectationsRepository;


	@GetMapping("/{id}")
	private ResponseEntity<AffectationsDTO> getVille(@PathVariable Long id)
	{
		AffectationsDTO affectations = affectationServices.getAffectation(id);

		return new ResponseEntity<>(affectations, HttpStatus.OK);
	}


	@GetMapping("/all")
	public List<AffectationsDTO> getAllActivites() {
		return affectationServices.getAllAffectation();
	}

	 @GetMapping("/allPagable")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
	{
		PageableResponseDTO affectations = affectationServices.getAllAffectationPagebal(pageNo,pageSize);
		return new ResponseEntity<>(affectations , HttpStatus.OK);
	}

	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		  affectationServices.deleteAffectationStatut(id);
		 return new ResponseEntity<>(new MessageResponse("Affectation supprimée.","success") , HttpStatus.OK);
	 }

	 @PostMapping
	 public ResponseEntity<?> addActivite(@Valid @RequestBody AffectationsDTO affectationsDTO)
	 {

		if(affectationsRepository.existsByLibelleAdd(affectationsDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
		}

		affectationServices.addAffectation(affectationsDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Affectation ajoutée.","success"));
	 }

	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id, @Valid @RequestBody AffectationsDTO affectationsDTO) {

			if(affectationsRepository.existsByLibelleModif(affectationsDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
			}
			if(affectationsDTO.getStatut() == null || affectationsDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
			}
	        affectationServices.updateAffectation(id, affectationsDTO) ;
	        return new ResponseEntity<>(new MessageResponse("Affectation modifiée.","success"),HttpStatus.OK);
	   }

	 @GetMapping("/listAffectations")
		public List<ListApis> getAllActivitesApis() {
			List<AffectationsDTO> activites  = affectationServices.getAllAffectation();
			List<ListApis> listApis = activites.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}

		private ListApis mapToApisList(AffectationsDTO activites)
		{
			ListApis listApis = new ListApis();
			listApis.setId(activites.getId());
			listApis.setCode(activites.getCode());
			listApis.setLibelle(activites.getLibelle());
			return listApis;
		}
}
