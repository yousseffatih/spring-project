package com.exemple.security.services.parametrage.OrgExpDest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.OrgExpDest;
import com.exemple.security.entity.Organisme;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.OrgExpDestDTO;
import com.exemple.security.playload.dto.OrganismeDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.OrgExpDestRepository;

@Service
public class OrgExpDestService implements InOrgExpDestService{
	
	@Autowired
	private OrgExpDestRepository orgExpDestRepository;
	

	
	@Override
	public List<OrgExpDest> getAllOrgExpDest() {
		List<OrgExpDest> orgExpDests = orgExpDestRepository.findAllWithStatus();
		return orgExpDests;
	}
	
	@Override
	public OrgExpDest getOrgExpDest(Long id)
	{
		OrgExpDest orgExpDest = orgExpDestRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("OrgExpDest", "id", id));
		return orgExpDest;
	}
	
	@Override
	public PageableResponseDTO getAllOrgExpDestPagebal(int pageNo , int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize); 
		Page<OrgExpDest> v = orgExpDestRepository.findallStatutsPa(pageable);
		List<OrgExpDestDTO> orgExpDests = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(orgExpDests);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(v.getTotalElements());
		pageableResponseDTO.setTotlaPages(v.getTotalPages());
		pageableResponseDTO.setLast(v.isLast());
		return pageableResponseDTO;
	}
	
	@Override
	public OrgExpDest addOrgExpDest(OrgExpDestDTO orgExpDestDTO ) {
		OrgExpDest orgrgExpDest=new OrgExpDest();
		orgrgExpDest.setCode(orgExpDestDTO.getCode());
		orgrgExpDest.setLibelle(orgExpDestDTO.getLibelle());
		orgrgExpDest.setAdresse(orgExpDestDTO.getAdresse());
		orgrgExpDest.setEmail(orgExpDestDTO.getEmail());
		orgrgExpDest.setTelephone(orgExpDestDTO.getTelephone());
		orgrgExpDest.setFax(orgExpDestDTO.getFax());
		orgrgExpDest.setStatut(GlobalConstants.STATUT_ACTIF);
		orgrgExpDest.setDateCreation(new Date());
		return orgExpDestRepository.save(orgrgExpDest);
	}
	
	@Override
	public OrgExpDest deleteOrgExpDestStatut(Long id) {
		OrgExpDest orgExpDest = orgExpDestRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("OrgExpDest", "id", id));
		 orgExpDest.setStatut(GlobalConstants.STATUT_DELETE);
		 orgExpDest.setDateDesactivation(new Date());
	     return  orgExpDestRepository.save(orgExpDest);
	}
	
	@Override
	public OrgExpDest updateorgExpDest(Long id, OrgExpDestDTO orgExpDestDTO) {
		OrgExpDest orgExpDest = orgExpDestRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("OrgExpDest", "id", id));
		orgExpDest.setCode(orgExpDestDTO.getCode());
		orgExpDest.setLibelle(orgExpDestDTO.getLibelle());
		orgExpDest.setAdresse(orgExpDestDTO.getAdresse());
		orgExpDest.setEmail(orgExpDestDTO.getEmail());
		orgExpDest.setTelephone(orgExpDestDTO.getTelephone());
		orgExpDest.setFax(orgExpDestDTO.getFax());
		orgExpDest.setStatut(orgExpDestDTO.getStatut().equals("actif")? GlobalConstants.STATUT_ACTIF : GlobalConstants.STATUT_INACTIF);

		orgExpDest.setDateModification(new Date());
		return orgExpDestRepository.save(orgExpDest);
	}
	
	private OrgExpDestDTO mapToDTO(OrgExpDest x)
	{
		OrgExpDestDTO dto = new OrgExpDestDTO();
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
