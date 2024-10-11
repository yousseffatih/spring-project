package com.exemple.security.services.parametrage.ProcessusModel;

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
import com.exemple.security.entity.ProcessusModel;
import com.exemple.security.entity.TypeCourriers;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.ProcessusModelDTO;
import com.exemple.security.repository.ProcessusModelRepository;
import com.exemple.security.repository.TypeCourriersRepository;

@Service
public class ProcessusModelServcieImp implements InProcessusModelService{
	
	@Autowired
	private ProcessusModelRepository processusModelRepository;
	
	@Autowired
	private TypeCourriersRepository typeCourriersRepository;
	
	
	
	public ProcessusModelServcieImp (ProcessusModelRepository processusModelRepository) {
		this.processusModelRepository = processusModelRepository;
	}

	
	@Override
	public List<ProcessusModelDTO> getAllProcessusModel() {
		List<ProcessusModel> processusModels = processusModelRepository.findAllWithStatus();
		List<ProcessusModelDTO> processusModelDTOs = new ArrayList<ProcessusModelDTO>();
		processusModelDTOs = processusModels.stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		return processusModelDTOs;
	}
	
	@Override
	public ProcessusModelDTO getProcessusModel(Long id)
	{
		ProcessusModel affectations = processusModelRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("ProcessusModel", "id", id));
		ProcessusModelDTO affectationsDto = new ProcessusModelDTO();
		affectationsDto = mapToDTO(affectations);
		return affectationsDto;
	}
	
	@Override
	public PageableResponseDTO getAllProcessusModelPagebal(int pageNo , int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize); 
		Page<ProcessusModel> a = processusModelRepository.findallStatutsPa(pageable);
		List<ProcessusModelDTO> activites = a.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
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
	public PageableResponseDTO getAllProcessusModelByTypePagebal(int pageNo , int pageSize , Long id) {
		Pageable pageable = PageRequest.of(pageNo, pageSize); 
		Page<ProcessusModel> a = processusModelRepository.findAllByTypeIDPagabel(id,pageable);
		List<ProcessusModelDTO> activites = a.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
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
	public ProcessusModel addProcessusModel(ProcessusModelDTO processusModelDTO) {
		ProcessusModel processusModel = new ProcessusModel();
		
		TypeCourriers typeCourriers = typeCourriersRepository.findByIdStatut(processusModelDTO.getIdTypeCourriers())
				.orElseThrow(() ->  new ResourceNotFoundException("TypeCourriers", "id", processusModelDTO.getIdTypeCourriers()));
		
		processusModel.setTypeCourriers(typeCourriers);
		
		processusModel.setStatut(GlobalConstants.STATUT_ACTIF);
		processusModel.setDateCreation(new Date());
		
		processusModel.setCode(processusModelDTO.getCode());
		processusModel.setLibelle(processusModelDTO.getLibelle());
		processusModel.setDuree(processusModelDTO.getDuree());
		processusModel.setOrderPM(processusModelDTO.getOrderPM());
		return processusModelRepository.save(processusModel);
	}
	
	@Override
	public ProcessusModel deleteProcessusModelStatut(Long id) {
		ProcessusModel processusModel = processusModelRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("ProcessusModel", "id", id));
		
		 processusModel.setStatut(GlobalConstants.STATUT_DELETE);
		 processusModel.setDateDesactivation(new Date());
	     return  processusModelRepository.save(processusModel);
	}
	
	@Override
	public ProcessusModelDTO updateProcessusModel(Long idAff, ProcessusModelDTO processusModelDTO) {
		ProcessusModel processusModel = processusModelRepository.findById(idAff)
				.orElseThrow(() ->  new ResourceNotFoundException("Affectations", "id", idAff));
		
		TypeCourriers typeCourriers = typeCourriersRepository.findByIdStatut(processusModelDTO.getIdTypeCourriers())
				.orElseThrow(() ->  new ResourceNotFoundException("TypeCourriers", "id", processusModelDTO.getIdTypeCourriers()));
		
		processusModel.setTypeCourriers(typeCourriers);
		
		processusModel.setDateModification(new Date());
		
		processusModel.setCode(processusModelDTO.getCode());
		processusModel.setLibelle(processusModelDTO.getLibelle());
		processusModel.setDuree(processusModelDTO.getDuree());
		processusModel.setOrderPM(processusModelDTO.getOrderPM());
		processusModel.setStatut(processusModelDTO.getStatut().equals("actif")? GlobalConstants.STATUT_ACTIF : GlobalConstants.STATUT_INACTIF);

		
		ProcessusModel processusModel2 = processusModelRepository.save(processusModel);
		ProcessusModelDTO processusModelDTO2 = mapToDTO(processusModel2);
		return processusModelDTO2;
	}
	
	@Override
	public List<ListApis> getAllByTypeIdApis(Long id) {
		 List<ProcessusModel> prosList = processusModelRepository.findAllByTypeID(id);
		 List<ListApis> list = prosList.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
		 return list;
	}
	
	private ListApis mapToApisList(ProcessusModel x)
	{
		ListApis listApis = new ListApis();
		listApis.setId(x.getId());
		listApis.setCode(x.getCode());
		listApis.setLibelle(x.getLibelle());
		return listApis;
	}
	
	
	private ProcessusModelDTO mapToDTO(ProcessusModel processusModel) {
		ProcessusModelDTO dto = new ProcessusModelDTO();
        dto.setId(processusModel.getId());
        
        dto.setIdTypeCourriers(processusModel.getTypeCourriers().getId());
        dto.setTypeCourries(processusModel.getTypeCourriers().getLibelle());
        
        dto.setCode(processusModel.getCode());
        dto.setLibelle(processusModel.getLibelle());
        dto.setDateCreation(processusModel.getDateCreation());
        dto.setDateDesactivation(processusModel.getDateDesactivation() );
        dto.setDateModification(processusModel.getDateModification());
		dto.setStatut(processusModel.getStatut().equals("1")? "actif" : "inactif");
        dto.setDuree(processusModel.getDuree());
        dto.setOrderPM(processusModel.getOrderPM());
        return dto;
    }
}
