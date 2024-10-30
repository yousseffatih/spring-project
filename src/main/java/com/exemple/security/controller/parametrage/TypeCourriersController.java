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
import com.exemple.security.entity.TypeCourriers;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.TypeCourriersDTO;
import com.exemple.security.repository.TypeCourriersRepository;
import com.exemple.security.services.parametrage.TypeCourriers.InTypeCourriersServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/typeCourriers")
@RequiredArgsConstructor
public class TypeCourriersController {

		@Autowired
		private InTypeCourriersServices typeCourriersServices;

		@Autowired
		private TypeCourriersRepository typeCourriersRepository;


		@GetMapping("/{id}")
		private ResponseEntity<TypeCourriers> getVille(@PathVariable Long id)
		{
			TypeCourriers typeCourriers = typeCourriersServices.getTypeCourriers(id);
			return new ResponseEntity<>(typeCourriers, HttpStatus.OK);
		}


		@GetMapping("/all")
		public List<TypeCourriers> getAllActivites() {
			return typeCourriersServices.getAllTypeCourriers();
		}


		 @GetMapping("/allPagable")
		public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
				@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
				@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
		{
			PageableResponseDTO typeC = typeCourriersServices.getAllTypeCourriersPagebal(pageNo,pageSize);
			return new ResponseEntity<>(typeC , HttpStatus.OK);
		}

		 @GetMapping("/delete/{id}")
		 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
		 {
			  typeCourriersServices.deleteTypeCourriersStatut(id);
			 return new ResponseEntity<>(new MessageResponse("Type de courrier supprimé.","success") , HttpStatus.OK);
		 }

		 @PostMapping
		 public ResponseEntity<?> addActivite(@Valid @RequestBody TypeCourriersDTO typeCourriersDTO)
		 {

			if(typeCourriersRepository.existsByLibelleAdd(typeCourriersDTO.getLibelle()))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libelle existe déjà !" , "warning"));
			}

			typeCourriersServices.addTypeCourriers(typeCourriersDTO);
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Type de courrier ajouté.","success"));
		 }

	 	@PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id, @Valid @RequestBody TypeCourriersDTO typeCourriersDTO) {

			if(typeCourriersRepository.existsByLibelleModif(typeCourriersDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libelle existe déjà !" , "warning"));
			}
			if(typeCourriersDTO.getStatut() == null || typeCourriersDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
			}
	        typeCourriersServices.updateTypeCourriers(id, typeCourriersDTO) ;
	        return new ResponseEntity<>(new MessageResponse("Type de courrier modifié.","success"),HttpStatus.OK);
	    }


	 	@GetMapping("/listTypeCourriers")
		public List<ListApis> getAllActivitesApis() {
			List<TypeCourriers> activites  = typeCourriersServices.getAllTypeCourriers();
			List<ListApis> listApis = activites.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}

		private ListApis mapToApisList(TypeCourriers activites)
		{
			ListApis listApis = new ListApis();
			listApis.setId(activites.getId());
			listApis.setCode(activites.getCode());
			listApis.setLibelle(activites.getLibelle());
			return listApis;
		}
}
