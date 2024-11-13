package com.exemple.security.services.clients;

import java.util.List;

import com.exemple.security.entity.Clients;
import com.exemple.security.playload.dto.ClientsDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;

public interface InClientsService {

	public List<Clients> getAllClients();

	public ClientsDTO getClients(Long id);

	public PageableResponseDTO getAllClientsPagebal(int pageNo, int pageSize);

	public ClientsDTO addClients(ClientsDTO clientsContactsDTO);

	public ClientsDTO deleteClients(Long id);

	public ClientsDTO updateClients(Long id, ClientsDTO clientsContactsDTO);
}
