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
import com.exemple.security.entity.Interaction;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.InteractionDTO;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.InteractionRepository;
import com.exemple.security.services.Interaction.InInteractionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/Interaction")
@RequiredArgsConstructor
public class InteractionController {


	@Autowired
	private InInteractionService interactionService;

	@Autowired
	private InteractionRepository interactionRepository;


	@GetMapping("/{id}")
	private ResponseEntity<InteractionDTO> getTypesInteraction(@PathVariable Long id)
	{
		InteractionDTO interactionDTO = interactionService.getInteraction(id);
		return new ResponseEntity<>(interactionDTO, HttpStatus.OK);
	}


	 @GetMapping("/allPagable")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
	{
		PageableResponseDTO activities = interactionService.getAllInteractionPagebal(pageNo,pageSize);
		return new ResponseEntity<>(activities , HttpStatus.OK);
	}

	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		 interactionService.deleteInteraction(id);
		 return new ResponseEntity<>(new MessageResponse("Interaction supprimée.","success") , HttpStatus.OK);
	 }

	 @PostMapping
	 public ResponseEntity<?> addActivite(@Valid @RequestBody InteractionDTO interactionDTO)
	 {

		if(interactionRepository.existsByLibelleAdd(interactionDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
		}

		interactionService.addInteraction(interactionDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Interaction ajoutée.","success"));
	 }

	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id, @Valid @RequestBody InteractionDTO interactionDTO) {

			if(interactionRepository.existsByLibelleModif(interactionDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
			}
			if(interactionDTO.getStatut() == null || interactionDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
			}
			interactionService.updateInteractions(id, interactionDTO) ;
	        return new ResponseEntity<>(new MessageResponse("Interaction modifiée","success"),HttpStatus.OK);
	    }


	 @GetMapping("/listTypesClients")
		public List<ListApis> getAllActivitesApis() {
			List<Interaction> typesClients  = interactionService.getAllInteraction();
			List<ListApis> listApis = typesClients.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}

		private ListApis mapToApisList(Interaction interaction)
		{
			ListApis listApis = new ListApis();
			listApis.setId(interaction.getId());
			listApis.setCode(interaction.getCode());
			listApis.setLibelle(interaction.getLibelle());
			return listApis;
		}
}
