package com.exemple.security.services.Interaction;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.Clients;
import com.exemple.security.entity.Interaction;
import com.exemple.security.entity.Numero;
import com.exemple.security.entity.TypesInteraction;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.InteractionDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.ClientsRepository;
import com.exemple.security.repository.InteractionRepository;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.repository.TypesInteractionRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;

@Service
public class InteractionService implements InInteractionService{

	@Autowired
	private InteractionRepository interactionRepository;

	@Autowired
	private ClientsRepository clientsRepository;

	@Autowired
	private TypesInteractionRepository typesInteractionRepository;

	@Autowired
	private InNumeroService numeroService;

	@Autowired
	private NumeroRepository numeroRepository;

	@Override
	public List<Interaction> getAllInteraction() {
		List<Interaction> interactions = interactionRepository.findAllWithStatus();
		return interactions;
	}

	@Override
	public InteractionDTO getInteraction(Long id) {
		Interaction interaction = interactionRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Interaction", "id", id));
		return mapToDTO(interaction);
	}

	@Override
	public PageableResponseDTO getAllInteractionPagebal(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Interaction> v = interactionRepository.findallStatutsPa(pageable);
		List<InteractionDTO> interactionDTOs = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(interactionDTOs);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(v.getTotalElements());
		pageableResponseDTO.setTotlaPages(v.getTotalPages());
		pageableResponseDTO.setLast(v.isLast());
		return pageableResponseDTO;
	}

	@Override
	public Interaction addInteraction(InteractionDTO interactionDTO) {
		 Clients clients = clientsRepository.findByIdStatut(interactionDTO.getIdClients()).orElseThrow(()-> new ResourceNotFoundException("Client", "id",interactionDTO.getIdClients()));
		 TypesInteraction typesInteraction = typesInteractionRepository.findByIdStatut(interactionDTO.getIdTypesInteraction()).orElseThrow(()-> new ResourceNotFoundException("Type interaction", "id", interactionDTO.getIdTypesInteraction()));

		 Interaction addInteraction = new Interaction();

		List<Numero> numeros = numeroRepository.findByCode("Interaction");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");

			numeroRepository.save(numero);
			addInteraction.setCode(numeroService.genrateNumero(numero));
		}

		addInteraction.setClients(clients);
		addInteraction.setTypesInteraction(typesInteraction);

		addInteraction.setLibelle(interactionDTO.getLibelle());
		addInteraction.setStatut(GlobalConstants.STATUT_ACTIF);
		addInteraction.setDateCreation(new Date());

		addInteraction.setNotes(interactionDTO.getNotes());
		addInteraction.setDateActions(interactionDTO.getDateAction());

		return interactionRepository.save(addInteraction);
	}

	@Override
	public Interaction deleteInteraction(Long id) {
		Interaction interaction = interactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interaction", "id", id));
		interaction.setStatut(GlobalConstants.STATUT_DELETE);
		interaction.setDateDesactivation(new Date());
		return  interactionRepository.save(interaction);
	}

	@Override
	public Interaction updateInteractions(Long id, InteractionDTO interactionDTO) {
		Clients clients = clientsRepository.findByIdStatut(interactionDTO.getIdClients()).orElseThrow(()-> new ResourceNotFoundException("Client", "id",interactionDTO.getIdClients()));
		TypesInteraction typesInteraction = typesInteractionRepository.findByIdStatut(interactionDTO.getIdTypesInteraction()).orElseThrow(()-> new ResourceNotFoundException("Type interaction", "id", interactionDTO.getIdTypesInteraction()));


		Interaction interaction = interactionRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Interaction", "id", id));

		interaction.setLibelle(interactionDTO.getLibelle());
		interaction.setStatut(GlobalConstants.getStatusFromDescription(interactionDTO.getStatut()));
		interaction.setDateModification(new Date());

		interaction.setClients(clients);
		interaction.setTypesInteraction(typesInteraction);

		interaction.setNotes(interactionDTO.getNotes());
		interaction.setDateActions(interactionDTO.getDateAction());

		return interactionRepository.save(interaction);
	}


	private InteractionDTO mapToDTO(Interaction x)
	{
		InteractionDTO dto = new InteractionDTO();
		dto.setId(x.getId());
		dto.setCode(x.getCode());
		dto.setLibelle(x.getLibelle());
		dto.setDateCreation(x.getDateCreation());
		dto.setDateModification(x.getDateModification());
		dto.setDateDesactivation(x.getDateDesactivation());
		dto.setStatut(GlobalConstants.getStatusDescription(x.getStatut()));

		dto.setNotes(x.getNotes());
		dto.setDateAction(x.getDateActions());

		dto.setIdClients(x.getClients().getId());
		dto.setLibelleClients(x.getClients().getLibelle());
		dto.setIdTypesInteraction(x.getTypesInteraction().getId());
		dto.setLibelleTypesInteraction(x.getTypesInteraction().getLibelle());

		return dto;
	}


}
