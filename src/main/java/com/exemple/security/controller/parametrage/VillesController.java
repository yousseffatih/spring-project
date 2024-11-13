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
import com.exemple.security.entity.Villes;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.VillesDTO;
import com.exemple.security.repository.VillesRepository;
import com.exemple.security.services.parametrage.villes.InVillesServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/villes")
@RequiredArgsConstructor
public class VillesController {

		@Autowired
		private InVillesServices villesServices;

		@Autowired
		private VillesRepository villesRepository;


		@PostMapping
		private ResponseEntity<?> addVilles(@Valid @RequestBody VillesDTO villes)
		{

			if(villesRepository.existsByLibelleAdd(villes.getLibelle()))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Libelle existe déjà !" , "warning"));
			}
			villesServices.addVilles(villes);
			return new ResponseEntity<>(new MessageResponse("Ville ajoutée.","success"),HttpStatus.OK);
		}

		@GetMapping("/{id}")
		private ResponseEntity<VillesDTO> getVille(@PathVariable Long id)
		{
			VillesDTO villes = villesServices.getVille(id);
			return new ResponseEntity<>(villes, HttpStatus.OK);
		}

		@GetMapping("/all")
		private ResponseEntity<List<Villes>> getAllVillesEntity()
		{
			List<Villes> villes = villesServices.getAllVilles();
			return new ResponseEntity<>(villes , HttpStatus.OK);
		}

		@PutMapping("/{id}")
	    public ResponseEntity<?> updateVille(@PathVariable Long id,@Valid @RequestBody VillesDTO villes) {

			if(villesRepository.existsByLibelleModif(villes.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libelle existe déjà !" , "warning"));
			}
			if(villes.getStatut() == null || villes.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
			}
	        villesServices.updateVille(id, villes);
	        return new ResponseEntity<>(new MessageResponse("Ville modifée.","success"),HttpStatus.OK);
	    }


		 @GetMapping("/delete/{id}")
		 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
		 {
			 villesServices.deleteVillesStatus(id);
			 return new ResponseEntity<>(new MessageResponse("Ville supprimée.","success") , HttpStatus.OK);
		 }

	 	@GetMapping("/allPagable")
		private ResponseEntity<PageableResponseDTO> getAllVillesPageable(
				@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
				@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
		{
			PageableResponseDTO villes = villesServices.getAllVillesPagebal(pageNo,pageSize);
			return new ResponseEntity<>(villes , HttpStatus.OK);
		}
	 	@GetMapping("/listVilles")
		public List<ListApis> getAllActivitesApis() {
			List<Villes> activites  = villesServices.getAllVilles();
			List<ListApis> listApis = activites.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}

		private ListApis mapToApisList(Villes activites)
		{
			ListApis listApis = new ListApis();
			listApis.setId(activites.getId());
			listApis.setCode(activites.getCode());
			listApis.setLibelle(activites.getLibelle());
			return listApis;
		}
}
