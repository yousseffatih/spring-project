package com.exemple.security.services.parametrage.Role;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.Numero;
import com.exemple.security.entity.Role;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.RoleDTO;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.repository.RoleRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;

@Service
public class RoleServicesImp implements InRoleServices{

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private InNumeroService numeroService;

	@Autowired
	private NumeroRepository numeroRepository;


	@Override
	public List<Role> getAllRoles() {
		List<Role> roles = roleRepository.findAllWithStatus();
		return roles;
	}

	@Override
	public RoleDTO getRoles(Long id)
	{
		Role role = roleRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("Role", "id", id));
		return mapToDTO(role);
	}

	@Override
	public PageableResponseDTO getAllRolesPagebal(int pageNo , int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Role> r = roleRepository.findallStatutsPa(pageable);
		List<RoleDTO> activites = r.getContent().stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		PageableResponseDTO  pageableResponseDTO = new PageableResponseDTO();
		pageableResponseDTO.setContent(activites);
		pageableResponseDTO.setPageNo(pageNo);
		pageableResponseDTO.setPageSize(pageSize);
		pageableResponseDTO.setTotalElement(r.getTotalElements());
		pageableResponseDTO.setTotlaPages(r.getTotalPages());
		pageableResponseDTO.setLast(r.isLast());
		return pageableResponseDTO;
	}

	@Override
	public Role addActivites(RoleDTO roleDTO) {
		Role role=new Role();

		List<Numero> numeros = numeroRepository.findByCode("Role");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");

			numeroRepository.save(numero);
			role.setCode(numeroService.genrateNumero(numero));
		}

		role.setLibelle(roleDTO.getLibelle());
		role.setName(roleDTO.getNom());
		role.setStatut(GlobalConstants.STATUT_ACTIF);
		role.setDateCreation(new Date());
		return roleRepository.save(role);
	}

	@Override
	public Role deleteActivitesStatut(Long id) {
		 Role role = roleRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
		 role.setStatut(GlobalConstants.STATUT_DELETE);
		 role.setDateDesactivation(new Date());
	     return  roleRepository.save(role);
	}

	@Override
	public Role updateActivites(Long id, RoleDTO roleDTO) {
		Role role = roleRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Role", "id", id));
		role.setLibelle(roleDTO.getLibelle());
		role.setName(roleDTO.getNom());
		role.setDateModification(new Date());
		role.setStatut(GlobalConstants.getStatusFromDescription(roleDTO.getStatut()));

		return roleRepository.save(role);
	}

	@Override
	public List<RoleDTO> getAllRoleAffectteToUser(Long id) {
		List<Role> roles = roleRepository.findRolesAffectedToUser(id);
		List<RoleDTO> rolesDtos = roles.stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		return rolesDtos;
	}


	@Override
	public List<RoleDTO> getAllRoleNotAffectteToUser( Long id) {
		List<Role> roles = roleRepository.findRolesNotAffectedToUser(id);
		List<RoleDTO> rolesDtos = roles.stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
		return rolesDtos;
	}

	private RoleDTO mapToDTO(Role role)
	{
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(role.getId());
		roleDTO.setCode(role.getCode());
		roleDTO.setLibelle(role.getLibelle());
		roleDTO.setNom(role.getName());
		roleDTO.setStatut(GlobalConstants.getStatusDescription(role.getStatut()));
		roleDTO.setDateCreation(role.getDateCreation());
		roleDTO.setDateDesactivation(role.getDateDesactivation());
		roleDTO.setDateModification(role.getDateModification());
		return roleDTO;
	}

}
