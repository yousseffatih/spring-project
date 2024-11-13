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
import com.exemple.security.entity.Role;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.RoleDTO;
import com.exemple.security.repository.RoleRepository;
import com.exemple.security.repository.UserRepository;
import com.exemple.security.services.parametrage.Role.InRoleServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

	@Autowired
	private InRoleServices roleServices;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;


	@GetMapping("/{id}")
	private ResponseEntity<RoleDTO> getVille(@PathVariable Long id)
	{
		RoleDTO role = roleServices.getRoles(id);
		return new ResponseEntity<>(role, HttpStatus.OK);
	}


	@GetMapping("/all")
	public List<Role> getAllActivites() {
		return roleServices.getAllRoles();
	}


	 @GetMapping("/allPagable")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
	{
		PageableResponseDTO roles = roleServices.getAllRolesPagebal(pageNo,pageSize);
		return new ResponseEntity<>(roles , HttpStatus.OK);
	}

	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		  roleServices.deleteActivitesStatut(id);
		 return new ResponseEntity<>(new MessageResponse("Rôle supprimé.","success") , HttpStatus.OK);
	 }

	 @PostMapping
	 public ResponseEntity<?> addActivite(@Valid @RequestBody RoleDTO roleDTO)
	 {

		if(roleRepository.existsByLibelleAdd(roleDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le Libelle existe déjà !" , "warning"));
		}

		roleServices.addActivites(roleDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Rôle ajouté","success"));
	 }

	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id, @Valid @RequestBody RoleDTO roleDTO) {

			if(roleRepository.existsByLibelleModif(roleDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libelle existe déjà !" , "warning"));
			}
			if(roleDTO.getStatut() == null || roleDTO.getStatut() == "")
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
			}
	        roleServices.updateActivites(id, roleDTO) ;
	        return new ResponseEntity<>(new MessageResponse("Rôle modifié.","success"),HttpStatus.OK);
	    }

	 @GetMapping("/affectToUSer")
	 public ResponseEntity<?> getAllRolesAffectToUser(@RequestParam(value = "idUser") Long id)
	 {
		 if(!userRepository.existsUserWithId(id))
		 {
			 return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Utilisateur non trouvé !" , "warning"));
		 }
		List<RoleDTO>  roleDTOs = roleServices.getAllRoleAffectteToUser(id);
		return new ResponseEntity<>(roleDTOs , HttpStatus.OK);
	 }

	 @GetMapping("/noAffectToUSer")
	 public ResponseEntity<?> getAllRolesNotAffectToUser(@RequestParam(value = "idUser", required = true) Long id)
	 {
		 if(!userRepository.existsUserWithId(id))
		 {
			 return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Utilisateur non trouvé !" , "warning"));
		 }
		List<RoleDTO> roleDTOs = roleServices.getAllRoleNotAffectteToUser(id);
		return new ResponseEntity<>(roleDTOs , HttpStatus.OK);
	 }


	 @GetMapping("/listRole")
		public List<ListApis> getAllActivitesApis() {
			List<Role> roles  = roleServices.getAllRoles();
			List<ListApis> listApis = roles.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
			return listApis;
		}

		private ListApis mapToApisList(Role role)
		{
			ListApis listApis = new ListApis();
			listApis.setId(role.getId());
			listApis.setCode(role.getCode());
			listApis.setLibelle(role.getLibelle());
			return listApis;
		}

}
