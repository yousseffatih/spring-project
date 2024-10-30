package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.security.entity.Clients;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {

	@Override
	Optional<Clients> findById(Long id);

	@Query("select a"
			+ " from Clients a "
			+ " where a.statut in ('0','1')")
	List<Clients> findAllWithStatus();


	@Query("select a"
			+ " from Clients a "
			+ " where a.statut in ('0','1')"
			+ " and a.id = :val "
			+ " order by a.libelle ")
	Optional<Clients> findByIdStatut(@Param("val") Long val);

	@Query("select a"
			+ " from Clients a "
			+ " where a.statut in ('0','1') "
			+ " order by a.libelle ")
    Page<Clients> findallStatutsPa(Pageable pageable);


	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Clients a "
			+ " where lower(a.libelle) = lower(:val)  and a.statut in('0','1') ")
	boolean existsByLibelleAdd(@Param("val") String val);


	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Clients a "
			+ " where lower(libelle) like lower(:val) "
			+ " and a.statut not in('-1','-2') "
			+ " and a.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);
}
