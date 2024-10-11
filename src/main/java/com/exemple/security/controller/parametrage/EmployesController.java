package com.exemple.security.controller.parametrage;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.AffectationsDTO;
import com.exemple.security.playload.dto.EmployesDTO;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.EmployesRepository;
import com.exemple.security.services.parametrage.Employes.InEmployesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/emlopyes")
@RequiredArgsConstructor
public class EmployesController {
	

	@Autowired
	private InEmployesService employesService;
	
	@Autowired
	private EmployesRepository employesRepository;
	
	
	@GetMapping("/{id}")
	private ResponseEntity<EmployesDTO> getVille(@PathVariable Long id)
	{
		EmployesDTO employesDTO = employesService.getEmployes(id);
		
		return new ResponseEntity<EmployesDTO>(employesDTO, HttpStatus.OK);
	}
	
	
	@GetMapping("/all")
	public List<EmployesDTO> getAllActivites() {
		return employesService.getAllEmployesDTOs();
	}
	
	 @GetMapping("/allPagable")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
	{
		PageableResponseDTO affectations = employesService.getAllEmployesPagebal(pageNo,pageSize);
		return new ResponseEntity<>(affectations , HttpStatus.OK);
	}
	 
	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		 employesService.deleteEmployesStatut(id);
		 return new ResponseEntity<>(new MessageResponse("Supprimer succès.","success") , HttpStatus.OK);
	 }
	 
	 @PostMapping
	 public ResponseEntity<?> addEmployes(@Valid @RequestBody EmployesDTO employesDTO)
	 {
		 if(employesRepository.existsByCodeAdd(employesDTO.getCode()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Code existe déjà !" , "warning"));
		}
		if(employesRepository.existsByMatriculeAdd(employesDTO.getMatricule()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Matricule existe déjà !" , "warning"));
		}
		
		employesService.addEmployes(employesDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Employer ajoutée.","success"));
	 }
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id,@Valid @RequestBody EmployesDTO employesDTO) {
			if(employesRepository.existsByCodeModif(employesDTO.getCode(),id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Code existe déjà !" , "warning"));
			}
			if(employesRepository.existsByMatriculeModif(employesDTO.getMatricule() , id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Matricule existe déjà !" , "warning"));
			}
			if(employesDTO.getStatut() == null || employesDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Statut est obligatoire !" , "warning"));
			}
	        employesService.updateEmployesDTO(id, employesDTO);
	        return new ResponseEntity<>(new MessageResponse("Employer modifiée","success"),HttpStatus.OK);
	   }
	 
	 @GetMapping("/lisEmployes")
		public List<ListApis> getAllActivitesApis() {
			List<EmployesDTO> activites  = employesService.getAllEmployesDTOs();
			List<ListApis> listApis = activites.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}
	 
		private ListApis mapToApisList(EmployesDTO activites)
		{
			ListApis listApis = new ListApis();
			listApis.setId(activites.getId());
			listApis.setCode(activites.getCode());
			listApis.setLibelle(activites.getLibelle());
			return listApis;
		}
}
