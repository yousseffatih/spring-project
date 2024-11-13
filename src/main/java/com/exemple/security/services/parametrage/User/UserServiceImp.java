package com.exemple.security.services.parametrage.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.Employes;
import com.exemple.security.entity.Numero;
import com.exemple.security.entity.User;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.UserDTO;
import com.exemple.security.repository.EmployesRepository;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.repository.UserRepository;
import com.exemple.security.services.parametrage.Numero.NumeroService;

@Service
public class UserServiceImp implements InUserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmployesRepository employesRepository;

	@Autowired
	private NumeroRepository numeroRepository;

	@Autowired
	private NumeroService numeroService;

	@Autowired
	private  PasswordEncoder passwordEncoder;

	@Override
	public List<User> getUsers() {
		List<User> list = userRepository.findUsersByStatut();
		return list;
	}

	@Override
	public UserDTO getUsers(Long id) {
		User user = userRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Utilisateur", "id", id));
		return mapToDTO(user);
	}

	@Override
	public PageableResponseDTO getAllUsersPagebal(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<User> v = userRepository.findallStatutsPa(pageable);
		List<UserDTO> userDTOs = v.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(userDTOs);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(v.getTotalElements());
		pageableResponseDTO.setTotlaPages(v.getTotalPages());
		pageableResponseDTO.setLast(v.isLast());
		return pageableResponseDTO;
	}

	@Override
	public UserDTO addUsers(UserDTO userDTO) {
		User addUser =new User();

		Employes employes = employesRepository.findByIdStatut(userDTO.getIdEmployer()).orElseThrow(()-> new ResourceNotFoundException("Employer", "id", userDTO.getIdEmployer()));
		List<Numero> numeros = numeroRepository.findByCode("Users");

		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())+ 1)+ "");

			numeroRepository.save(numero);
			addUser.setCode(numeroService.genrateNumero(numero));
		}

		addUser.setEmployes(employes);
		addUser.setLibelle(userDTO.getLibelle());
		addUser.setStatut(GlobalConstants.STATUT_ACTIF);
		addUser.setDateCreation(new Date());


		addUser.setUsername(userDTO.getUsername());
		addUser.setPassword(passwordEncoder.encode(userDTO.getUsername()));
		addUser.setFirst("1");

		return mapToDTO(userRepository.save(addUser));
	}

	@Override
	public UserDTO deleteUsersStatut(Long id) {
		User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
		user.setStatut(GlobalConstants.STATUT_DELETE);
	 user.setDateDesactivation(new Date());
     return mapToDTO(userRepository.save(user));
	}

//	@Override
//	public UserDTO updateUsers(Long id, UserDTO userDTO) {
//		User user = userRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Utilisateur", "id", id));
//		Employes employes = employesRepository.findByIdStatut(userDTO.getIdEmployer()).orElseThrow(()-> new ResourceNotFoundException("Employer", "id", userDTO.getIdEmployer()));
//		user.setEmployes(employes);
//		user.setLibelle(userDTO.getLibelle());
//		user.setStatut(GlobalConstants.getStatusFromDescription(userDTO.getStatut()));
//		user.setDateModification(new Date());
//		return mapToDTO(userRepository.save(user));
//	}

	private UserDTO mapToDTO(User x)
	{
		UserDTO dto = new UserDTO();
		dto.setId(x.getId());
		dto.setCode(x.getCode());
		dto.setLibelle(x.getLibelle());
		dto.setDateCreation(x.getDateCreation());
		dto.setDateModification(x.getDateModification());
		dto.setDateDesactivation(x.getDateDesactivation());
		dto.setStatut(GlobalConstants.getStatusDescription(x.getStatut()));

		dto.setUsername(x.getUsername());
		dto.setEmail(x.getEmployes().getEmail());
		dto.setIdEmployer(x.getEmployes().getId());
		dto.setEmployer(x.getEmployes().getLibelle());
		return dto;
	}
}
