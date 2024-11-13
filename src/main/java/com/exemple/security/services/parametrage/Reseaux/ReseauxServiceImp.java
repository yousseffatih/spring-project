package com.exemple.security.services.parametrage.Reseaux;

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
import com.exemple.security.entity.Reseaux;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.ReseauxDTO;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.repository.ReseauxRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;

@Service
public class ReseauxServiceImp implements InReseauxService{

	@Autowired
	private ReseauxRepository reseauxRepository;

	@Autowired
	private InNumeroService numeroService;

	@Autowired
	private NumeroRepository numeroRepository;

	public ReseauxServiceImp (ReseauxRepository reseauxRepository)
	{
		this.reseauxRepository = reseauxRepository;
	}

	@Override
	public Reseaux addReseaux(ReseauxDTO reseauxDTO) {
		Reseaux addreseaux=new Reseaux();

		List<Numero> numeros = numeroRepository.findByCode("Reseaux");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");

			numeroRepository.save(numero);
			addreseaux.setCode(numeroService.genrateNumero(numero));
		}

		addreseaux.setLibelle(reseauxDTO.getLibelle());
		addreseaux.setStatut(GlobalConstants.STATUT_ACTIF);
		addreseaux.setDateCreation(new Date());
		return reseauxRepository.save(addreseaux);
	}

	@Override
	public ReseauxDTO getReseaux(Long id) {
		Reseaux  reseaux = reseauxRepository.findByIdStatut(id).orElseThrow(() -> new ResourceNotFoundException("Reseaux", "id", id));
		return mapToDTO(reseaux);
	}

	@Override
	public List<Reseaux> getAllReseaux() {
		List<Reseaux> reseaux = reseauxRepository.findAllStatus();
		return reseaux;
	}

	@Override
	public Reseaux updateReseaux(Long id, ReseauxDTO reseauxDTO) {
		Reseaux reseaux = reseauxRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Reseaux", "id", Long.valueOf(id)));
		reseaux.setLibelle(reseauxDTO.getLibelle());
		reseaux.setDateModification(new Date());
		reseaux.setStatut(GlobalConstants.getStatusFromDescription(reseauxDTO.getStatut()));

		return reseauxRepository.save(reseaux);
	}

	@Override
	public void deleteReseaux(Long id) {
		Reseaux ville = reseauxRepository.findById(id)
	                .orElseThrow(() -> new  ResourceNotFoundException("Reseaux", "id", Long.valueOf(id)));
	        reseauxRepository.delete(ville);

	}

	@Override
	public Reseaux deleteReseauxStatut(Long id) {
		Reseaux reseaux = reseauxRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Reseaux", "id", Long.valueOf(id)));
		reseaux.setStatut(GlobalConstants.STATUT_DELETE);
		reseaux.setDateDesactivation(new Date());
	     return  reseauxRepository.save(reseaux);
	}

	@Override
	public PageableResponseDTO getAllReseauxPagebal(int pageNo , int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Reseaux> v = reseauxRepository.findallStatutsPa(pageable);
		List<ReseauxDTO> villes = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(villes);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(v.getTotalElements());
		pageableResponseDTO.setTotlaPages(v.getTotalPages());
		pageableResponseDTO.setLast(v.isLast());
		return pageableResponseDTO;

	}

	private ReseauxDTO mapToDTO(Reseaux x)
	{
		ReseauxDTO dto = new ReseauxDTO();
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
