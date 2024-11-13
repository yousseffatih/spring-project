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
import com.exemple.security.entity.TypesClients;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.TypesClientsDTO;
import com.exemple.security.repository.TypesClientsRepository;
import com.exemple.security.services.TypesClient.InTypesClientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/TypesClients")
@RequiredArgsConstructor
public class TypesClientsController {


	@Autowired
	private InTypesClientService typesClientService;

	@Autowired
	private TypesClientsRepository typesClientsRepository;


	@GetMapping("/{id}")
	private ResponseEntity<TypesClientsDTO> getTypesInteraction(@PathVariable Long id)
	{
		TypesClientsDTO typesClientsDTO = typesClientService.getTypesClients(id);
		return new ResponseEntity<>(typesClientsDTO, HttpStatus.OK);
	}


	 @GetMapping("/allPagable")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
	{
		PageableResponseDTO activities = typesClientService.getAllTypesClientsPagebal(pageNo,pageSize);
		return new ResponseEntity<>(activities , HttpStatus.OK);
	}

	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		 typesClientService.deleteTypesClients(id);
		 return new ResponseEntity<>(new MessageResponse("Type de client supprimé.","success") , HttpStatus.OK);
	 }

	 @PostMapping
	 public ResponseEntity<?> addActivite(@Valid @RequestBody TypesClientsDTO typeClientsDTO)
	 {

		if(typesClientsRepository.existsByLibelleAdd(typeClientsDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
		}

		typesClientService.addTypesClients(typeClientsDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Type de client ajouté.","success"));
	 }

	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id, @Valid @RequestBody TypesClientsDTO typesClientsDTO) {
		    System.out.println("this is th problem :"+ typesClientsDTO.toString());

			if(typesClientsRepository.existsByLibelleModif(typesClientsDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
			}
			if(typesClientsDTO.getStatut() == null || typesClientsDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
			}
			typesClientService.updateTypesClients(id, typesClientsDTO) ;
	        return new ResponseEntity<>(new MessageResponse("Type de client modifié","success"),HttpStatus.OK);
	    }


	 @GetMapping("/listTypesClients")
		public List<ListApis> getAllActivitesApis() {
			List<TypesClients> typesClients  = typesClientService.getAllTypesClients();
			List<ListApis> listApis = typesClients.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}

		private ListApis mapToApisList(TypesClients typesClients)
		{
			ListApis listApis = new ListApis();
			listApis.setId(typesClients.getId());
			listApis.setCode(typesClients.getCode());
			listApis.setLibelle(typesClients.getLibelle());
			return listApis;
		}
}
