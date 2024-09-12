package com.exemple.security.services.parametrage.villes;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.Villes;
import com.exemple.security.playload.PageableResponseDTO;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.VillesDTO;
import com.exemple.security.repository.VillesRepository;

import jakarta.transaction.Transactional;

@Service
public class VillesServicesImp implements InVillesServices{
	
	@Autowired
	private VillesRepository villesRepository;

	@Override
	@Transactional
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
		Villes villes = villesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ville", "id", id));
		return villes;
	}

	@Override
	public List<Villes> getAllVilles() {
		List<Villes> villes = villesRepository.findAll();
		return villes;
	}

	@Override
	public Villes updateVille(Long idVilles, VillesDTO villes) {
		Villes ville = villesRepository.findById(idVilles).orElseThrow(() ->  new ResourceNotFoundException("ville", "id", idVilles));
		ville.setCode(villes.getCode());
		ville.setLibelle(villes.getLibelle());
		ville.setDateModification(new Date());
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
		Page<Villes> v = villesRepository.findAll(pageable);
		List<Villes> villes = v.getContent();
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(villes);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(v.getTotalElements());
		pageableResponseDTO.setTotlaPages(v.getTotalPages());
		pageableResponseDTO.setLast(v.isLast());
		return pageableResponseDTO;
		
	}

}
