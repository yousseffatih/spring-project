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
import com.exemple.security.entity.Activites;
import com.exemple.security.entity.TypesInteraction;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.TypesInteractionDTO;
import com.exemple.security.repository.TypesInteractionRepository;
import com.exemple.security.services.TypeInstraction.InTypeInstractionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/TypesInteraction")
@RequiredArgsConstructor
public class TypesInteractionController {



	@Autowired
	private InTypeInstractionService typeInstractionService;

	@Autowired
	private TypesInteractionRepository typesInteractionRepository;


	@GetMapping("/{id}")
	private ResponseEntity<TypesInteractionDTO> getTypesInteraction(@PathVariable Long id)
	{
		TypesInteractionDTO typesInteraction = typeInstractionService.getTypesInteraction(id);
		 
		return new ResponseEntity<>(typesInteraction, HttpStatus.OK);
	}




	 @GetMapping("/allPagable")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
	{
		PageableResponseDTO activities = typeInstractionService.getAllTypesInteractionPagebal(pageNo,pageSize);
		return new ResponseEntity<>(activities , HttpStatus.OK);
	}

	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		 typeInstractionService.deleteTypesInteractionStatut(id);
		 return new ResponseEntity<>(new MessageResponse("Type Instraction supprimée.","success") , HttpStatus.OK);
	 }

	 @PostMapping
	 public ResponseEntity<?> addActivite(@Valid @RequestBody TypesInteractionDTO typesInteractionDTO)
	 {

		if(typesInteractionRepository.existsByLibelleAdd(typesInteractionDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
		}

		typeInstractionService.addActivites(typesInteractionDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Type Instraction ajoutée.","success"));
	 }

	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id, @Valid @RequestBody TypesInteractionDTO typesInteractionDTO) {
		    System.out.println("this is th problem :"+ typesInteractionDTO.toString());

			if(typesInteractionRepository.existsByLibelleModif(typesInteractionDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
			}
			if(typesInteractionDTO.getStatut() == null || typesInteractionDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
			}
			typeInstractionService.updateTypesInteraction(id, typesInteractionDTO) ;
	        return new ResponseEntity<>(new MessageResponse("Type Instraction modifiée","success"),HttpStatus.OK);
	    }


	 @GetMapping("/listActivite")
		public List<ListApis> getAllActivitesApis() {
			List<TypesInteraction> activites  = typeInstractionService.getAllTypesInteraction();
			List<ListApis> listApis = activites.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}

		private ListApis mapToApisList(TypesInteraction typesInteraction)
		{
			ListApis listApis = new ListApis();
			listApis.setId(typesInteraction.getId());
			listApis.setCode(typesInteraction.getCode());
			listApis.setLibelle(typesInteraction.getLibelle());
			return listApis;
		}
}
