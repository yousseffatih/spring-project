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
import com.exemple.security.entity.TypesRapport;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.TypesRapportDTO;
import com.exemple.security.repository.TypesRapportRepository;
import com.exemple.security.services.parametrage.TypesRapports.InTypesRapportsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/TypesRapport")
@RequiredArgsConstructor
public class TypesRapportController {


	@Autowired
	private InTypesRapportsService typesRapportsService;

	@Autowired
	private TypesRapportRepository typesRapportRepository;

	@GetMapping("/{id}")
	private ResponseEntity<TypesRapportDTO> getVille(@PathVariable Long id)
	{
		TypesRapportDTO activites = typesRapportsService.getTypesRapports(id);
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
		PageableResponseDTO pageableResponseDTO = typesRapportsService.getAllTypesRapportsPagebal(pageNo,pageSize);
		return new ResponseEntity<>(pageableResponseDTO , HttpStatus.OK);
	}

	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		  typesRapportsService.deleteTypesRapportsStatut(id);
		 return new ResponseEntity<>(new MessageResponse("Type de rapport supprimé.","success") , HttpStatus.OK);
	 }

	 @PostMapping
	 public ResponseEntity<?> addActivite(@Valid @RequestBody TypesRapportDTO typesRapportDTO)
	 {

		if(typesRapportRepository.existsByLibelleAdd(typesRapportDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
		}

		typesRapportsService.addTypesRapports(typesRapportDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Type de rapport ajouté.","success"));
	 }

	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id, @Valid @RequestBody TypesRapportDTO typesRapportDTO) {

			if(typesRapportRepository.existsByLibelleModif(typesRapportDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
			}
			if(typesRapportDTO.getStatut() == null || typesRapportDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
			}
	        typesRapportsService.updateTypesRapports(id, typesRapportDTO) ;
	        return new ResponseEntity<>(new MessageResponse("Type de rapport modifié.","success"),HttpStatus.OK);
	    }


	 @GetMapping("/listActivite")
		public List<ListApis> getAllActivitesApis() {
			List<TypesRapport> activites  = typesRapportsService.getAllTypesRapports();
			List<ListApis> listApis = activites.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}

		private ListApis mapToApisList(TypesRapport x)
		{
			ListApis listApis = new ListApis();
			listApis.setId(x.getId());
			listApis.setCode(x.getCode());
			listApis.setLibelle(x.getLibelle());
			return listApis;
		}

}
