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
import com.exemple.security.entity.Reseaux;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.ReseauxDTO;
import com.exemple.security.repository.ReseauxRepository;
import com.exemple.security.services.parametrage.Reseaux.InReseauxService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reseaux")
@RequiredArgsConstructor
public class ReseauxController {
	
	
	@Autowired
	private InReseauxService reseauxService ;
	
	@Autowired
	private ReseauxRepository reseauxRepository;
	
	
	@GetMapping("/{id}")
	private ResponseEntity<Reseaux> getVille(@PathVariable Long id)
	{
		Reseaux activites = reseauxService.getReseaux(id);
		return new ResponseEntity<Reseaux>(activites, HttpStatus.OK);
	}
	
	
	@GetMapping("/all")
	public List<Reseaux> getAllActivites() {
		return reseauxService.getAllReseaux();
	}
	
	 @GetMapping("/allPagable")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
	{
		PageableResponseDTO activities = reseauxService.getAllReseauxPagebal(pageNo,pageSize);
		return new ResponseEntity<>(activities , HttpStatus.OK);
	}
	 
	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		 reseauxService.deleteReseauxStatut(id);
		 return new ResponseEntity<>(new MessageResponse("Reseaux supprimée.","success") , HttpStatus.OK);
	 }
	 
	 @PostMapping
	 public ResponseEntity<?> addActivite(@Valid @RequestBody ReseauxDTO reseauxDTO)
	 {
		 if(reseauxRepository.existsByCodeAdd(reseauxDTO.getCode()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Code existe déjà !" , "warning"));
		}
		if(reseauxRepository.existsByLibelleAdd(reseauxDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Libelle existe déjà !" , "warning"));
		}
		
		ReseauxDTO reseaux = new ReseauxDTO();
		reseaux.setCode(reseauxDTO.getCode());
		reseaux.setLibelle(reseauxDTO.getLibelle());
		
		reseauxService.addReseaux(reseaux);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Reseaux ajoutée.","success"));
	 }
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id, @Valid @RequestBody ReseauxDTO reseauxDTO) {
			if(reseauxRepository.existsByCodeModif(reseauxDTO.getCode(),id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Code existe déjà !" , "warning"));
			}
			if(reseauxRepository.existsByLibelleModif(reseauxDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Libelle existe déjà !" , "warning"));
			}
			if(reseauxDTO.getStatut() == null || reseauxDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Statut est obligatoire !" , "warning"));
			}
	        reseauxService.updateReseaux(id, reseauxDTO) ;
	        return new ResponseEntity<>(new MessageResponse("Reseaux modifiée.","success"),HttpStatus.OK);
	    }
	 @GetMapping("/listReseaux")
		public List<ListApis> getAllActivitesApis() {
			List<Reseaux> activites  =reseauxService.getAllReseaux();
			List<ListApis> listApis = activites.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}
	 
		private ListApis mapToApisList(Reseaux activites)
		{
			ListApis listApis = new ListApis();
			listApis.setId(activites.getId());
			listApis.setCode(activites.getCode());
			listApis.setLibelle(activites.getLibelle());
			return listApis;
		}
	 
	
}
