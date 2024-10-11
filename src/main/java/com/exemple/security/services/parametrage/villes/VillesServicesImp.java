package com.exemple.security.services.parametrage.villes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.Villes;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.VillesDTO;
import com.exemple.security.repository.VillesRepository;


@Service
public class VillesServicesImp implements InVillesServices{
	
	@Autowired
	private VillesRepository villesRepository;
	
	public VillesServicesImp (VillesRepository villesRepository)
	{
		this.villesRepository = villesRepository;
	}

	@Override
	public Villes addVilles(VillesDTO villes) {
		Villes addville=new Villes();
		addville.setCode(villes.getCode());
		addville.setLibelle(villes.getLibelle());
		addville.setStatut(GlobalConstants.STATUT_ACTIF);
		addville.setDateCreation(new Date());
		return villesRepository.save(addville);
	}

	@Override
	public Villes getVille(Long id) {
		Villes villes = villesRepository.findByIdStatut(id).orElseThrow(() -> new ResourceNotFoundException("ville", "id", id));
		return villes;
	}

	@Override
	public List<Villes> getAllVilles() {
		List<Villes> villes = villesRepository.findAllStatus();
		return villes;
	}

	@Override
	public Villes updateVille(Long idVilles, VillesDTO villes) {
		Villes ville = villesRepository.findByIdStatut(idVilles).orElseThrow(() ->  new ResourceNotFoundException("ville", "id", idVilles));
		ville.setCode(villes.getCode());
		ville.setLibelle(villes.getLibelle());
		ville.setDateModification(new Date());
		ville.setStatut(villes.getStatut().equals("actif")? GlobalConstants.STATUT_ACTIF : GlobalConstants.STATUT_INACTIF);

		return villesRepository.save(ville);
	}

	@Override
	public void deleteVilles(Long id) {
		 Villes ville = villesRepository.findById(id)
	                .orElseThrow(() -> new  ResourceNotFoundException("ville", "id", id));
	        villesRepository.delete(ville);
		
	}
	
	@Override
	public Villes deleteVillesStatus(Long id) {
		 Villes ville = villesRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("ville", "id", id));
		 ville.setStatut(GlobalConstants.STATUT_DELETE);
		 ville.setDateDesactivation(new Date());
	     return  villesRepository.save(ville);
	}
	
	@Override
	public PageableResponseDTO getAllVillesPagebal(int pageNo , int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize); 
		Page<Villes> v = villesRepository.findallStatutsPa(pageable);
		List<VillesDTO> villes = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(villes);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(v.getTotalElements());
		pageableResponseDTO.setTotlaPages(v.getTotalPages());
		pageableResponseDTO.setLast(v.isLast());
		return pageableResponseDTO;
		
	}
	
	private VillesDTO mapToDTO(Villes x)
	{
		VillesDTO dto = new VillesDTO();
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
