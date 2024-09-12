package com.exemple.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exemple.security.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{
	
	@Query("SELECT d "
	 		+ " FROM UserRole d "
	 		+ " WHERE user.id = :idUser "
	 		+ " and d.statut = '1' ")
	 List<UserRole> getDroits(@Param("idUser") Integer idUser);
}
