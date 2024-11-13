package com.exemple.security.services.ClientsContacts;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.Affectations;
import com.exemple.security.entity.Clients;
import com.exemple.security.entity.ClientsContacts;
import com.exemple.security.entity.Fonctions;
import com.exemple.security.entity.Numero;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.ClientsContactsDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.AffectationsRepository;
import com.exemple.security.repository.ClientsContactsRepository;
import com.exemple.security.repository.ClientsRepository;
import com.exemple.security.repository.FonctionsRepository;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;

@Service
public class ClientContactsService implements InClientContactsService {

	@Autowired
	private ClientsContactsRepository clientsContactsRepository;

	@Autowired
	private ClientsRepository clientsRepository;

	@Autowired
	private AffectationsRepository affectationsRepository;

	@Autowired
	private FonctionsRepository fondtFonctionsRepository;

	@Autowired
	private InNumeroService numeroService;

	@Autowired
	private NumeroRepository numeroRepository;

	@Override
	public List<ClientsContacts> getAllClientContacts() {
		List<ClientsContacts> clientsContacts = clientsContactsRepository.findAllWithStatus();
		return clientsContacts;
	}

	@Override
	public ClientsContactsDTO getClientContacts(Long id) {
		ClientsContacts clientsContacts = clientsContactsRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Client Contacts", "id", id));
		return mapToDTO(clientsContacts);
	}

	@Override
	public PageableResponseDTO getAllClientContactsPagebal(int pageNo, int pageSize , Long idClient) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<ClientsContacts> v = clientsContactsRepository.findallStatutsPa(pageable , idClient);
		List<ClientsContactsDTO> typesInteractionDTOs = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(typesInteractionDTOs);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(v.getTotalElements());
		pageableResponseDTO.setTotlaPages(v.getTotalPages());
		pageableResponseDTO.setLast(v.isLast());
		return pageableResponseDTO;
	}

	@Override
	public ClientsContacts addClientContacts(ClientsContactsDTO clientsContactsDTO) {
		 Fonctions fonctions = fondtFonctionsRepository.findByIdStatut(clientsContactsDTO.getIdFonctions()).orElseThrow(()-> new ResourceNotFoundException("Fonctions", "id", clientsContactsDTO.getIdFonctions()));
		 Affectations affectations = affectationsRepository.findByIdStatut(clientsContactsDTO.getIdAffectations()).orElseThrow(()-> new ResourceNotFoundException("Fonctions", "id", clientsContactsDTO.getIdAffectations()));

		 Clients client = clientsRepository.findByIdStatut(clientsContactsDTO.getIdClient()).orElseThrow(()-> new ResourceNotFoundException("Client", "id", clientsContactsDTO.getIdFonctions()));

		ClientsContacts addClientsContacts=new ClientsContacts();

		List<Numero> numeros = numeroRepository.findByCode("ClientsContacts");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");
			numeroRepository.save(numero);
			addClientsContacts.setCode(numeroService.genrateNumero(numero));
		}

		addClientsContacts.setAffectations(affectations);
		addClientsContacts.setFonctions(fonctions);

		addClientsContacts.setClients(client);

		addClientsContacts.setLibelle(clientsContactsDTO.getLibelle());
		addClientsContacts.setStatut(GlobalConstants.STATUT_ACTIF);
		addClientsContacts.setDateCreation(new Date());
		addClientsContacts.setNom(clientsContactsDTO.getNom());
		addClientsContacts.setPrenom(clientsContactsDTO.getPrenom());
		addClientsContacts.setEmail(clientsContactsDTO.getEmail());
		addClientsContacts.setTelephone(clientsContactsDTO.getTelephone());
		return clientsContactsRepository.save(addClientsContacts);
	}

	@Override
	public ClientsContacts deleteTypesClients(Long id) {
		ClientsContacts clientsContacts = clientsContactsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client Contacts", "id", id));
		clientsContacts.setStatut(GlobalConstants.STATUT_DELETE);
		clientsContacts.setDateDesactivation(new Date());
		return  clientsContactsRepository.save(clientsContacts);
	}

	@Override
	public ClientsContacts updateClientContacts(Long id, ClientsContactsDTO clientsContactsDTO) {
		 Fonctions fonctions = fondtFonctionsRepository.findByIdStatut(clientsContactsDTO.getIdFonctions()).orElseThrow(()-> new ResourceNotFoundException("Fonctions", "id", clientsContactsDTO.getIdFonctions()));
		 Affectations affectations = affectationsRepository.findByIdStatut(clientsContactsDTO.getIdAffectations()).orElseThrow(()-> new ResourceNotFoundException("Fonctions", "id", clientsContactsDTO.getIdAffectations()));

		ClientsContacts clientsContacts = clientsContactsRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Client Contacts", "id", id));

		 Clients client = clientsRepository.findByIdStatut(clientsContactsDTO.getIdClient()).orElseThrow(()-> new ResourceNotFoundException("Client", "id", clientsContactsDTO.getIdFonctions()));


		clientsContacts.setAffectations(affectations);
		clientsContacts.setFonctions(fonctions);
		clientsContacts.setClients(client);

		clientsContacts.setLibelle(clientsContactsDTO.getLibelle());
		clientsContacts.setStatut(GlobalConstants.getStatusFromDescription(clientsContactsDTO.getStatut()));
		clientsContacts.setDateModification(new Date());

		clientsContacts.setEmail(clientsContactsDTO.getEmail());
		clientsContacts.setPrenom(clientsContactsDTO.getPrenom());
		clientsContacts.setNom(clientsContactsDTO.getNom());
		clientsContacts.setTelephone(clientsContactsDTO.getTelephone());
		return clientsContactsRepository.save(clientsContacts);
	}

	private ClientsContactsDTO mapToDTO(ClientsContacts x)
	{
		ClientsContactsDTO dto = new ClientsContactsDTO();
		dto.setId(x.getId());
		dto.setCode(x.getCode());
		dto.setLibelle(x.getLibelle());
		dto.setDateCreation(x.getDateCreation());
		dto.setDateModification(x.getDateModification());
		dto.setDateDesactivation(x.getDateDesactivation());
		dto.setStatut(GlobalConstants.getStatusDescription(x.getStatut()));

		dto.setNom(x.getNom());
		dto.setPrenom(x.getPrenom());
		dto.setEmail(x.getEmail());
		dto.setTelephone(x.getTelephone());

		dto.setIdAffectations(x.getAffectations().getId());
		dto.setLibelleAffectations(x.getAffectations().getLibelle());

		dto.setIdFonctions(x.getFonctions().getId());
		dto.setLibelleFonctions(x.getFonctions().getLibelle());
		return dto;
	}

}
