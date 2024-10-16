package com.exemple.security.services.parametrage.Activities;


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
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.ActivitesDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.ActivitesRepository;




@Service
public class ActivitiesServicesImp implements InActivitiesServices{
	
	@Autowired
	private ActivitesRepository activitesRepository;
	
	public ActivitiesServicesImp (ActivitesRepository activitesRepository) {
		this.activitesRepository = activitesRepository;
	}

	
	@Override
	public List<Activites> getAllActivities() {
		List<Activites> activites = activitesRepository.findAllWithStatus();
		return activites;
	}
	
	@Override
	public Activites getActivities(Long id)
	{
		Activites activites = activitesRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Activites", "id", id));
		return activites;
	}
	
	@Override
	public PageableResponseDTO getAllActivitesPagebal(int pageNo , int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize); 
		Page<Activites> v = activitesRepository.findallStatutsPa(pageable);
		List<ActivitesDTO> activites = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(activites);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(v.getTotalElements());
		pageableResponseDTO.setTotlaPages(v.getTotalPages());
		pageableResponseDTO.setLast(v.isLast());
		return pageableResponseDTO;
	}
	
	@Override
	public Activites addActivites(ActivitesDTO activites) {
		Activites addActivite=new Activites();
		addActivite.setCode(activites.getCode());
		addActivite.setLibelle(activites.getLibelle());
		addActivite.setStatut(GlobalConstants.STATUT_ACTIF);
		addActivite.setDateCreation(new Date());
		return activitesRepository.save(addActivite);
	}
	
	@Override
	public Activites deleteActivitesStatut(Long id) {
		 Activites activites = activitesRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Activite", "id", id));
		 activites.setStatut(GlobalConstants.STATUT_DELETE);
		 activites.setDateDesactivation(new Date());
	     return  activitesRepository.save(activites);
	}
	
	@Override
	public Activites updateActivites(Long idActivite, ActivitesDTO activitesDTO) {
		Activites activites = activitesRepository.findById(idActivite).orElseThrow(() ->  new ResourceNotFoundException("Activite", "id", idActivite));
		activites.setCode(activitesDTO.getCode());
		activites.setLibelle(activitesDTO.getLibelle());
		activites.setStatut(activitesDTO.getStatut().equals("actif")? GlobalConstants.STATUT_ACTIF : GlobalConstants.STATUT_INACTIF);
		activites.setDateModification(new Date());
		return activitesRepository.save(activites);
	}
	
	private ActivitesDTO mapToDTO(Activites x)
	{
		ActivitesDTO dto = new ActivitesDTO();
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
