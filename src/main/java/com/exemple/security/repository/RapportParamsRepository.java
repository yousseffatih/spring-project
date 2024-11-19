package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exemple.security.entity.RapportsParams;

public interface RapportParamsRepository extends JpaRepository<RapportsParams, Long>{



	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from RapportsParams a "
			+ " where a.orderParam = :val  and a.statut in('0','1') ")
	boolean existsByOrderAdd(@Param("val") Integer val);

	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from RapportsParams a "
			+ " where a.orderParam =:val "
			+ " and a.statut not in('-1','-2') "
			+ " and a.id <> :id ")
	boolean existsByOrderModif(@Param("val") Integer val, @Param("id") Long id);

	@Override
	Optional<RapportsParams> findById(Long id);

	@Query("select a"
			+ " from RapportsParams a "
			+ " where a.statut in ('0','1')")
	List<RapportsParams> findAllWithStatus();


	@Query("select a"
			+ " from RapportsParams a "
			+ " where a.statut in ('0','1')"
			+ " and a.id = :val "
			+ " order by a.libelle ")
	Optional<RapportsParams> findByIdStatut(@Param("val") Long val);

	@Query(value = """
			SELECT * FROM public.rapports_params
			WHERE statut IN ('1','0')
			AND rapport_id = 1
			ORDER BY order_param
			""", nativeQuery = true )
    List<RapportsParams> findallStatutsPa(@Param("val") Long val);


	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from RapportsParams a "
			+ " where lower(a.libelle) = lower(:val)  and a.statut in('0','1') ")
	boolean existsByLibelleAdd(@Param("val") String val);


	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from RapportsParams a "
			+ " where lower(libelle) like lower(:val) "
			+ " and a.statut not in('-1','-2') "
			+ " and a.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);
}
