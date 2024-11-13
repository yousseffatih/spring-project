package com.exemple.security.services.clients;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.Activites;
import com.exemple.security.entity.Clients;
import com.exemple.security.entity.Employes;
import com.exemple.security.entity.Numero;
import com.exemple.security.entity.TypesClients;
import com.exemple.security.entity.Villes;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.ClientsDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.ActivitesRepository;
import com.exemple.security.repository.ClientsRepository;
import com.exemple.security.repository.EmployesRepository;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.repository.TypesClientsRepository;
import com.exemple.security.repository.VillesRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;

@Service
public class ClientsService implements InClientsService{

	@Autowired
	private ClientsRepository clientsRepository;

	@Autowired
	private VillesRepository villesRepository;

	@Autowired
	private ActivitesRepository activitesRepository;

	@Autowired
	private TypesClientsRepository typesClientsRepository;

	@Autowired
	private EmployesRepository employesRepository;

	@Autowired
	private InNumeroService numeroService;

	@Autowired
	private NumeroRepository numeroRepository;

	@Override
	public List<Clients> getAllClients() {
		List<Clients> clients = clientsRepository.findAllWithStatus();
		return clients;
	}

	@Override
	public ClientsDTO getClients(Long id) {
		Clients clients = clientsRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Client Contacts", "id", id));
		return mapToDTO(clients);
	}

	@Override
	public PageableResponseDTO getAllClientsPagebal(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Clients> v = clientsRepository.findallStatutsPa(pageable);
		List<ClientsDTO> typesInteractionDTOs = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
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
	public ClientsDTO addClients(ClientsDTO clientsDTO) {
		Villes ville = villesRepository.findByIdStatut(clientsDTO.getIdVilles()).orElseThrow(()-> new ResourceNotFoundException("Villes", "id", clientsDTO.getIdVilles()));
		Activites activites = activitesRepository.findByIdStatut(clientsDTO.getIdActivites()).orElseThrow(()-> new ResourceNotFoundException("Activite", "id", clientsDTO.getIdActivites()));
		TypesClients typesClients = typesClientsRepository.findByIdStatut(clientsDTO.getIdTypesClients()).orElseThrow(()-> new ResourceNotFoundException("Type Client", "id", clientsDTO.getIdTypesClients()));
		Employes employes = employesRepository.findByIdStatut(clientsDTO.getIdEmployes()).orElseThrow(()-> new ResourceNotFoundException("Employe", "id", clientsDTO.getIdEmployes()));

		Clients addClients=new Clients();

		List<Numero> numeros = numeroRepository.findByCode("Clients");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");

			numeroRepository.save(numero);
			addClients.setCode(numeroService.genrateNumero(numero));
		}


		addClients.setVilles(ville);
		addClients.setActivites(activites);
		addClients.setEmployes(employes);
		addClients.setTypesClients(typesClients);

		addClients.setLibelle(clientsDTO.getLibelle());
		addClients.setStatut(GlobalConstants.STATUT_ACTIF);
		addClients.setDateCreation(new Date());

		addClients.setNom(clientsDTO.getNom());
		addClients.setPrenom(clientsDTO.getPrenom());
		addClients.setEmail(clientsDTO.getEmail());
		addClients.setTelephone(clientsDTO.getTelephone());

		addClients.setAdresse(clientsDTO.getAdresse());
		addClients.setFax(clientsDTO.getFax());
		addClients.setIce(clientsDTO.getIce());
		addClients.setNature("Prospect");
		addClients.setRc(clientsDTO.getRc());

		return mapToDTO(clientsRepository.save(addClients));
	}

	@Override
	public ClientsDTO deleteClients(Long id) {
		Clients clients = clientsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
		clients.setStatut(GlobalConstants.STATUT_DELETE);
		clients.setDateDesactivation(new Date());
		return mapToDTO(clientsRepository.save(clients));
	}

	@Override
	public ClientsDTO updateClients(Long id, ClientsDTO clientsDTO) {

		Villes ville = villesRepository.findByIdStatut(clientsDTO.getIdVilles()).orElseThrow(()-> new ResourceNotFoundException("Villes", "id", clientsDTO.getIdVilles()));
		Activites activites = activitesRepository.findByIdStatut(clientsDTO.getIdActivites()).orElseThrow(()-> new ResourceNotFoundException("Activite", "id", clientsDTO.getIdActivites()));
		TypesClients typesClients = typesClientsRepository.findByIdStatut(clientsDTO.getIdTypesClients()).orElseThrow(()-> new ResourceNotFoundException("Type Client", "id", clientsDTO.getIdTypesClients()));
		Employes employes = employesRepository.findByIdStatut(clientsDTO.getIdEmployes()).orElseThrow(()-> new ResourceNotFoundException("Employe", "id", clientsDTO.getIdEmployes()));

		Clients clients = clientsRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Client Contacts", "id", id));

		clients.setVilles(ville);
		clients.setActivites(activites);
		clients.setEmployes(employes);
		clients.setTypesClients(typesClients);

		clients.setLibelle(clientsDTO.getLibelle());
		clients.setStatut(GlobalConstants.getStatusFromDescription(clientsDTO.getStatut()));
		clients.setDateModification(new Date());

		clients.setEmail(clientsDTO.getEmail());
		clients.setPrenom(clientsDTO.getPrenom());
		clients.setNom(clientsDTO.getNom());
		clients.setTelephone(clientsDTO.getTelephone());

		clients.setNom(clientsDTO.getNom());
		clients.setPrenom(clientsDTO.getPrenom());
		clients.setEmail(clientsDTO.getEmail());
		clients.setTelephone(clientsDTO.getTelephone());

		clients.setAdresse(clientsDTO.getAdresse());
		clients.setFax(clientsDTO.getFax());
		clients.setIce(clientsDTO.getIce());
		clients.setRc(clientsDTO.getRc());
		return mapToDTO(clientsRepository.save(clients));
	}

	private ClientsDTO mapToDTO(Clients x)
	{
		ClientsDTO dto = new ClientsDTO();
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
		dto.setAdresse(x.getAdresse());
		dto.setIce(x.getIce());
		dto.setRc(x.getRc());
		dto.setNature(x.getNature());
		dto.setFax(x.getFax());

		dto.setIdVilles(x.getVilles().getId());
		dto.setLibelleVilles(x.getVilles().getLibelle());

		dto.setIdActivites(x.getActivites().getId());
		dto.setLibelleActivites(x.getActivites().getLibelle());

		dto.setIdTypesClients(x.getTypesClients().getId());
		dto.setLibelleTypesClients(x.getTypesClients().getLibelle());

		dto.setIdEmployes(x.getEmployes().getId());
		dto.setLibelleEmployes(x.getEmployes().getLibelle());
		return dto;
	}


}
