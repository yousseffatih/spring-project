package com.exemple.security.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.security.entity.User;




@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);

	@Query("SELECT us FROM User us WHERE us.statut IN ('0', '1')")
	List<User> findUsersByStatut();


	 @Query("SELECT CASE WHEN COUNT(us) > 0 THEN true ELSE false END "
	            + "FROM User us "
	            + "WHERE us.statut IN ('0', '1') "
	            + "AND us.id = :val")
	 boolean existsUserWithId(@Param("val") Long val);

}
