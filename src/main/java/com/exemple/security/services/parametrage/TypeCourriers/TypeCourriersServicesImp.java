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
import com.exemple.security.entity.Activites;
import com.exemple.security.entity.TypeCourriers;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.ActivitesDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.TypeCourriersDTO;
import com.exemple.security.repository.TypeCourriersRepository;

@Service
public class TypeCourriersServicesImp implements InTypeCourriersServices{

	@Autowired
	private TypeCourriersRepository typeCourriersRepository;
	
	public TypeCourriersServicesImp (TypeCourriersRepository typeCourriersRepository) {
		this.typeCourriersRepository = typeCourriersRepository;
	}

	
	@Override
	public List<TypeCourriers> getAllTypeCourriers() {
		List<TypeCourriers> typeCourriers = typeCourriersRepository.findAllWithStatus();
		return typeCourriers;
	}
	
	@Override
	public TypeCourriers getTypeCourriers(Long id)
	{
		TypeCourriers typeCourriers = typeCourriersRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("TypeCourriers", "id", id));
		return typeCourriers;
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
		addTypeCourriers.setCode(typeCourriersDTO.getCode());
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
		typeCourriers.setCode(typeCourriersDTO.getCode());
		typeCourriers.setLibelle(typeCourriersDTO.getLibelle());
		typeCourriers.setDateModification(new Date());
		typeCourriers.setStatut(typeCourriersDTO.getStatut().equals("actif")? GlobalConstants.STATUT_ACTIF : GlobalConstants.STATUT_INACTIF);

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
		dto.setStatut(x.getStatut().equals("1")? "actif" : "inactif");
		return dto;
	}
}
