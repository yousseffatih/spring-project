package com.exemple.security.services.ClientsContacts;

import java.util.List;

import com.exemple.security.entity.ClientsContacts;
import com.exemple.security.playload.dto.ClientsContactsDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;

public interface InClientContactsService {

	public List<ClientsContacts> getAllClientContacts();

	public ClientsContactsDTO getClientContacts(Long id);

	public PageableResponseDTO getAllClientContactsPagebal(int pageNo, int pageSize, Long idClient);

	public ClientsContacts addClientContacts(ClientsContactsDTO clientsContactsDTO);

	public ClientsContacts deleteTypesClients(Long id);

	public ClientsContacts updateClientContacts(Long id, ClientsContactsDTO clientsContactsDTO);

}
