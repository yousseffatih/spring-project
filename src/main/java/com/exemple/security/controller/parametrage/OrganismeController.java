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
import com.exemple.security.entity.Organisme;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.OrganismeDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.OrganismeRepository;
import com.exemple.security.services.parametrage.organisme.InOrganismeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/organisme")
@RequiredArgsConstructor
public class OrganismeController {
	

	@Autowired
	private InOrganismeService organismeService;
	
	@Autowired
	private OrganismeRepository organismeRepository;
	
	
	@GetMapping("/{id}")
	private ResponseEntity<Organisme> getVille(@PathVariable Long id)
	{
		Organisme organisme = organismeService.getOrganisme(id);
		return new ResponseEntity<Organisme>(organisme, HttpStatus.OK);
	}
	
	
	@GetMapping("/all")
	public List<Organisme> getAllActivites() {
		return organismeService.getAllOrganisme();
	}
	
	 @GetMapping("/allPagable")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
	{
		PageableResponseDTO organismeList = organismeService.getAllOrganismePagebal(pageNo,pageSize);
		return new ResponseEntity<>(organismeList , HttpStatus.OK);
	}
	 
	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		  organismeService.deleteOrganismeStatut(id);
		 return new ResponseEntity<>(new MessageResponse("Organisme supprimée.","success") , HttpStatus.OK);
	 }
	 
	 @PostMapping
	 public ResponseEntity<?> addActivite(@Valid @RequestBody OrganismeDTO organismeDTO)
	 {
		 if(organismeRepository.existsByCodeAdd(organismeDTO.getCode()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Code existe déjà !" , "warning"));
		}
		if(organismeRepository.existsByLibelleAdd(organismeDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Libelle existe déjà !" , "warning"));
		}
		
		organismeService.addOrganisme(organismeDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Organisme ajoutée.","success"));
	 }
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id,	@Valid @RequestBody OrganismeDTO organismeDTO) {
			if(organismeRepository.existsByCodeModif(organismeDTO.getCode(),id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Code existe déjà !" , "warning"));
			}
			if(organismeRepository.existsByLibelleModif(organismeDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Libelle existe déjà !" , "warning"));
			}
			if(organismeDTO.getStatut() == null || organismeDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Statut est obligatoire !" , "warning"));
			}
	        organismeService.updateActivites(id, organismeDTO) ;
	        return new ResponseEntity<>(new MessageResponse("Organisme modifiée.","success"),HttpStatus.OK);
	    }
	 @GetMapping("/listOrganisme")
		public List<ListApis> getAllActivitesApis() {
			List<Organisme> activites  = organismeService.getAllOrganisme();
			List<ListApis> listApis = activites.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}
	 
		private ListApis mapToApisList(Organisme activites)
		{
			ListApis listApis = new ListApis();
			listApis.setId(activites.getId());
			listApis.setCode(activites.getCode());
			listApis.setLibelle(activites.getLibelle());
			return listApis;
		}
}
