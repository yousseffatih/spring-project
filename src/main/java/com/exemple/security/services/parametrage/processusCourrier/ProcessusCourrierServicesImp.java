package com.exemple.security.services.parametrage.processusCourrier;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.ProcessusCourrier;
import com.exemple.security.entity.User;
import com.exemple.security.exception.CustomException;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.ProcessusCourrierDTO;
import com.exemple.security.playload.dto.ValidePCdto;
import com.exemple.security.repository.CourrierRepository;
import com.exemple.security.repository.EmployesRepository;
import com.exemple.security.repository.ProcessusCourrierRepository;
import com.exemple.security.repository.ProcessusModelRepository;
import com.exemple.security.repository.UserRepository;


@Service
public class ProcessusCourrierServicesImp implements InProcessusCourrierServices{	
	
	@Autowired
	private ProcessusCourrierRepository processusCourrierRepository;
	
	@Autowired
	private CourrierRepository courrierRepository ;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployesRepository employesRepository;
	
	@Autowired
	private ProcessusModelRepository processusModelRepository;
	
	
	
	@Override
	public ProcessusCourrierDTO getProcessusCourrier(Long id)
	{
		ProcessusCourrier processusCourrier = processusCourrierRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Processus Courrier", "id", id));
		ProcessusCourrierDTO processusCourrierDTO = new ProcessusCourrierDTO();
		processusCourrierDTO = mapToDTO(processusCourrier);
		return processusCourrierDTO;
	}

	
	@Override
	public PageableResponseDTO getAllProcessusCourrier(int pageNo , int pageSize , Long idCourrier)
	{
		Pageable pageable = PageRequest.of(pageNo, pageSize); 
		Page<ProcessusCourrier> a = processusCourrierRepository.findAllPagebaleByIDCourriers(idCourrier, pageable);
		List<ProcessusCourrierDTO> processusCourrier = a.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(processusCourrier);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(a.getTotalElements());
		pageableResponseDTO.setTotlaPages(a.getTotalPages());
		pageableResponseDTO.setLast(a.isLast());
		return pageableResponseDTO;
	}
	
	@Override
	public void validateProcessusCourrier(ValidePCdto validePCdto)
	{
	    List<ProcessusCourrier> processusCourriers = processusCourrierRepository.findAllByIDCourriersStatut(validePCdto.getIdCourrier());
	    User user = userRepository.findById(validePCdto.getIdUser()).orElseThrow(()-> new ResourceNotFoundException("User", "id", validePCdto.getIdUser()));
	    int index = -1;
	    for(int i=0 ; i < processusCourriers.size() ;i++)
	    {
	    
	    	ProcessusCourrier currentPC = processusCourriers.get(i);
	    	if(currentPC.getId().equals(validePCdto.getIdProcessusCourrier()) 
	    			&& currentPC.getStatut().equals(GlobalConstants.STATUT_ACTIF))
	    	{
	    		index = i;
	    		currentPC.setStatut(GlobalConstants.STATUT_VALIDER);
	    		currentPC.setUser(user);	
	    		currentPC.setCommentaire(validePCdto.getCommentaire());
	    		currentPC.setPv(validePCdto.getPv());		    		
	    		processusCourrierRepository.save(currentPC);
	    		
	    		if(processusCourriers.size() > 1 && processusCourriers.size()-1 > i )
	    		{
	    			ProcessusCourrier c =processusCourriers.get(i+1);
	    			c.setStatut(GlobalConstants.STATUT_ACTIF);
	    			processusCourrierRepository.save(c);
	    		}
	    		continue;
	    	}
	    	
	    }
	    if(index == -1)
	    {
	    	throw new CustomException("Il y a un problème dans cette validation !");
	    }
	
	}
	
	
	
	private ProcessusCourrierDTO mapToDTO(ProcessusCourrier x) {
		ProcessusCourrierDTO dto = new ProcessusCourrierDTO();
		dto.setId(x.getId());
		dto.setCode(x.getCode());
		dto.setLibelle(x.getLibelle());
		dto.setDateCreation(x.getDateCreation());
		dto.setDateModification(x.getDateModification());
		dto.setDateDesactivation(x.getDateDesactivation());
		dto.setDateDebut(x.getDateDebut());
		dto.setDateFin(x.getDateFin());
		dto.setStatut(GlobalConstants.getStatusDescription(x.getStatut()));
		
		dto.setIdCourrier(x.getCourrier().getId());
		dto.setLibelleIdCourrier(x.getCourrier().getLibelle());
		
		dto.setIdEmployes(x.getEmployes().getId());
		dto.setEmploye(x.getEmployes().getLibelle());
		
		dto.setIdProcessusModel(x.getProcessusModel().getId());
		dto.setLibelleProcessusModel(x.getProcessusModel().getLibelle());
		dto.setCommentaire(x.getCommentaire());
		dto.setPv(x.getPv());
		if(x.getUser() != null)
		{
			dto.setIdUser(x.getUser().getId());
			dto.setUser(x.getUser().getUsername());
		}
		
        return dto;
    }


}
