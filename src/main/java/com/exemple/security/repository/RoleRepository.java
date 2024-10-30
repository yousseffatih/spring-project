package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exemple.security.entity.Role;



public interface RoleRepository extends JpaRepository<Role, Long>{

	@Override
	Optional<Role> findById(Long id);

	@Query("select r"
			+ " from Role r "
			+ " where r.statut in ('0','1') "
			+ " order by r.libelle")
	List<Role> findAllWithStatus();


	@Query("select r"
			+ " from Role r "
			+ " where r.statut in ('0','1')"
			+ " and r.id = :val")
	Optional<Role> findByIdStatut(@Param("val") Long val);

	@Query("select r"
			+ " from Role r "
			+ " where r.statut in ('0','1') "
			+ " order by r.libelle")
    Page<Role> findallStatutsPa(Pageable pageable);

	@Query("select "
			+ " case when count(r)> 0 then true "
			+ " else false end "
			+ " from Role r "
			+ " where lower(r.code) = lower(:val)  and r.statut in('0','1')")
	boolean existsByCodeAdd(@Param("val") String val);


	@Query("select "
			+ " case when count(r)> 0 then true "
			+ " else false end "
			+ " from Role r "
			+ " where lower(r.libelle) = lower(:val)  and r.statut in('0','1')")
	boolean existsByLibelleAdd(@Param("val") String val);

	@Query("select "
			+ " case when count(r)> 0 then true "
			+ " else false end "
			+ " from Role r "
			+ " where lower(code) like lower(:val) and r.statut not in('-1','-2')"
			+ " and r.id <> :id ")
	boolean existsByCodeModif(@Param("val") String val, @Param("id") Long id);

	@Query("select "
			+ " case when count(r)> 0 then true "
			+ " else false end "
			+ " from Role r "
			+ " where lower(libelle) like lower(:val) and r.statut not in('-1','-2')"
			+ " and r.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);

	 @Query("SELECT r "
	 		+ " FROM Role r "
	 		+ " WHERE r.id NOT IN "
	 		+ "	(SELECT ur.role.id "
	 		+ "	FROM UserRole ur WHERE ur.user.id = :userId "
	 		+ " AND ur.statut IN ('0','1'))"
	 		+ " AND r.statut in ('0','1')"
	 		+ " ORDER BY r.libelle")
	 List<Role> findRolesNotAffectedToUser(@Param("userId") Long userId);

	 @Query("SELECT r "
		 		+ " FROM Role r "
		 		+ " WHERE r.id IN "
		 		+ "	(SELECT ur.role.id "
		 		+ "	FROM UserRole ur WHERE ur.user.id = :userId "
		 		+ " AND ur.statut IN ('0','1'))"
		 		+ " AND r.statut in ('0','1')")
	 List<Role> findRolesAffectedToUser(@Param("userId") Long userId);


	 @Query("SELECT r "
	 		+ "FROM Role r "
	 		+ "WHERE r.statut IN ('1','0') "
	 		+ "AND r.id IN :roleIds "
	 		+ "ORDER BY r.libelle")
	  List<Role> findRolesByIds(@Param("roleIds") List<Long> roleIds);
}
