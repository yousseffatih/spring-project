package com.exemple.security.services.parametrage.courrier;

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
import com.exemple.security.entity.Affectations;
import com.exemple.security.entity.Courrier;
import com.exemple.security.entity.Employes;
import com.exemple.security.entity.OrgExpDest;
import com.exemple.security.entity.ProcessusCourrier;
import com.exemple.security.entity.ProcessusModel;
import com.exemple.security.entity.TypeCourriers;
import com.exemple.security.entity.User;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.AffectationsDTO;
import com.exemple.security.playload.dto.CourrierDTO;
import com.exemple.security.playload.dto.ListCourrierDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.ProcessusModelDTO;
import com.exemple.security.repository.AffectationsRepository;
import com.exemple.security.repository.CourrierRepository;
import com.exemple.security.repository.EmployesRepository;
import com.exemple.security.repository.ProcessusCourrierRepository;
import com.exemple.security.repository.ProcessusModelRepository;
import com.exemple.security.repository.UserRepository;
import com.exemple.security.services.parametrage.OrgExpDest.OrgExpDestService;
import com.exemple.security.services.parametrage.TypeCourriers.InTypeCourriersServices;

import jakarta.transaction.Transactional;

@Service
public class CourrierServiceImp implements InCourrierServcies{
	
	@Autowired
	private CourrierRepository courrierRepository;
	
	@Autowired
	private ProcessusCourrierRepository processusCourrierRepository;
	
	
	@Autowired
	private AffectationsRepository affectationsRepository;
	
	
	@Autowired
	private OrgExpDestService orgExpDestService;
	
	@Autowired
	private InTypeCourriersServices typeCourriersServices;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployesRepository employesRepository;
	
	@Autowired
	private ProcessusModelRepository processusModelRepository;
	
	
	
	@Override
	@Transactional
	public CourrierDTO addCourrier(CourrierDTO courrierDTO) {
		
		Courrier courrier = new Courrier();
		
		OrgExpDest orgExpDestSrc = orgExpDestService.getOrgExpDest(courrierDTO.getIdOrgExpDestSrc());
		OrgExpDest orgExpDestCible = orgExpDestService.getOrgExpDest(courrierDTO.getIdOrgExpDestCible());
		Affectations affectations = affectationsRepository .findByIdStatut(courrierDTO.getIdAffectations()).orElseThrow(()-> new ResourceNotFoundException("Affectations", "id", courrierDTO.getIdAffectations()));
		Employes employes = employesRepository.findById(courrierDTO.getIdEmployes()).orElseThrow(()-> new ResourceNotFoundException("Employes", "id", courrierDTO.getIdEmployes()));
		
		TypeCourriers typeCourriers = typeCourriersServices.getTypeCourriers(courrierDTO.getIdTypeCourriers());
		
		List<ProcessusModel> processusModels = processusModelRepository.findAllByTypeID(courrierDTO.getIdTypeCourriers());
		
		
		courrier.setAffectations(affectations);
		courrier.setTypeCourriers(typeCourriers);
		courrier.setOrgExpDestCible(orgExpDestCible);
		courrier.setOrgExpDestSrc(orgExpDestSrc);
		courrier.setEmployes(employes);

		courrier.setStatut(GlobalConstants.STATUT_ACTIF);
		courrier.setDateCourrirer(courrierDTO.getDateCourrirer());
		courrier.setInterne(courrierDTO.getInterne());
		courrier.setDateCreation(new Date());
		courrier.setLibelle(courrierDTO.getLibelle());
		courrier.setCode(courrierDTO.getCode());
		courrier.setDateEchuance(courrierDTO.getDateEchuance());
		courrier.setDateCourrirer(courrierDTO.getDateCourrirer());
		courrier.setNCourrier(courrierDTO.getNCourrier());
		
		ProcessusCourrier pc = new ProcessusCourrier();
		Courrier courrier2 = courrierRepository.save(courrier);
		int index = 0;
		for(ProcessusModel  p : processusModels)
		{
			index += 1;
			ProcessusCourrier processusCourrier = new ProcessusCourrier();
			processusCourrier.setCode(courrierDTO.getCode()+"_PC" + index);
			processusCourrier.setLibelle(courrierDTO.getLibelle()+"_PC" + index);
			processusCourrier.setEmployes(employes);
			processusCourrier.setProcessusModel(p);
			processusCourrier.setDateCreation(new Date());
			processusCourrier.setCourrier(courrier2);
			processusCourrier.setDateDebut(courrierDTO.getDateCourrirer());
			processusCourrier.setDateFin(courrierDTO.getDateEchuance());
			if(p.equals(processusModels.get(0)))
			{
				processusCourrier.setStatut(GlobalConstants.STATUT_ACTIF);
				pc = processusCourrier;
			} else {
				processusCourrier.setStatut(GlobalConstants.STATUT_PLANIFIER);
			}
			
			processusCourrierRepository.save(processusCourrier);
		}
		courrier.setProcessusCourrier(pc);
		courrier2 = courrierRepository.save(courrier);
		return maptoDto(courrier2);
	}
	
