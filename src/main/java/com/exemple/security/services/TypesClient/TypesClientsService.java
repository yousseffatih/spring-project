package com.exemple.security.services.TypesClient;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.Numero;
import com.exemple.security.entity.TypesClients;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.TypesClientsDTO;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.repository.TypesClientsRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;

@Service
public class TypesClientsService implements InTypesClientService{
	
	@Autowired
	private TypesClientsRepository typesClientsRepository;

	@Autowired
	private InNumeroService numeroService;

	@Autowired
	private NumeroRepository numeroRepository;

	@Override
	public List<TypesClients> getAllTypesClients() {
		List<TypesClients> typesInteractions = typesClientsRepository.findAllWithStatus();
		return typesInteractions;
	}

	@Override
	public TypesClientsDTO getTypesClients(Long id) {
		TypesClients typesClients = typesClientsRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Type Client", "id", id));
		return mapToDTO(typesClients);
	}

	@Override
	public PageableResponseDTO getAllTypesClientsPagebal(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<TypesClients> v = typesClientsRepository.findallStatutsPa(pageable);
		List<TypesClientsDTO> typesInteractionDTOs = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
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
	public TypesClients addTypesClients(TypesClientsDTO typesInteractionDTO) {
		TypesClients addTypesClients=new TypesClients();

		List<Numero> numeros = numeroRepository.findByCode("TypesClients");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");

			numeroRepository.save(numero);
			addTypesClients.setCode(numeroService.genrateNumero(numero));
		}

		addTypesClients.setLibelle(typesInteractionDTO.getLibelle());
		addTypesClients.setStatut(GlobalConstants.STATUT_ACTIF);
		addTypesClients.setDateCreation(new Date());
		return typesClientsRepository.save(addTypesClients);
	}

	@Override
	public TypesClients deleteTypesClients(Long id) {
		TypesClients typesClients = typesClientsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type Client", "id", id));
		typesClients.setStatut(GlobalConstants.STATUT_DELETE);
		typesClients.setDateDesactivation(new Date());
		return  typesClientsRepository.save(typesClients);
	}

	@Override
	public TypesClients updateTypesClients(Long id, TypesClientsDTO typesInteractionDTO) {
		TypesClients typesClients = typesClientsRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("TypesInteraction", "id", id));
		typesClients.setLibelle(typesInteractionDTO.getLibelle());
		typesClients.setStatut(typesInteractionDTO.getStatut().equals("actif")? GlobalConstants.STATUT_ACTIF : GlobalConstants.STATUT_INACTIF);
		typesClients.setDateModification(new Date());
		return typesClientsRepository.save(typesClients);
	}
	
	private TypesClientsDTO mapToDTO(TypesClients x)
	{
		TypesClientsDTO dto = new TypesClientsDTO();
		dto.setId(x.getId());
		dto.setCode(x.getCode());
		dto.setLibelle(x.getLibelle());
		dto.setDateCreation(x.getDateCreation());
		dto.setDateModification(x.getDateModification());
		dto.setDateDesactivation(x.getDateDesactivation());
		dto.setStatut(GlobalConstants.getStatusDescription(x.getStatut()));
		return dto;
	}
	
}
