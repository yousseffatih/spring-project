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
import com.exemple.security.entity.Fonctions;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.FonctionsDTO;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.FonctionsRepository;
import com.exemple.security.services.parametrage.fonctions.InFonctionsServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fonctions")
@RequiredArgsConstructor
public class FonctionsController {

	@Autowired
	private InFonctionsServices fonctionsServices;

	@Autowired
	private FonctionsRepository fonctionsRepository;



	@PostMapping
	private ResponseEntity<?> addFonctions(@Valid @RequestBody FonctionsDTO fonctionsDTO)
	{

		if(fonctionsRepository.existsByLibelleAdd(fonctionsDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libelle existe déjà !" , "warning"));
		}

		fonctionsServices.addFonctions(fonctionsDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Fonction ajoutée.","success"));
	}

	@GetMapping("/{id}")
	private ResponseEntity<Fonctions> getFonctions(@PathVariable Long id)
	{
		Fonctions fonctions = fonctionsServices.getFonctions(id);
		return new ResponseEntity<>(fonctions, HttpStatus.OK);
	}

	@GetMapping("/all")
	private ResponseEntity<List<Fonctions>> getAllVillesEntity()
	{
		List<Fonctions> fonctions = fonctionsServices.getAllFonctions();
		return new ResponseEntity<>(fonctions , HttpStatus.OK);
	}

	@PutMapping("/{id}")
    public ResponseEntity<?> updateVille(@PathVariable Long id,@Valid @RequestBody FonctionsDTO fonctionsDTO) {

		if(fonctionsRepository.existsByLibelleModif(fonctionsDTO.getLibelle(), id))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libelle existe déjà !" , "warning"));
		}
		if(fonctionsDTO.getStatut() == null || fonctionsDTO.getStatut() == "")
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
		}
		Fonctions updateFonctions = fonctionsServices.updateFonctions(id, fonctionsDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Fonction modifiée.","success"));
    }



	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteFonctionsStatus(@PathVariable Long id)
	 {
		 fonctionsServices.deleteFonctionsStatus(id);
		 return new ResponseEntity<>(new MessageResponse("Fonction supprimée.","success") , HttpStatus.OK);
	 }

	 @GetMapping("/allPagable")
		private ResponseEntity<PageableResponseDTO> getAllVillesPageable(
				@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
				@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
		{
			PageableResponseDTO fonctions = fonctionsServices.getAllFonctionsPagebal(pageNo,pageSize);
			return new ResponseEntity<>(fonctions , HttpStatus.OK);
		}

	 @GetMapping("/listFonctions")
		public List<ListApis> getAllActivitesApis() {
			List<Fonctions> activites  = fonctionsServices.getAllFonctions();
			List<ListApis> listApis = activites.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}

		private ListApis mapToApisList(Fonctions activites)
		{
			ListApis listApis = new ListApis();
			listApis.setId(activites.getId());
			listApis.setCode(activites.getCode());
			listApis.setLibelle(activites.getLibelle());
			return listApis;
		}




}
