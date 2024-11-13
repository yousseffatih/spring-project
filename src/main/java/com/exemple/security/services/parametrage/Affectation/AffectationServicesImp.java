package com.exemple.security.services.parametrage.Affectation;

import java.util.ArrayList;
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
import com.exemple.security.entity.Affectations;
import com.exemple.security.entity.Numero;
import com.exemple.security.entity.Organisme;
import com.exemple.security.entity.Reseaux;
import com.exemple.security.entity.Villes;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.AffectationsDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.ActivitesRepository;
import com.exemple.security.repository.AffectationsRepository;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.repository.OrganismeRepository;
import com.exemple.security.repository.ReseauxRepository;
import com.exemple.security.repository.VillesRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;

@Service
public class AffectationServicesImp implements InAffectationServices{
	@Autowired
	private AffectationsRepository affectationsRepository;

	@Autowired
	private OrganismeRepository organismeRepository;

	@Autowired
	private ReseauxRepository reseauxRepository;

	@Autowired
	private VillesRepository villesRepository;

	@Autowired
	private ActivitesRepository activitesRepository;

	@Autowired
	private InNumeroService numeroService;

	@Autowired
	private NumeroRepository numeroRepository;

	public AffectationServicesImp (AffectationsRepository affectationsRepository) {
		this.affectationsRepository = affectationsRepository;
	}


	@Override
	public List<AffectationsDTO> getAllAffectation() {
		List<Affectations> affectations = affectationsRepository.findAllWithStatus();
		List<AffectationsDTO> affectationsDTO = new ArrayList<>();
		affectationsDTO = affectations.stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		return affectationsDTO;
	}

	@Override
	public AffectationsDTO getAffectation(Long id)
	{
		Affectations affectations = affectationsRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Affectations", "id", id));
		AffectationsDTO affectationsDto = new AffectationsDTO();
		affectationsDto = mapToDTO(affectations);
		return affectationsDto;
	}

	@Override
	public PageableResponseDTO getAllAffectationPagebal(int pageNo , int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Affectations> a = affectationsRepository.findallStatutsPa(pageable);
		List<AffectationsDTO> activites = a.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(activites);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(a.getTotalElements());
		pageableResponseDTO.setTotlaPages(a.getTotalPages());
		pageableResponseDTO.setLast(a.isLast());
		return pageableResponseDTO;
	}

	@Override
	public Affectations addAffectation(AffectationsDTO affectationsDTO) {
		Affectations addAffectations = new Affectations();

		Reseaux reseaux = reseauxRepository.findByIdStatut(affectationsDTO.getIdreseaux()).orElseThrow(() ->  new ResourceNotFoundException("Reseaux", "id", affectationsDTO.getIdreseaux()));
		Activites activites = activitesRepository.findByIdStatut(affectationsDTO.getIdactivites()).orElseThrow(() ->  new ResourceNotFoundException("Activites", "id", affectationsDTO.getIdactivites()));
		Organisme organisme = organismeRepository.findByIdStatut(affectationsDTO.getIdorganismes()).orElseThrow(() ->  new ResourceNotFoundException("Organisme", "id", affectationsDTO.getIdorganismes()));
		Villes villes = villesRepository.findByIdStatut(affectationsDTO.getIdvilles()).orElseThrow(() ->  new ResourceNotFoundException("Villes", "id", affectationsDTO.getIdvilles()));

		addAffectations.setOrganismes(organisme);
		addAffectations.setActivites(activites);
		addAffectations.setReseaux(reseaux);
		addAffectations.setVilles(villes);

		addAffectations.setStatut(GlobalConstants.STATUT_ACTIF);
		addAffectations.setDateCreation(new Date());

		List<Numero> numeros = numeroRepository.findByCode("Affectations");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");

			numeroRepository.save(numero);
			addAffectations.setCode(numeroService.genrateNumero(numero));
		}

