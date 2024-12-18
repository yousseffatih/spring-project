package com.exemple.security.services.parametrage.TypeCourriers;

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
import com.exemple.security.entity.TypeCourriers;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.TypeCourriersDTO;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.repository.TypeCourriersRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;

@Service
public class TypeCourriersServicesImp implements InTypeCourriersServices{

	@Autowired
	private TypeCourriersRepository typeCourriersRepository;

	@Autowired
	private InNumeroService numeroService;

	@Autowired
	private NumeroRepository numeroRepository;

	public TypeCourriersServicesImp (TypeCourriersRepository typeCourriersRepository) {
		this.typeCourriersRepository = typeCourriersRepository;
	}


	@Override
	public List<TypeCourriers> getAllTypeCourriers() {
		List<TypeCourriers> typeCourriers = typeCourriersRepository.findAllWithStatus();
		return typeCourriers;
	}

	@Override
	public TypeCourriersDTO getTypeCourriers(Long id)
	{
		TypeCourriers typeCourriers = typeCourriersRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("TypeCourriers", "id", id));
		return mapToDTO(typeCourriers);
	}

	@Override
	public PageableResponseDTO getAllTypeCourriersPagebal(int pageNo , int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<TypeCourriers> v = typeCourriersRepository.findallStatutsPa(pageable);
		List<TypeCourriersDTO> typeCourriers = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(typeCourriers);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(v.getTotalElements());
		pageableResponseDTO.setTotlaPages(v.getTotalPages());
		pageableResponseDTO.setLast(v.isLast());
		return pageableResponseDTO;
	}

	@Override
	public TypeCourriers addTypeCourriers(TypeCourriersDTO typeCourriersDTO) {
		TypeCourriers addTypeCourriers=new TypeCourriers();

		List<Numero> numeros = numeroRepository.findByCode("TypeCourriers");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");

			numeroRepository.save(numero);
			addTypeCourriers.setCode(numeroService.genrateNumero(numero));
		}

		addTypeCourriers.setLibelle(typeCourriersDTO.getLibelle());
		addTypeCourriers.setStatut(GlobalConstants.STATUT_ACTIF);
		addTypeCourriers.setDateCreation(new Date());
		return typeCourriersRepository.save(addTypeCourriers);
	}

	@Override
	public TypeCourriers deleteTypeCourriersStatut(Long id) {
		TypeCourriers typeCourriers = typeCourriersRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("TypeCourriers", "id", id));
		 typeCourriers.setStatut(GlobalConstants.STATUT_DELETE);
		 typeCourriers.setDateDesactivation(new Date());
	     return  typeCourriersRepository.save(typeCourriers);
	}

	@Override
	public TypeCourriers updateTypeCourriers(Long id, TypeCourriersDTO typeCourriersDTO) {
		TypeCourriers typeCourriers = typeCourriersRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("TypeCourriers", "id", id));
		typeCourriers.setLibelle(typeCourriersDTO.getLibelle());
		typeCourriers.setDateModification(new Date());
		typeCourriers.setStatut(GlobalConstants.getStatusFromDescription(typeCourriersDTO.getStatut()));

		return typeCourriersRepository.save(typeCourriers);
	}

	private TypeCourriersDTO mapToDTO(TypeCourriers x)
	{
		TypeCourriersDTO dto = new TypeCourriersDTO();
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
