package com.exemple.security.services.parametrage.fonctions;

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
import com.exemple.security.entity.Fonctions;
import com.exemple.security.entity.Villes;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.ActivitesDTO;
import com.exemple.security.playload.dto.FonctionsDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.VillesDTO;
import com.exemple.security.repository.FonctionsRepository;
import com.exemple.security.repository.VillesRepository;

@Service
public class FonctionsServicesImp implements InFonctionsServices{
	@Autowired
	private FonctionsRepository fonctionsRepository;
	
	public FonctionsServicesImp (FonctionsRepository fonctionsRepository)
	{
		this.fonctionsRepository = fonctionsRepository;
	}

	@Override
	public Fonctions addFonctions(FonctionsDTO fonctionsDTO) {
		Fonctions addFonctions=new Fonctions();
		addFonctions.setCode(fonctionsDTO.getCode());
		addFonctions.setLibelle(fonctionsDTO.getLibelle());
		addFonctions.setStatut(GlobalConstants.STATUT_ACTIF);
		addFonctions.setDateCreation(new Date());
		return fonctionsRepository.save(addFonctions);
	}

	@Override
	public Fonctions getFonctions(Long id) {
		Fonctions fonctions = fonctionsRepository.findByIdStatut(id).orElseThrow(() -> new ResourceNotFoundException("Fonctions", "id", id));
		return fonctions;
	}

	@Override
	public List<Fonctions> getAllFonctions() {
		List<Fonctions> fonctions = fonctionsRepository.findAllStatus();
		return fonctions;
	}

	@Override
	public Fonctions updateFonctions(Long idFonctions, FonctionsDTO fonctionsDTO) {
		Fonctions fonctions = fonctionsRepository.findById(idFonctions).orElseThrow(() ->  new ResourceNotFoundException("Fonctions", "id", idFonctions));
		fonctions.setCode(fonctionsDTO.getCode());
		fonctions.setLibelle(fonctionsDTO.getLibelle());
		fonctions.setDateModification(new Date());
		fonctions.setStatut(fonctionsDTO.getStatut().equals("actif")? GlobalConstants.STATUT_ACTIF : GlobalConstants.STATUT_INACTIF);
		return fonctionsRepository.save(fonctions);
	}

	@Override
	public void deleteFonctions(Long id) {
		Fonctions fonctions = fonctionsRepository.findById(id)
	                .orElseThrow(() -> new  ResourceNotFoundException("Fonctions", "id", id));
	        fonctionsRepository.delete(fonctions);
		
	}
	
	@Override
	public Fonctions deleteFonctionsStatus(Long id) {
		Fonctions fonctions = fonctionsRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Fonctions", "id", id));
		 fonctions.setStatut(GlobalConstants.STATUT_DELETE);
		 fonctions.setDateDesactivation(new Date());
	     return  fonctionsRepository.save(fonctions);
	}
	
	@Override
	public PageableResponseDTO getAllFonctionsPagebal(int pageNo , int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize); 
		Page<Fonctions> v = fonctionsRepository.findallStatutsPa(pageable);
		List<FonctionsDTO> fonctions = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(fonctions);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(v.getTotalElements());
		pageableResponseDTO.setTotlaPages(v.getTotalPages());
		pageableResponseDTO.setLast(v.isLast());
		return pageableResponseDTO;
		
	}
	
	private FonctionsDTO mapToDTO(Fonctions x)
	{
		FonctionsDTO dto = new FonctionsDTO();
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