	@Override
	public List<CourrierDTO> getAllCourrier() {
		List<Courrier> courriers = courrierRepository.findAllWithStatus();
		List<CourrierDTO> courrierDTOs = new ArrayList<CourrierDTO>();
		courrierDTOs = courriers.stream().map(e -> maptoDto(e)).collect(Collectors.toList());
		return courrierDTOs;
	}
	
	@Override
	public CourrierDTO getCourrierId(Long id)
	{
		Courrier courrier = courrierRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Courrier", "id", id));
		CourrierDTO courrierDTO = new CourrierDTO();
		courrierDTO = maptoDto(courrier);
		return courrierDTO;
	}
	
	@Override
	public PageableResponseDTO getAllCourriersPagebal(int pageNo , int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize); 
		Page<Courrier> a = courrierRepository.findallStatutsPa(pageable);
		List<CourrierDTO> activites = a.getContent().stream().map(e -> maptoDto(e)).collect(Collectors.toList());
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
	public PageableResponseDTO filterCourriers(String nCourrier,Long idOrgExpDestSrc,Long idOrgExpDestCible,String dateCourrirer,String dateEchuance, Long idTypeCourriers,Long idProcessusModel,int pageNo,int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize); 
		Page<ListCourrierDTO> list = courrierRepository.findFilteredCourrier(nCourrier, idOrgExpDestSrc, idOrgExpDestCible,idTypeCourriers,  dateCourrirer, dateEchuance, idProcessusModel,pageable);
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(list.getContent());
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(list.getTotalElements());
		pageableResponseDTO.setTotlaPages(list.getTotalPages());
		pageableResponseDTO.setLast(list.isLast());
		return pageableResponseDTO;
	}
	
	
	@Override
	public Courrier deleteCourirrerStatut(Long id) {
		Courrier courrier = courrierRepository.findByIdStatut(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Courrier", "id", id));
		courrier.setStatut(GlobalConstants.STATUT_DELETE);
		courrier.setDateDesactivation(new Date());
		 List<ProcessusCourrier> processusCourriers = processusCourrierRepository.findAllByIDCourriersStatut(id);
		 for(ProcessusCourrier p : processusCourriers )
		 {
			 p.setStatut(GlobalConstants.STATUT_DELETE);
			 p.setDateDesactivation(new Date());
		 }
		 return  courrierRepository.save(courrier);
	}
	
	@Override
	@Transactional
	public CourrierDTO updateCourrir(CourrierDTO courrierDTO ,Long id) {
		
		Courrier courrier = courrierRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Courrier", "id", id));;
		
		OrgExpDest orgExpDestSrc = orgExpDestService.getOrgExpDest(courrierDTO.getIdOrgExpDestSrc());
		OrgExpDest orgExpDestCible = orgExpDestService.getOrgExpDest(courrierDTO.getIdOrgExpDestCible());
		Affectations affectations = affectationsRepository .findByIdStatut(courrierDTO.getIdAffectations()).orElseThrow(()-> new ResourceNotFoundException("Affectations", "id", courrierDTO.getIdAffectations()));
		Employes employes = employesRepository.findById(courrierDTO.getIdEmployes()).orElseThrow(()-> new ResourceNotFoundException("Employes", "id", courrierDTO.getIdEmployes()));
		
		TypeCourriers typeCourriers = typeCourriersServices.getTypeCourriers(courrierDTO.getIdTypeCourriers());
		
		List<ProcessusModel> processusModels = processusModelRepository.findAllByTypeID(courrierDTO.getIdTypeCourriers());
		
		courrier.setAffectations(affectations);
		courrier.setTypeCourriers(typeCourriers);
		courrier.setOrgExpDestCible(orgExpDestCible);
		courrier.setOrgExpDestSrc(orgExpDestSrc);
		courrier.setEmployes(employes);

		courrier.setStatut(courrierDTO.getStatut().equals("actif") ? GlobalConstants.STATUT_ACTIF : GlobalConstants.STATUT_INACTIF);
		courrier.setDateCourrirer(courrierDTO.getDateCourrirer());
		courrier.setInterne(courrierDTO.getInterne());
		courrier.setDateModification(new Date());
		courrier.setLibelle(courrierDTO.getLibelle());
		courrier.setCode(courrierDTO.getCode());
		courrier.setDateEchuance(courrierDTO.getDateEchuance());
		courrier.setDateCourrirer(courrierDTO.getDateCourrirer());
		courrier.setNCourrier(courrierDTO.getNCourrier());
		
		ProcessusCourrier pc = new ProcessusCourrier();
		Courrier courrier2 = courrierRepository.save(courrier);
		int index = 0;
		for(ProcessusModel  p : processusModels)
		{
			index += 1;
			ProcessusCourrier processusCourrier = new ProcessusCourrier();
			processusCourrier.setCode(courrierDTO.getCode()+"_PC" + index);
			processusCourrier.setLibelle(courrierDTO.getLibelle()+"_PC" + index);
			processusCourrier.setEmployes(employes);
			processusCourrier.setProcessusModel(p);
			processusCourrier.setDateModification(new Date());
			processusCourrier.setCourrier(courrier2);
			processusCourrier.setDateDebut(courrierDTO.getDateCourrirer());
			processusCourrier.setDateFin(courrierDTO.getDateEchuance());
			if(p.equals(processusModels.get(0)))
			{
				processusCourrier.setStatut(courrierDTO.getStatut().equals("1") ? GlobalConstants.STATUT_ACTIF : GlobalConstants.STATUT_INACTIF);
				pc = processusCourrier;
			} else {
				processusCourrier.setStatut(GlobalConstants.STATUT_PLANIFIER);
			}
			
			processusCourrierRepository.save(processusCourrier);
		}
		courrier.setProcessusCourrier(pc);
		courrier2 = courrierRepository.save(courrier);
		return maptoDto(courrier2) ;
	}
	
	private CourrierDTO maptoDto(Courrier courrier)
	{
		CourrierDTO dto = new CourrierDTO();
		dto.setId(courrier.getId());
		dto.setCode(courrier.getCode());
		dto.setLibelle(courrier.getLibelle());
		dto.setStatut(courrier.getStatut().equals("1")? "actif" : "inactif");
		dto.setDateCreation(courrier.getDateCreation());
		dto.setDateDesactivation(courrier.getDateDesactivation());
		dto.setDateModification(courrier.getDateModification());
		
		dto.setIdOrgExpDestSrc(courrier.getOrgExpDestSrc().getId());
		dto.setIdOrgExpDestCible(courrier.getOrgExpDestCible().getId());
		dto.setIdTypeCourriers(courrier.getTypeCourriers().getId());
		dto.setIdTypeProcessusCourrier(courrier.getProcessusCourrier().getId());
		dto.setIdEmployes(courrier.getEmployes().getId());
		dto.setIdAffectations(courrier.getAffectations().getId());
		dto.setIdProcessusCourrier(courrier.getProcessusCourrier().getId());
		
		dto.setNCourrier(courrier.getNCourrier());
		dto.setDateCourrirer(courrier.getDateCourrirer());
		dto.setDateEchuance(courrier.getDateEchuance());
		dto.setInterne(courrier.getInterne());
		return dto;
	}
	
}
