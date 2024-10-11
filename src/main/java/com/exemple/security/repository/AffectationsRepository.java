package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exemple.security.entity.Affectations;

public interface AffectationsRepository extends JpaRepository<Affectations, Long>{
	
	Optional<Affectations> findById(Long id);
	
	@Query("select a"
			+ " from Affectations a "
			+ " where a.statut in ('0','1')")
	List<Affectations> findAllWithStatus();
	
	
	@Query("select a"
			+ " from Affectations a "
			+ " where (a.statut = '1' OR a.statut = '0')"
			+ "and a.id = :val")
	Optional<Affectations> findByIdStatut(@Param("val") Long val);
	
	@Query("select a"
			+ " from Affectations a "
			+ " where a.statut in ('0','1')")
    Page<Affectations> findallStatutsPa(Pageable pageable);
	
	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Affectations a "
			+ " where lower(a.code) = lower(:val)  and a.statut in('0','1')")
	boolean existsByCodeAdd(@Param("val") String val);

	
	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Affectations a "
			+ " where lower(a.libelle) = lower(:val)  and a.statut in('0','1')")
	boolean existsByLibelleAdd(@Param("val") String val);
	
	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Affectations a "
			+ " where lower(code) like lower(:val) and a.statut not in('-1','-2')"
			+ " and a.id <> :id ")
	boolean existsByCodeModif(@Param("val") String val, @Param("id") Long id);
	
	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Affectations a "
			+ " where lower(libelle) like lower(:val) and a.statut not in('-1','-2')"
			+ " and a.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);
}
