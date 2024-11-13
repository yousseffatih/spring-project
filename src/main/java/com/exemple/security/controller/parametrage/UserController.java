package com.exemple.security.controller.parametrage;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.User;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.UserDTO;
import com.exemple.security.repository.UserRepository;
import com.exemple.security.services.parametrage.User.InUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private InUserService userService;

	@Autowired
	private UserRepository userRepository;


	@GetMapping("/{id}")
	private ResponseEntity<UserDTO> getVille(@PathVariable Long id)
	{
		UserDTO userDTO = userService.getUsers(id);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}



	 @GetMapping("/allPagable")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
	{
		PageableResponseDTO users = userService.getAllUsersPagebal(pageNo,pageSize);
		return new ResponseEntity<>(users , HttpStatus.OK);
	}

	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		  userService.deleteUsersStatut(id);
		 return new ResponseEntity<>(new MessageResponse("Utilisateur supprimé.","success") , HttpStatus.OK);
	 }

	 @PostMapping
	 public ResponseEntity<?> addActivite(@Valid @RequestBody UserDTO userDTO)
	 {

		if(userRepository.existsByLibelleAdd(userDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
		}
		if(userRepository.existsUserWithUsername(userDTO.getUsername()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("L'utilisateur existe déjà !" , "warning"));
		}

		userService.addUsers(userDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Utilisateur ajouté.","success"));
	 }

//	 @PutMapping("/{id}")
//	    public ResponseEntity<?> updateActivites(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
//
//			if(userRepository.existsByLibelleModif(userDTO.getLibelle(), id))
//			{
//				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le libellé existe déjà !" , "warning"));
//			}
//
//			if(userDTO.getStatut() == null || userDTO.getStatut() == "")
//			{
//				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Le statut est obligatoire !" , "warning"));
//			}
//
//	        userService.updateUsers(id, userDTO);
//	        return new ResponseEntity<>(new MessageResponse("Utilisateur modifié","success"),HttpStatus.OK);
//	   }

	@GetMapping("/listUsers")
	public List<ListApis> getAllActivitesApis() {
		List<User> users  = userService.getUsers();
		List<ListApis> listApis = users.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
		return listApis;
	}

	private ListApis mapToApisList(User user)
	{
		ListApis listApis = new ListApis();
		listApis.setId(user.getId());
		listApis.setCode(user.getCode());
		listApis.setLibelle(user.getEmployes().getNom() + " " + user.getEmployes().getPrenom());
		return listApis;
	}

}
