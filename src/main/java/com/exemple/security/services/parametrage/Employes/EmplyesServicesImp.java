 package com.exemple.security.services.parametrage.Employes;

import java.util.ArrayList;
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
import com.exemple.security.entity.Employes;
import com.exemple.security.entity.Fonctions;
import com.exemple.security.entity.Numero;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.EmployesDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.AffectationsRepository;
import com.exemple.security.repository.EmployesRepository;
import com.exemple.security.repository.FonctionsRepository;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;


@Service
public class EmplyesServicesImp implements InEmployesService{


	@Autowired
	private EmployesRepository employesRepository;

	@Autowired
	private AffectationsRepository affectationsRepository;

	@Autowired
	private FonctionsRepository fonctionsRepository;


	@Autowired
	private InNumeroService numeroService;

	@Autowired
	private NumeroRepository numeroRepository;


	@Override
	public List<EmployesDTO> getAllEmployesDTOs() {
		List<Employes> employes = employesRepository.findAllWithStatus();
		List<EmployesDTO> employesDTOs = new ArrayList<>();
		employesDTOs = employes.stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		return employesDTOs;
	}

	@Override
	public EmployesDTO getEmployes(Long id)
	{
		Employes employes = employesRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Employes", "id", id));
		EmployesDTO affectationsDto = new EmployesDTO();
		affectationsDto = mapToDTO(employes);
		return affectationsDto;
	}

	@Override
	public PageableResponseDTO getAllEmployesPagebal(int pageNo , int pageSize) {

		Pageable pageable = PageRequest.of(pageNo, pageSize);

		Page<Employes> a = employesRepository.findallStatutsPa(pageable);

		List<EmployesDTO> employes = a.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());

		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();

		pageableResponseDTO.setContent(employes);

		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(a.getTotalElements());
		pageableResponseDTO.setTotlaPages(a.getTotalPages());
		pageableResponseDTO.setLast(a.isLast());
		return pageableResponseDTO;
	}

	@Override
	public Employes addEmployes(EmployesDTO employesDTO) {
		Employes addEmployes = new Employes();

		Fonctions fonctions = fonctionsRepository.findByIdStatut(employesDTO.getIdFonctions()).orElseThrow(() ->  new ResourceNotFoundException("Fonction", "id", employesDTO.getIdFonctions()));
		Affectations affectations = affectationsRepository.findByIdStatut(employesDTO.getIdAffectations()).orElseThrow(() ->  new ResourceNotFoundException("Affectation", "id", employesDTO.getIdAffectations()));

		addEmployes.setAffectations(affectations);
		addEmployes.setFonctions(fonctions);

		List<Numero> numeros = numeroRepository.findByCode("Employes");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");

			numeroRepository.save(numero);
			addEmployes.setCode(numeroService.genrateNumero(numero));
		}

		addEmployes.setLibelle(employesDTO.getNom() + " " + employesDTO.getPrenom());
		addEmployes.setStatut(GlobalConstants.STATUT_ACTIF);
		addEmployes.setDateCreation(new Date());
		addEmployes.setFax(employesDTO.getFax());
		addEmployes.setEmail(employesDTO.getEmail());
		addEmployes.setTelephone(employesDTO.getTelephone());
		addEmployes.setAdresse(employesDTO.getAdresse());
		addEmployes.setCin(employesDTO.getCin());
		addEmployes.setNom(employesDTO.getNom());
		addEmployes.setPrenom(employesDTO.getPrenom());
		addEmployes.setMatricule(employesDTO.getMatricule());
		return employesRepository.save(addEmployes);
	}

	@Override
	public Employes deleteEmployesStatut(Long id) {
		Employes employes = employesRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Employes", "id", id));
		 employes.setStatut(GlobalConstants.STATUT_DELETE);
		 employes.setDateDesactivation(new Date());
	     return  employesRepository.save(employes);
	}

	@Override
	public EmployesDTO updateEmployesDTO(Long idAff, EmployesDTO employesDTO) {
		Employes employes = employesRepository.findById(idAff).orElseThrow(() ->  new ResourceNotFoundException("Employes", "id", employesDTO.getId()));

		Fonctions fonctions = fonctionsRepository.findByIdStatut(employesDTO.getIdFonctions()).orElseThrow(() ->  new ResourceNotFoundException("Fonction", "id", employesDTO.getIdFonctions()));
		Affectations affectations = affectationsRepository.findByIdStatut(employesDTO.getIdAffectations()).orElseThrow(() ->  new ResourceNotFoundException("Affectation", "id", employesDTO.getIdAffectations()));

		employes.setAffectations(affectations);
		employes.setFonctions(fonctions);

		employes.setLibelle(employesDTO.getNom() + " " + employesDTO.getPrenom());
		employes.setDateModification(new Date());
		employes.setEmail(employesDTO.getEmail());
		employes.setTelephone(employesDTO.getTelephone());
		employes.setAdresse(employesDTO.getAdresse());
		employes.setCin(employesDTO.getCin());
		employes.setNom(employesDTO.getNom());
		employes.setPrenom(employesDTO.getPrenom());
		employes.setMatricule(employesDTO.getMatricule());
		employes.setFax(employesDTO.getFax());
		employes.setStatut(employesDTO.getStatut().equals("actif")? GlobalConstants.STATUT_ACTIF : GlobalConstants.STATUT_INACTIF);


		Employes employes2 = employesRepository.save(employes);
		EmployesDTO affectationsDTO2 = mapToDTO(employes2);
		return affectationsDTO2;
	}


	private EmployesDTO mapToDTO(Employes employes) {
		 EmployesDTO dto = new EmployesDTO();
        dto.setId(employes.getId());

        dto.setIdFonctions(employes.getFonctions().getId());
        dto.setLibelleFonction(employes.getFonctions().getLibelle());

        dto.setIdAffectations(employes.getAffectations().getId());
        dto.setLibelleAfficatation(employes.getAffectations().getLibelle());

        dto.setCode(employes.getCode());
        dto.setLibelle(employes.getLibelle());
        dto.setDateCreation(employes.getDateCreation());
        dto.setDateDesactivation(employes.getDateDesactivation() );
        dto.setDateModification(employes.getDateModification());
        dto.setAdresse(employes.getAdresse());
        dto.setEmail(employes.getEmail());
        dto.setFax(employes.getFax());
        dto.setTelephone(employes.getTelephone());
		dto.setStatut(employes.getStatut().equals("1")? "actif" : "inactif");
        dto.setCin(employes.getCin());
        dto.setNom(employes.getNom());
		dto.setPrenom(employes.getPrenom());
		dto.setMatricule(employes.getMatricule());
        return dto;
    }
}
