package com.exemple.security.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exemple.security.entity.User;




public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByUsername(String username);
	
	//@Query("SELECT u FROM User u LEFT JOIN FETCH u.userRoles ur LEFT JOIN FETCH ur.role WHERE u.username = :username")
	//List<UserWithRoleDTO> findByUsernameWithRoles(@Param("username") String username);
	
	//	@Query("SELECT u FROM User u LEFT JOIN FETCH u.userRoles ur LEFT JOIN FETCH ur.role WHERE u.username = :username")
	//	List<User> findByUsernameWithRoles(@Param("username") String username);
	
	//	@Query(value = "SELECT u.id as userId, u.email, u.username, u.password, "
	//	        + "r.id as roleId, r.name as roleName, r.status as roleStatus "
	//	        + "FROM user u "
	//	        + "LEFT JOIN user_role ur ON u.id = ur.user_id "
	//	        + "LEFT JOIN role r ON ur.role_id = r.id "
	//	        + "WHERE u.username = :username", nativeQuery = true)
	//	Optional<List<UserWithRoleDTO>> findByUsernameWithRoles(@Param("username") String username);
	
	//	@Query(value = "SELECT u.id as userId, u.email, u.username, u.password, "
	//	        + "r.id as roleId, r.name as roleName, r.status as roleStatus "
	//	        + "FROM user u "
	//	        + "LEFT JOIN user_role ur ON u.id = ur.user_id "
	//	        + "LEFT JOIN role r ON ur.role_id = r.id "
	//	        + "WHERE u.username = :username", nativeQuery = true)
	//	List<Object[]> findByUsernameWithRoles(@Param("username") String username);

}
