package com.exemple.security.services.parametrage.organisme;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.Fonctions;
import com.exemple.security.entity.Organisme;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.FonctionsDTO;
import com.exemple.security.playload.dto.OrganismeDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.OrganismeRepository;

@Service
public class OrganismeServiceImp implements InOrganismeService{
	
	@Autowired
	private OrganismeRepository organismeRepository;
	
	

	
	@Override
	public List<Organisme> getAllOrganisme() {
		List<Organisme> organismes = organismeRepository.findAllWithStatus();
		return organismes;
	}
	
	@Override
	public Organisme getOrganisme(Long id)
	{
		Organisme organisme = organismeRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Organisme", "id", id));
		return organisme;
	}
	
	@Override
	public PageableResponseDTO getAllOrganismePagebal(int pageNo , int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize); 
		Page<Organisme> v = organismeRepository.findallStatutsPa(pageable);
		List<OrganismeDTO> organismes = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(organismes);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(v.getTotalElements());
		pageableResponseDTO.setTotlaPages(v.getTotalPages());
		pageableResponseDTO.setLast(v.isLast());
		return pageableResponseDTO;
	}
	
	@Override
	public Organisme addOrganisme(OrganismeDTO organismeDTO) {
		Organisme addOrganisme=new Organisme();
		addOrganisme.setCode(organismeDTO.getCode());
		addOrganisme.setLibelle(organismeDTO.getLibelle());
		addOrganisme.setAdresse(organismeDTO.getAdresse());
		addOrganisme.setEmail(organismeDTO.getEmail());
		addOrganisme.setTelephone(organismeDTO.getTelephone());
		addOrganisme.setFax(organismeDTO.getFax());
		addOrganisme.setStatut(GlobalConstants.STATUT_ACTIF);
		addOrganisme.setDateCreation(new Date());
		return organismeRepository.save(addOrganisme);
	}
	
	@Override
	public Organisme deleteOrganismeStatut(Long id) {
		 Organisme organisme = organismeRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Organisme", "id", id));
		 organisme.setStatut(GlobalConstants.STATUT_DELETE);
		 organisme.setDateDesactivation(new Date());
	     return  organismeRepository.save(organisme);
	}
	
	@Override
	public Organisme updateActivites(Long id, OrganismeDTO organismeDTO) {
		Organisme organisme = organismeRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Organisme", "id", id));
		organisme.setCode(organismeDTO.getCode());
		organisme.setLibelle(organismeDTO.getLibelle());
		organisme.setAdresse(organismeDTO.getAdresse());
		organisme.setEmail(organismeDTO.getEmail());
		organisme.setTelephone(organismeDTO.getTelephone());
		organisme.setFax(organismeDTO.getFax());
		organisme.setDateModification(new Date());
		organisme.setStatut(organismeDTO.getStatut().equals("actif")? GlobalConstants.STATUT_ACTIF : GlobalConstants.STATUT_INACTIF);

		return organismeRepository.save(organisme);
	}
	
	private OrganismeDTO mapToDTO(Organisme x)
	{
		OrganismeDTO dto = new OrganismeDTO();
		dto.setId(x.getId());
		dto.setCode(x.getCode());
		dto.setLibelle(x.getLibelle());
		dto.setDateCreation(x.getDateCreation());
		dto.setDateModification(x.getDateModification());
		dto.setDateDesactivation(x.getDateDesactivation());
		dto.setStatut(x.getStatut().equals("1")? "actif" : "inactif");
		dto.setAdresse(x.getAdresse());
		dto.setTelephone(x.getTelephone());
		dto.setFax(x.getFax());
		dto.setEmail(x.getEmail());
		return dto;
	}
	
	
}
