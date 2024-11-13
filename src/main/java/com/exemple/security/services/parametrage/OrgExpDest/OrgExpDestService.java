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
import com.exemple.security.entity.Numero;
import com.exemple.security.entity.OrgExpDest;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.OrgExpDestDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.repository.OrgExpDestRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;

@Service
public class OrgExpDestService implements InOrgExpDestService{

	@Autowired
	private OrgExpDestRepository orgExpDestRepository;

	@Autowired
	private InNumeroService numeroService;

	@Autowired
	private NumeroRepository numeroRepository;



	@Override
	public List<OrgExpDest> getAllOrgExpDest() {
		List<OrgExpDest> orgExpDests = orgExpDestRepository.findAllWithStatus();
		return orgExpDests;
	}

	@Override
	public OrgExpDestDTO getOrgExpDest(Long id)
	{
		OrgExpDest orgExpDest = orgExpDestRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("OrgExpDest", "id", id));
		return mapToDTO(orgExpDest);
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

		List<Numero> numeros = numeroRepository.findByCode("OrgExpDest");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");

			numeroRepository.save(numero);
			orgrgExpDest.setCode(numeroService.genrateNumero(numero));
		}

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
		orgExpDest.setLibelle(orgExpDestDTO.getLibelle());
		orgExpDest.setAdresse(orgExpDestDTO.getAdresse());
		orgExpDest.setEmail(orgExpDestDTO.getEmail());
		orgExpDest.setTelephone(orgExpDestDTO.getTelephone());
		orgExpDest.setFax(orgExpDestDTO.getFax());
		orgExpDest.setStatut(GlobalConstants.getStatusFromDescription(orgExpDestDTO.getStatut()));

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
		dto.setStatut(GlobalConstants.getStatusDescription(x.getStatut()));
		dto.setAdresse(x.getAdresse());
		dto.setTelephone(x.getTelephone());
		dto.setFax(x.getFax());
		dto.setEmail(x.getEmail());
		return dto;
	}
}
