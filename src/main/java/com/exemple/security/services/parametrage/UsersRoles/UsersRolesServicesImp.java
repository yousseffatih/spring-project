package com.exemple.security.services.parametrage.UsersRoles;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.Role;
import com.exemple.security.entity.User;
import com.exemple.security.entity.UserRole;
import com.exemple.security.exception.CustomException;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.repository.RoleRepository;
import com.exemple.security.repository.UserRepository;
import com.exemple.security.repository.UserRoleRepository;

@Service
public class UsersRolesServicesImp implements InUserRolesServices{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public void affectRoleToUser(Long userId, Long roleId) {

        User user = userRepository.findById(userId)
        		.orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

		List<UserRole> userRoles = userRoleRepository.getDroits(user.getId());
		user.setRoles(userRoles);

        Role role = roleRepository.findByIdStatut(roleId)
        		.orElseThrow(()-> new ResourceNotFoundException("Role", "id", roleId));

        boolean roleAlreadyAffected = user.getRoles().stream()
                .anyMatch(userRole -> userRole.getRole().getId().equals(roleId));

        if(roleAlreadyAffected)
        {
        	throw new CustomException("Ce rôle est déjà affecté à cet utilisateur !");
        }

        UserRole userRole = new UserRole();
    	userRole.setRole(role);
    	userRole.setUser(user);
    	userRole.setDateCreation(new Date());
    	userRole.setStatut(GlobalConstants.STATUT_ACTIF);
    	userRoleRepository.save(userRole);

    }

    @Override
    public void unaffectRoleToUser(Long userId, Long roleId) {

        User user = userRepository.findById(userId)
        		.orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        List<UserRole> userRoles = userRoleRepository.getDroits(userId);

		user.setRoles(userRoles);


        Role role = roleRepository.findByIdStatut(roleId)
        		.orElseThrow(()-> new ResourceNotFoundException("Role", "id", roleId));

        boolean roleAlreadyAffected = user.getRoles().stream()
                .anyMatch(userRole -> userRole.getRole().getId().equals(roleId));


        if(!roleAlreadyAffected)
        {
        	throw new CustomException("Ce rôle n'est pas affecté à cet utilisateur !");
        }

    	UserRole userRole = userRoleRepository.findByUserAndRole(userId, roleId);
    	userRole.setDateDesactivation(new Date());
    	userRole.setStatut(GlobalConstants.STATUT_DELETE);
    	userRoleRepository.save(userRole);

    }
}
