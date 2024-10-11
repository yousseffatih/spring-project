package com.exemple.security.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.security.entity.Activites;

@Repository
public interface ActivitesRepository extends JpaRepository<Activites, Long>{
	
	Optional<Activites> findById(Long id);
	
	@Query("select a"
			+ " from Activites a "
			+ " where a.statut in ('0','1')")
	List<Activites> findAllWithStatus();
	
	
	@Query("select a"
			+ " from Activites a "
			+ " where a.statut in ('0','1')"
			+ "and a.id = :val")
	Optional<Activites> findByIdStatut(@Param("val") Long val);
	
	@Query("select a"
			+ " from Activites a "
			+ " where a.statut in ('0','1')")
    Page<Activites> findallStatutsPa(Pageable pageable);
	
	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Activites a "
			+ " where lower(a.code) = lower(:val)  and a.statut in('0','1')")
	boolean existsByCodeAdd(@Param("val") String val);

	
	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Activites a "
			+ " where lower(a.libelle) = lower(:val)  and a.statut in('0','1')")
	boolean existsByLibelleAdd(@Param("val") String val);
	
	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Activites a "
			+ " where lower(code) like lower(:val) "
			+ " and a.statut not in('-1','-2') "
			+ " and a.id <> :id ")
	boolean existsByCodeModif(@Param("val") String val, @Param("id") Long id);
	
	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Activites a "
			+ " where lower(libelle) like lower(:val) "
			+ " and a.statut not in('-1','-2') "
			+ " and a.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);
}
