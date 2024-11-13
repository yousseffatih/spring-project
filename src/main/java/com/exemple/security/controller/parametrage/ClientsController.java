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
import com.exemple.security.entity.Clients;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ClientsDTO;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.ClientsRepository;
import com.exemple.security.services.clients.InClientsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/Clients")
@RequiredArgsConstructor
public class ClientsController {


	@Autowired
	private InClientsService clientsService;

	@Autowired
	private ClientsRepository clientsRepository;


	@GetMapping("/{id}")
	private ResponseEntity<ClientsDTO> getTypesInteraction(@PathVariable Long id)
	{
		ClientsDTO clientsDTO = clientsService.getClients(id);
		return new ResponseEntity<>(clientsDTO, HttpStatus.OK);
	}


	 @GetMapping("/allPagable")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
	{
		PageableResponseDTO activities = clientsService.getAllClientsPagebal(pageNo,pageSize);
		return new ResponseEntity<>(activities , HttpStatus.OK);
	}

	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		 clientsService.deleteClients(id);
		 return new ResponseEntity<>(new MessageResponse("Client supprimé.","success") , HttpStatus.OK);
	 }

	 @PostMapping
	 public ResponseEntity<?> addActivite(@Valid @RequestBody ClientsDTO clientsDTO)
	 {

		if(clientsRepository.existsByLibelleAdd(clientsDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
		}

		clientsService.addClients(clientsDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Client ajouté.","success"));
	 }

	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id, @Valid @RequestBody ClientsDTO clientsDTO) {

			if(clientsRepository.existsByLibelleModif(clientsDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
			}
			if(clientsDTO.getStatut() == null || clientsDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
			}
			clientsService.updateClients(id, clientsDTO) ;
	        return new ResponseEntity<>(new MessageResponse("Client modifié","success"),HttpStatus.OK);
	    }


	 @GetMapping("/listClients")
		public List<ListApis> getAllActivitesApis() {
			List<Clients> typesClients  = clientsService.getAllClients();
			List<ListApis> listApis = typesClients.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}

		private ListApis mapToApisList(Clients clients)
		{
			ListApis listApis = new ListApis();
			listApis.setId(clients.getId());
			listApis.setCode(clients.getCode());
			listApis.setLibelle(clients.getLibelle());
			return listApis;
		}
}
