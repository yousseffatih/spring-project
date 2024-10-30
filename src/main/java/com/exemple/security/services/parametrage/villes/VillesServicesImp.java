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
import com.exemple.security.entity.Numero;
import com.exemple.security.entity.Villes;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.VillesDTO;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.repository.VillesRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;


@Service
public class VillesServicesImp implements InVillesServices{

	@Autowired
	private VillesRepository villesRepository;

	@Autowired
	private InNumeroService numeroService;

	@Autowired
	private NumeroRepository numeroRepository;

	public VillesServicesImp (VillesRepository villesRepository)
	{
		this.villesRepository = villesRepository;
	}

	@Override
	public Villes addVilles(VillesDTO villes) {
		Villes addville=new Villes();

		List<Numero> numeros = numeroRepository.findByCode("Villes");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");

			numeroRepository.save(numero);
			addville.setCode(numeroService.genrateNumero(numero));
		}

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
