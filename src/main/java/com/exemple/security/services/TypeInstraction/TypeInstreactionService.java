package com.exemple.security.services.TypeInstraction;


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
import com.exemple.security.entity.TypesInteraction;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.TypesInteractionDTO;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.repository.TypesInteractionRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;


@Service
public class TypeInstreactionService implements InTypeInstractionService{


	@Autowired
	private TypesInteractionRepository typesInteractionRepository;

	@Autowired
	private InNumeroService numeroService;

	@Autowired
	private NumeroRepository numeroRepository;

	@Override
	public List<TypesInteraction> getAllTypesInteraction() {
		List<TypesInteraction> typesInteractions = typesInteractionRepository.findAllWithStatus();
		return typesInteractions;
	}

	@Override
	public TypesInteractionDTO getTypesInteraction(Long id) {
		TypesInteraction typesInteraction = typesInteractionRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("TypesInteraction", "id", id));
		return mapToDTO(typesInteraction);
	}

	@Override
	public PageableResponseDTO getAllTypesInteractionPagebal(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<TypesInteraction> v = typesInteractionRepository.findallStatutsPa(pageable);
		List<TypesInteractionDTO> activites = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(activites);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(v.getTotalElements());
		pageableResponseDTO.setTotlaPages(v.getTotalPages());
		pageableResponseDTO.setLast(v.isLast());
		return pageableResponseDTO;
	}

	@Override
	public TypesInteraction addActivites(TypesInteractionDTO typesInteractionDTO) {
		TypesInteraction addTypesInteraction=new TypesInteraction();

		List<Numero> numeros = numeroRepository.findByCode("TypesInteraction");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");

			numeroRepository.save(numero);
			addTypesInteraction.setCode(numeroService.genrateNumero(numero));
		}

		addTypesInteraction.setLibelle(typesInteractionDTO.getLibelle());
		addTypesInteraction.setStatut(GlobalConstants.STATUT_ACTIF);
		addTypesInteraction.setDateCreation(new Date());
		return typesInteractionRepository.save(addTypesInteraction);
	}

	@Override
	public TypesInteraction deleteTypesInteractionStatut(Long id) {
		TypesInteraction typesInteraction = typesInteractionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TypesInteraction", "id", id));
		typesInteraction.setStatut(GlobalConstants.STATUT_DELETE);
		typesInteraction.setDateDesactivation(new Date());
		return  typesInteractionRepository.save(typesInteraction);
	}

	@Override
	public TypesInteraction updateTypesInteraction(Long id, TypesInteractionDTO typesInteractionDTO) {
		TypesInteraction typesInteraction = typesInteractionRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("TypesInteraction", "id", id));
		typesInteraction.setLibelle(typesInteractionDTO.getLibelle());
		typesInteraction.setStatut(typesInteractionDTO.getStatut().equals("actif")? GlobalConstants.STATUT_ACTIF : GlobalConstants.STATUT_INACTIF);
		typesInteraction.setDateModification(new Date());
		return typesInteractionRepository.save(typesInteraction);
	}

	private TypesInteractionDTO mapToDTO(TypesInteraction x)
	{
		TypesInteractionDTO dto = new TypesInteractionDTO();
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
