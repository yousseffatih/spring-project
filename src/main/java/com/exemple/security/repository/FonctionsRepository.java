package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.security.entity.Fonctions;

@Repository
public interface FonctionsRepository extends JpaRepository<Fonctions, Long>{

	@Override
	Optional<Fonctions> findById(Long id);

	@Query("select f"
			+ " from Fonctions f "
			+ " where f.statut in ('0','1') "
			+ " order by f.libelle")
	List<Fonctions> findAllStatus();

	@Query("select f"
			+ " from Fonctions f "
			+ " where f.statut in ('0','1')"
			+ "and f.id = :val")
	Optional<Fonctions> findByIdStatut(@Param("val") Long val);


	@Query("select f"
			+ " from Fonctions f "
			+ " where f.statut in ('0','1') "
			+ " order by f.libelle")
    Page<Fonctions> findallStatutsPa(Pageable pageable);

	@Query("select "
			+ " case when count(f)> 0 then true "
			+ " else false end "
			+ " from Fonctions f "
			+ " where lower(f.code) = lower(:val)  and f.statut in('0','1')")
	boolean existsByCodeAdd(@Param("val") String val);

	@Query("select "
			+ " case when count(f)> 0 then true "
			+ " else false end "
			+ " from Fonctions f "
			+ " where lower(f.libelle) = lower(:val)  and f.statut in('0','1')")
	boolean existsByLibelleAdd(@Param("val") String val);

	@Query("select "
			+ " case when count(f)> 0 then true "
			+ " else false end "
			+ " from Fonctions f "
			+ " where lower(code) like lower(:val) and f.statut not in('-1','-2')"
			+ " and f.id <> :id ")
	boolean existsByCodeModif(@Param("val") String val, @Param("id") Long id);

	@Query("select "
			+ " case when count(f)> 0 then true "
			+ " else false end "
			+ " from Fonctions f "
			+ " where lower(libelle) like lower(:val) and f.statut not in('-1','-2')"
			+ " and f.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);
}
