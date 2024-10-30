package com.exemple.security.services.TypeInstraction;

import java.util.List;

import com.exemple.security.entity.TypesInteraction;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.TypesInteractionDTO;

public interface InTypeInstractionService {

	public List<TypesInteraction> getAllTypesInteraction();

	public TypesInteractionDTO getTypesInteraction(Long id);

	public PageableResponseDTO getAllTypesInteractionPagebal(int pageNo, int pageSize);

	public TypesInteraction addActivites(TypesInteractionDTO typesInteractionDTO);

	public TypesInteraction deleteTypesInteractionStatut(Long id);

	public TypesInteraction updateTypesInteraction(Long idActivite, TypesInteractionDTO typesInteractionDTO);
}
