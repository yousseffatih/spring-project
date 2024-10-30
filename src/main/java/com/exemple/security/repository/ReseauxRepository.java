package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.security.entity.Reseaux;

@Repository
public interface ReseauxRepository extends JpaRepository<Reseaux, Integer>{

	Optional<Reseaux> findById(Long id);

	@Query("select r"
			+ " from Reseaux r "
			+ " where r.statut in ('0','1') "
			+ " order by r.libelle")
	List<Reseaux> findAllStatus();

	@Query("select r"
			+ " from Reseaux r "
			+ " where r.statut in ('0','1')"
			+ " order by r.libelle")
    Page<Reseaux> findallStatutsPa(Pageable pageable);

	@Query("select r"
			+ " from Reseaux r "
			+ " where r.statut in ('0','1')"
			+ "and r.id = :val")
	Optional<Reseaux> findByIdStatut(@Param("val") Long val);

	@Query("select "
			+ " case when count(r)> 0 then true "
			+ " else false end "
			+ " from Reseaux r "
			+ " where lower(r.code) = lower(:val)  and r.statut in('0','1')")
	boolean existsByCodeAdd(@Param("val") String val);

	@Query("select "
			+ " case when count(r)> 0 then true "
			+ " else false end "
			+ " from Reseaux r "
			+ " where lower(r.libelle) = lower(:val)  and r.statut in('0','1')")
	boolean existsByLibelleAdd(@Param("val") String val);

	@Query("select "
			+ " case when count(r)> 0 then true "
			+ " else false end "
			+ " from Reseaux r "
			+ " where lower(code) like lower(:val) and r.statut not in('-1','-2')"
			+ " and r.id <> :id ")
	boolean existsByCodeModif(@Param("val") String val, @Param("id") Long id);

	@Query("select "
			+ " case when count(r)> 0 then true "
			+ " else false end "
			+ " from Reseaux r "
			+ " where lower(libelle) like lower(:val) and r.statut not in('-1','-2')"
			+ " and r.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);
}
