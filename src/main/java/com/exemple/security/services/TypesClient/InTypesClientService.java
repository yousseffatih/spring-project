package com.exemple.security.services.TypesClient;

import java.util.List;

import com.exemple.security.entity.TypesClients;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.TypesClientsDTO;

public interface InTypesClientService {
	
	public List<TypesClients> getAllTypesClients();

	public TypesClientsDTO getTypesClients(Long id);

	public PageableResponseDTO getAllTypesClientsPagebal(int pageNo, int pageSize);

	public TypesClients addTypesClients(TypesClientsDTO typesInteractionDTO);

	public TypesClients deleteTypesClients(Long id);

	public TypesClients updateTypesClients(Long id, TypesClientsDTO typesInteractionDTO);
}
