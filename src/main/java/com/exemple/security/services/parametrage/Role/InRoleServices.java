package com.exemple.security.services.parametrage.Role;

import java.util.List;

import com.exemple.security.entity.Role;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.RoleDTO;

public interface InRoleServices {

	List<Role> getAllRoles();

	Role getRoles(Long id);

	PageableResponseDTO getAllRolesPagebal(int pageNo, int pageSize);

	Role addActivites(RoleDTO roleDTO);

	Role deleteActivitesStatut(Long id);

	Role updateActivites(Long id, RoleDTO roleDTO);

	List<RoleDTO> getAllRoleAffectteToUser(Long id);

	List<RoleDTO> getAllRoleNotAffectteToUser(Long id);
}
