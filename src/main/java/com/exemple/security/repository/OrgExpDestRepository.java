package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exemple.security.entity.OrgExpDest;

public interface OrgExpDestRepository extends JpaRepository<OrgExpDest, Long>{

@Override
Optional<OrgExpDest> findById(Long id);

	@Query("select o"
			+ " from OrgExpDest o "
			+ " where o.statut in ('0','1')"
			+ "and o.id = :val")
	Optional<OrgExpDest> findByIdStatut(@Param("val") Long val);

	@Query("select o"
			+ " from OrgExpDest o "
			+ " where o.statut in ('0','1') "
			+ " order by o.libelle")
	List<OrgExpDest> findAllWithStatus();

	@Query("select o"
			+ " from OrgExpDest o "
			+ " where o.statut in ('0','1')"
			+ " order by o.libelle ")
    Page<OrgExpDest> findallStatutsPa(Pageable pageable);

	@Query("select "
			+ " case when count(o)> 0 then true "
			+ " else false end "
			+ " from OrgExpDest o "
			+ " where lower(o.code) = lower(:val)  and o.statut in('0','1')")
	boolean existsByCodeAdd(@Param("val") String val);


	@Query("select "
			+ " case when count(o)> 0 then true "
			+ " else false end "
			+ " from OrgExpDest o "
			+ " where lower(o.libelle) = lower(:val)  and o.statut in('0','1')")
	boolean existsByLibelleAdd(@Param("val") String val);

	@Query("select "
			+ " case when count(o)> 0 then true "
			+ " else false end "
			+ " from OrgExpDest o "
			+ " where lower(code) like lower(:val) and o.statut not in('-1','-2')"
			+ " and o.id <> :id ")
	boolean existsByCodeModif(@Param("val") String val, @Param("id") Long id);

	@Query("select "
			+ " case when count(o)> 0 then true "
			+ " else false end "
			+ " from OrgExpDest o "
			+ " where lower(libelle) like lower(:val) and o.statut not in('-1','-2')"
			+ " and o.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);
}
