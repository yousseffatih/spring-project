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
import com.exemple.security.entity.OrgExpDest;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.OrgExpDestDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.OrgExpDestRepository;
import com.exemple.security.services.parametrage.OrgExpDest.InOrgExpDestService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orgExpDest")
@RequiredArgsConstructor
public class OrgExpDestController {
	

	@Autowired
	private InOrgExpDestService orgExpDestService;
	
	@Autowired
	private OrgExpDestRepository orgExpDestRepository;
	
	
	@GetMapping("/{id}")
	private ResponseEntity<OrgExpDest> getVille(@PathVariable Long id)
	{
		OrgExpDest orgExpDest = orgExpDestService.getOrgExpDest(id);
		return new ResponseEntity<OrgExpDest>(orgExpDest, HttpStatus.OK);
	}
	
	
	@GetMapping("/all")
	public List<OrgExpDest> getAllActivites() {
		return orgExpDestService.getAllOrgExpDest();
	}
	
	 @GetMapping("/allPagable")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
	{
		PageableResponseDTO organismeList = orgExpDestService.getAllOrgExpDestPagebal(pageNo,pageSize);
		return new ResponseEntity<>(organismeList , HttpStatus.OK);
	}
	 
	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		  orgExpDestService.deleteOrgExpDestStatut(id);
		 return new ResponseEntity<>(new MessageResponse("OrgExpDest supprimée.","success") , HttpStatus.OK);
	 }
	 
	 @PostMapping
	 public ResponseEntity<?> addActivite(@Valid @RequestBody OrgExpDestDTO orgExpDestDTO)
	 {
		 if(orgExpDestRepository.existsByCodeAdd(orgExpDestDTO.getCode()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Code existe déjà !" , "warning"));
		}
		if(orgExpDestRepository.existsByLibelleAdd(orgExpDestDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Libelle existe déjà !" , "warning"));
		}
		
		orgExpDestService.addOrgExpDest(orgExpDestDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("OrgExpDest ajoutée.","success"));
	 }
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id,	@Valid @RequestBody OrgExpDestDTO orgExpDestDTO) {
			if(orgExpDestRepository.existsByCodeModif(orgExpDestDTO.getCode(),id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Code existe déjà !" , "warning"));
			}
			if(orgExpDestRepository.existsByLibelleModif(orgExpDestDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Libelle existe déjà !" , "warning"));
			}
			if(orgExpDestDTO.getStatut() == null || orgExpDestDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Statut est obligatoire !" , "warning"));
			}
	        orgExpDestService.updateorgExpDest(id, orgExpDestDTO) ;
	        return new ResponseEntity<>(new MessageResponse("OrgExpDest modifiée.","success"),HttpStatus.OK);
	    }
	 @GetMapping("/listOrgExpDest")
		public List<ListApis> getAllActivitesApis() {
			List<OrgExpDest> activites  = orgExpDestService.getAllOrgExpDest();
			List<ListApis> listApis = activites.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}
	 
		private ListApis mapToApisList(OrgExpDest activites)
		{
			ListApis listApis = new ListApis();
			listApis.setId(activites.getId());
			listApis.setCode(activites.getCode());
			listApis.setLibelle(activites.getLibelle());
			return listApis;
		}
}
