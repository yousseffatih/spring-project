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
import com.exemple.security.entity.Activites;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ActivitesDTO;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.repository.ActivitesRepository;
import com.exemple.security.services.parametrage.Activities.InActivitiesServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/activites")
@RequiredArgsConstructor
public class ActivitesController {


	@Autowired
	private InActivitiesServices activitiesServices;

	@Autowired
	private ActivitesRepository activitesRepository;


	@GetMapping("/{id}")
	private ResponseEntity<Activites> getVille(@PathVariable Long id)
	{
		Activites activites = activitiesServices.getActivities(id);
		return new ResponseEntity<>(activites, HttpStatus.OK);
	}


	//	@GetMapping("/all")
	//	public List<Activites> getAllActivites() {
	//		return activitiesServices.getAllActivities();
	//	}


	 @GetMapping("/allPagable")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
	{
		PageableResponseDTO activities = activitiesServices.getAllActivitesPagebal(pageNo,pageSize);
		return new ResponseEntity<>(activities , HttpStatus.OK);
	}

	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		  activitiesServices.deleteActivitesStatut(id);
		 return new ResponseEntity<>(new MessageResponse("Activité supprimée.","success") , HttpStatus.OK);
	 }

	 @PostMapping
	 public ResponseEntity<?> addActivite(@Valid @RequestBody ActivitesDTO activitesDTO)
	 {

		if(activitesRepository.existsByLibelleAdd(activitesDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
		}

		activitiesServices.addActivites(activitesDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Activité ajoutée.","success"));
	 }

	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id, @Valid @RequestBody ActivitesDTO activitesDTO) {
		    System.out.println("this is th problem :"+ activitesDTO.toString());

			if(activitesRepository.existsByLibelleModif(activitesDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
			}
			if(activitesDTO.getStatut() == null || activitesDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
			}
	        activitiesServices.updateActivites(id, activitesDTO) ;
	        return new ResponseEntity<>(new MessageResponse("Activité modifiée","success"),HttpStatus.OK);
	    }


	 @GetMapping("/listActivite")
		public List<ListApis> getAllActivitesApis() {
			List<Activites> activites  = activitiesServices.getAllActivities();
			List<ListApis> listApis = activites.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}

		private ListApis mapToApisList(Activites activites)
		{
			ListApis listApis = new ListApis();
			listApis.setId(activites.getId());
			listApis.setCode(activites.getCode());
			listApis.setLibelle(activites.getLibelle());
			return listApis;
		}

}
