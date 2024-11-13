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
import com.exemple.security.entity.ClientsContacts;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ClientsContactsDTO;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.ClientsContactsRepository;
import com.exemple.security.services.ClientsContacts.InClientContactsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ClientsContacts")
@RequiredArgsConstructor
public class ClientsContactsController {


	@Autowired
	private InClientContactsService clientContactsService;

	@Autowired
	private ClientsContactsRepository clientsContactsRepository;


	@GetMapping("/{id}")
	private ResponseEntity<ClientsContactsDTO> getTypesInteraction(@PathVariable Long id)
	{
		ClientsContactsDTO clientsContactsDTO = clientContactsService.getClientContacts(id);
		return new ResponseEntity<>(clientsContactsDTO, HttpStatus.OK);
	}


	 @GetMapping("/allPagable/idClient={idClient}")
	public ResponseEntity<PageableResponseDTO> getAllClientsContactsPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize,
			@PathVariable Long idClient)
	{
		PageableResponseDTO page = clientContactsService.getAllClientContactsPagebal(pageNo,pageSize, idClient);
		return new ResponseEntity<>(page , HttpStatus.OK);
	}

	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteClientsContactsStatus(@PathVariable Long id)
	 {
		 clientContactsService.deleteTypesClients(id);
		 return new ResponseEntity<>(new MessageResponse("Contacts client supprimés.","success") , HttpStatus.OK);
	 }

	 @PostMapping
	 public ResponseEntity<?> addClientsContacts(@Valid @RequestBody ClientsContactsDTO clientsContactsDTO)
	 {

		if(clientsContactsRepository.existsByLibelleAdd(clientsContactsDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
		}

		clientContactsService.addClientContacts(clientsContactsDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Contacts client ajouté.","success"));
	 }

	@PutMapping("/{id}")
    public ResponseEntity<?> updateClientsContacts(@PathVariable Long id, @Valid @RequestBody ClientsContactsDTO clientsContactsDTO) {

		if(clientsContactsRepository.existsByLibelleModif(clientsContactsDTO.getLibelle(), id))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
		}
		if(clientsContactsDTO.getStatut() == null || clientsContactsDTO.getStatut() == "")
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
		}
		clientContactsService.updateClientContacts(id, clientsContactsDTO) ;
        return new ResponseEntity<>(new MessageResponse("Contacts client modifié","success"),HttpStatus.OK);
    }



	@GetMapping("/listClientsContacts")
	public List<ListApis> getAllClientsContactsApis() {
		List<ClientsContacts> clientsContacts  = clientContactsService.getAllClientContacts();
		List<ListApis> listApis = clientsContacts.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
		return listApis;
	}

	private ListApis mapToApisList(ClientsContacts clientsContacts)
	{
		ListApis listApis = new ListApis();
		listApis.setId(clientsContacts.getId());
		listApis.setCode(clientsContacts.getCode());
		listApis.setLibelle(clientsContacts.getLibelle());
		return listApis;
	}
}
