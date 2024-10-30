package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.security.entity.Villes;

@Repository
public interface VillesRepository extends JpaRepository<Villes, Long>{


	@Override
	Optional<Villes> findById(Long id);

	@Query("select v"
			+ " from Villes v "
			+ " where v.statut in ('0','1') "
			+ " order by v.libelle")
	List<Villes> findAllStatus();

	@Query("select v"
			+ " from Villes v "
			+ " where v.statut in ('0','1')"
			+ "and v.id = :val")
	Optional<Villes> findByIdStatut(@Param("val") Long val);

	@Query("select v"
			+ " from Villes v "
			+ " where v.statut in ('0','1') "
			+ " order by v.libelle")
    Page<Villes> findallStatutsPa(Pageable pageable);

	@Query("select "
			+ " case when count(v)> 0 then true "
			+ " else false end "
			+ " from Villes v "
			+ " where lower(v.code) = lower(:val)  and v.statut in('0','1')")
	boolean existsByCodeAdd(@Param("val") String val);

	@Query("select "
			+ " case when count(v)> 0 then true "
			+ " else false end "
			+ " from Villes v "
			+ " where lower(v.libelle) = lower(:val)  and v.statut in('0','1')")
	boolean existsByLibelleAdd(@Param("val") String val);

	@Query("select "
			+ " case when count(v)> 0 then true "
			+ " else false end "
			+ " from Villes v "
			+ " where lower(code) like lower(:val) and v.statut not in('-1','-2')"
			+ " and v.id <> :id ")
	boolean existsByCodeModif(@Param("val") String val, @Param("id") Long id);

	@Query("select "
			+ " case when count(v)> 0 then true "
			+ " else false end "
			+ " from Villes v "
			+ " where lower(libelle) like lower(:val) and v.statut not in('-1','-2')"
			+ " and v.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);

}
