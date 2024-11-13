package com.exemple.security.services.Interaction;

import java.util.List;

import com.exemple.security.entity.Interaction;
import com.exemple.security.playload.dto.InteractionDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;

public interface InInteractionService {

	public List<Interaction> getAllInteraction();

	public InteractionDTO getInteraction(Long id);

	public PageableResponseDTO getAllInteractionPagebal(int pageNo, int pageSize);

	public Interaction addInteraction(InteractionDTO interactionDTO);

	public Interaction deleteInteraction(Long id);

	public Interaction updateInteractions(Long id, InteractionDTO interactionDTO);
}
