package com.exemple.security.services.parametrage.TypesRapports;

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
import com.exemple.security.entity.TypesRapport;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.TypesRapportDTO;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.repository.TypesRapportRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;

@Service
public class TypesRapportsService implements InTypesRapportsService{

	@Autowired
	private TypesRapportRepository typesRapportRepository;


	@Autowired
	private NumeroRepository numeroRepository;

	@Autowired
	private InNumeroService numeroService;

	@Override
	public List<TypesRapport> getAllTypesRapports() {
		List<TypesRapport> typesRapports = typesRapportRepository.findAllWithStatus();
		return typesRapports;
	}

	@Override
	public TypesRapportDTO getTypesRapports(Long id) {
		TypesRapport typesRapport = typesRapportRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Type de rapport", "id", id));
		return mapToDTO(typesRapport);
	}

	@Override
	public PageableResponseDTO getAllTypesRapportsPagebal(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<TypesRapport> v = typesRapportRepository.findallStatutsPa(pageable);
		List<TypesRapportDTO> typesRapportDTOs = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(typesRapportDTOs);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(v.getTotalElements());
		pageableResponseDTO.setTotlaPages(v.getTotalPages());
		pageableResponseDTO.setLast(v.isLast());
		return pageableResponseDTO;
	}

	@Override
	public TypesRapportDTO addTypesRapports(TypesRapportDTO typesRapportDTO) {
		TypesRapport addTypesRapport =new TypesRapport();

		List<Numero> numeros = numeroRepository.findByCode("TypesRapport");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");

			numeroRepository.save(numero);
			addTypesRapport.setCode(numeroService.genrateNumero(numero));
		}

		addTypesRapport.setLibelle(typesRapportDTO.getLibelle());
		addTypesRapport.setStatut(GlobalConstants.STATUT_ACTIF);
		addTypesRapport.setDateCreation(new Date());
		addTypesRapport.setProfile(typesRapportDTO.getProfile());
		return mapToDTO(typesRapportRepository.save(addTypesRapport));
	}

	@Override
	public TypesRapportDTO deleteTypesRapportsStatut(Long id) {
		TypesRapport typesRapport = typesRapportRepository.findByIdStatut(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Type de rapport", "id", id));
		typesRapport.setStatut(GlobalConstants.STATUT_DELETE);
		typesRapport.setDateDesactivation(new Date());
	     return  mapToDTO(typesRapportRepository.save(typesRapport));
	}

	@Override
	public TypesRapportDTO updateTypesRapports(Long idActivite, TypesRapportDTO typesRapportDTO) {

		TypesRapport typesRapport = typesRapportRepository.findByIdStatut(idActivite)
				.orElseThrow(() ->  new ResourceNotFoundException("Type de rapport", "id", idActivite));

		typesRapport.setLibelle(typesRapportDTO.getLibelle());
		typesRapport.setStatut(GlobalConstants.getStatusFromDescription(typesRapportDTO.getStatut()));
		typesRapport.setDateModification(new Date());
		typesRapport.setProfile(typesRapportDTO.getProfile());
		return mapToDTO(typesRapportRepository.save(typesRapport));
	}

	private TypesRapportDTO mapToDTO(TypesRapport x)
	{
		TypesRapportDTO dto = new TypesRapportDTO();
		dto.setId(x.getId());
		dto.setCode(x.getCode());
		dto.setLibelle(x.getLibelle());
		dto.setDateCreation(x.getDateCreation());
		dto.setDateModification(x.getDateModification());
		dto.setDateDesactivation(x.getDateDesactivation());
		dto.setStatut(GlobalConstants.getStatusDescription(x.getStatut()));

		dto.setProfile(x.getProfile());
		return dto;
	}

}
