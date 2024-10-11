package com.exemple.security.services.parametrage.UsersRoles;

public interface InUserRolesServices {

	void unaffectRoleToUser(Long userId, Long roleId);

	void affectRoleToUser(Long userId, Long roleId);

}
