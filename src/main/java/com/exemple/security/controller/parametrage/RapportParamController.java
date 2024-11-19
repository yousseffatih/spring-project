package com.exemple.security.controller.parametrage;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.client.RestTemplate;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.RapportsParams;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.RapportsParamsDTO;
import com.exemple.security.repository.RapportParamsRepository;
import com.exemple.security.services.parametrage.RapportParams.InRapportParamsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/RapportParam")
@RequiredArgsConstructor
public class RapportParamController {



	@Autowired
	private InRapportParamsService rapportParamsService;

	@Autowired
	private RapportParamsRepository rapportParamsRepository;
	


	
	
	@GetMapping("/{id}")
	private ResponseEntity<RapportsParamsDTO> getAllParamsByIdRapport(@PathVariable Long id)
	{
		RapportsParamsDTO rapportsParamsDTO = rapportParamsService.getRapportsParams(id);
		return new ResponseEntity<>(rapportsParamsDTO, HttpStatus.OK);
	}


	 @GetMapping("/idRapport={idRapport}")
	public ResponseEntity<?> getAllClientsContactsPageable(
			@PathVariable Long idRapport)
	{
		List<RapportsParamsDTO> page = rapportParamsService.getAllRapportsParamsPagebal(idRapport);
		return new ResponseEntity<>(page , HttpStatus.OK);
	}

	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteClientsContactsStatus(@PathVariable Long id)
	 {
		 rapportParamsService.deleteRapportsParams(id);
		 return new ResponseEntity<>(new MessageResponse("Contacts client supprimés.","success") , HttpStatus.OK);
	 }

	 @PostMapping
	 public ResponseEntity<?> addClientsContacts(@Valid @RequestBody RapportsParamsDTO rapportsParamsDTO)
	 {

		if(rapportParamsRepository.existsByLibelleAdd(rapportsParamsDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
		}
		if(rapportParamsRepository.existsByOrderAdd(rapportsParamsDTO.getOrderParam()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("L'order existe déjà !" , "warning"));

		}

		rapportParamsService.addRapportsParams(rapportsParamsDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Contacts client ajouté.","success"));
	 }

	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateClientsContacts(@PathVariable Long id, @Valid @RequestBody RapportsParamsDTO rapportsParamsDTO) {

			if(rapportParamsRepository.existsByLibelleModif(rapportsParamsDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
			}
			if(rapportsParamsDTO.getStatut() == null || rapportsParamsDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
			}
			if(rapportParamsRepository.existsByOrderModif(rapportsParamsDTO.getOrderParam(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("L'order existe déjà !" , "warning"));
			}

			rapportParamsService.updateTypesClients(id, rapportsParamsDTO) ;
	        return new ResponseEntity<>(new MessageResponse("Contacts client modifié","success"),HttpStatus.OK);
	    }



	 @GetMapping("/listRParams")
		public List<ListApis> getAllClientsContactsApis() {
			List<RapportsParams> rapportsParams  = rapportParamsService.getAllRapportsParams();
			List<ListApis> listApis = rapportsParams.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}

		private ListApis mapToApisList(RapportsParams clientsContacts)
		{
			ListApis listApis = new ListApis();
			listApis.setId(clientsContacts.getId());
			listApis.setCode(clientsContacts.getCode());
			listApis.setLibelle(clientsContacts.getLibelle());
			return listApis;
		}
		
		
}