		addAffectations.setLibelle(affectationsDTO.getLibelle());
		addAffectations.setDateCreation(affectationsDTO.getDateCreation());
		addAffectations.setEmail(affectationsDTO.getEmail());
		addAffectations.setTelephone(affectationsDTO.getTelephone());
		addAffectations.setFax(affectationsDTO.getFax());
		addAffectations.setAdresse(affectationsDTO.getAdresse());
		addAffectations.setTypeAffectation(affectationsDTO.getTypeAffectation());
		addAffectations.setBloque(affectationsDTO.getBloque());
		return affectationsRepository.save(addAffectations);
	}

	@Override
	public Affectations deleteAffectationStatut(Long id) {
		Affectations affectations = affectationsRepository.findByIdStatut(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Affectations", "id", id));
		 affectations.setStatut(GlobalConstants.STATUT_DELETE);
		 affectations.setDateDesactivation(new Date());
	     return  affectationsRepository.save(affectations);
	}

	@Override
	public AffectationsDTO updateAffectation(Long idAff, AffectationsDTO affectationsDTO) {
		Affectations affectations = affectationsRepository.findById(idAff).orElseThrow(() ->  new ResourceNotFoundException("Affectations", "id", idAff));

		Reseaux reseaux = reseauxRepository.findByIdStatut(affectationsDTO.getIdreseaux()).orElseThrow(() ->  new ResourceNotFoundException("Reseaux", "id", affectationsDTO.getIdreseaux()));
		Activites activites = activitesRepository.findByIdStatut(affectationsDTO.getIdactivites()).orElseThrow(() ->  new ResourceNotFoundException("Activites", "id", affectationsDTO.getIdactivites()));
		Organisme organisme = organismeRepository.findByIdStatut(affectationsDTO.getIdorganismes()).orElseThrow(() ->  new ResourceNotFoundException("Organisme", "id", affectationsDTO.getIdorganismes()));
		Villes villes = villesRepository.findByIdStatut(affectationsDTO.getIdvilles()).orElseThrow(() ->  new ResourceNotFoundException("Villes", "id", affectationsDTO.getIdvilles()));

		affectations.setOrganismes(organisme);
		affectations.setActivites(activites);
		affectations.setReseaux(reseaux);
		affectations.setVilles(villes);

		affectations.setLibelle(affectationsDTO.getLibelle());
		affectations.setDateModification(new Date());
		affectations.setEmail(affectationsDTO.getEmail());
		affectations.setTelephone(affectationsDTO.getTelephone());
		affectations.setFax(affectationsDTO.getFax());
		affectations.setAdresse(affectationsDTO.getAdresse());
		affectations.setTypeAffectation(affectationsDTO.getTypeAffectation());
		affectations.setBloque(affectationsDTO.getBloque());
		affectations.setStatut(GlobalConstants.getStatusFromDescription(affectationsDTO.getStatut()));


		Affectations affectations2 = affectationsRepository.save(affectations);
		AffectationsDTO affectationsDTO2 = mapToDTO( affectations2);
		return affectationsDTO2;
	}


	private AffectationsDTO mapToDTO(Affectations affectation) {
		AffectationsDTO dto = new AffectationsDTO();
        dto.setId(affectation.getId());

        dto.setIdorganismes(affectation.getOrganismes().getId());
        dto.setLibelleOrganisme(affectation.getOrganismes().getLibelle());

        dto.setIdvilles(affectation.getVilles().getId());
        dto.setLibelleVille(affectation.getVilles().getLibelle());

        dto.setIdreseaux(affectation.getReseaux().getId());
        dto.setLibelleReseaux(affectation.getReseaux().getLibelle());

        dto.setIdactivites(affectation.getActivites().getId());
        dto.setLibelleActivites(affectation.getActivites().getLibelle());

        dto.setCode(affectation.getCode());
        dto.setLibelle(affectation.getLibelle());
        dto.setDateCreation(affectation.getDateCreation());
        dto.setDateDesactivation(affectation.getDateDesactivation() );
        dto.setDateModification(affectation.getDateModification());
        dto.setAdresse(affectation.getAdresse());
        dto.setEmail(affectation.getEmail());
        dto.setTelephone(affectation.getTelephone());
        dto.setFax(affectation.getFax());
        dto.setStatut(GlobalConstants.getStatusDescription(affectation.getStatut()));
        dto.setTypeAffectation(affectation.getTypeAffectation());
        dto.setBloque(affectation.getBloque());
        return dto;
    }


}
