package com.exemple.security.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.security.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT us FROM User us WHERE us.statut IN ('0', '1') AND us.username =:val ")
	Optional<User> findByUsernameStatut(@Param("val")String username);

	@Query("SELECT us FROM User us WHERE us.statut IN ('0', '1')")
	List<User> findUsersByStatut();


	 @Query("SELECT CASE WHEN COUNT(us) > 0 THEN true ELSE false END "
	            + "FROM User us "
	            + "WHERE us.statut IN ('0', '1') "
	            + "AND us.id = :val")
	 boolean existsUserWithId(@Param("val") Long val);

	 @Query("SELECT CASE WHEN COUNT(us) > 0 THEN true ELSE false END "
	            + "FROM User us "
	            + "WHERE us.statut IN ('0', '1') "
	            + "AND us.username = :val")
	 boolean existsUserWithUsername(@Param("val") String val);


 	@Override
	Optional<User> findById(Long id);

	@Query("select a"
			+ " from User a "
			+ " where a.statut in ('0','1')")
	List<User> findAllWithStatus();


	@Query("select a"
			+ " from User a "
			+ " where a.statut in ('0','1')"
			+ " and a.id = :val "
			+ " order by a.libelle ")
	Optional<User> findByIdStatut(@Param("val") Long val);

	@Query("select a"
			+ " from User a "
			+ " where a.statut in ('0','1') "
			+ " order by a.libelle ")
    Page<User> findallStatutsPa(Pageable pageable);

	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from User a "
			+ " where lower(a.libelle) = lower(:val)  and a.statut in('0','1') ")
	boolean existsByLibelleAdd(@Param("val") String val);



	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from User a "
			+ " where lower(libelle) like lower(:val) "
			+ " and a.statut not in('-1','-2') "
			+ " and a.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);
	
	@Query(value = """ 
			SELECT 
			CASE WHEN COUNT(u)> 0 THEN true ELSE false END 
			from users u
			WHERE employes_id = :id
			""", nativeQuery = true)
	boolean existsByEmployerAdd(@Param("id") Long id);
	
	@Query(value = """ 
			SELECT 
			CASE WHEN COUNT(u)> 0 THEN true ELSE false END 
			from users u
			WHERE employes_id = :id
			AND u.id <> :idUser
			""", nativeQuery = true)
	boolean existsByEmployerModif(@Param("id") Long id , @Param("idUser") Long idUser);
	
}
