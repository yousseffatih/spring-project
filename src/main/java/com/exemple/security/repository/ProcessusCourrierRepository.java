package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.security.entity.ProcessusCourrier;

@Repository
public interface ProcessusCourrierRepository extends JpaRepository<ProcessusCourrier, Long>{

	@Query(value = "select *"
			+ " from processus_courrier pc "
			+ " where pc.statut not in ('-1','-2') "
			+ " and pc.courrier_id = :val "
			+ " order by pc.id" , nativeQuery = true)
	List<ProcessusCourrier> findAllByIDCourriersStatut(@Param("val") Long val);

	@Query(value = "select *"
			+ " from processus_courrier pc "
			+ " where pc.statut in (:statut) "
			+ " and pc.courrier_id = :val "
			+ " order by pc.id" , nativeQuery = true)
	List<ProcessusCourrier> findAllByIDCourriersStatut(@Param("val") Long val, @Param("statut") String statut);

	@Query(value = "select *"
			+ " from processus_courrier pc "
			+ " where pc.statut in (:statut) "
			+ " and pc.courrier_id = :val "
			+ " and pc.orderpm = :ordre "
			+ " order by pc.id" , nativeQuery = true)
	List<ProcessusCourrier> findAllByIDCourriersStatut(@Param("val") Long val, @Param("statut") String statut , @Param("ordre") Integer ordre);

	@Query(value = "select *"
			+ " from processus_courrier pc "
			+ " where pc.statut not in ('-1','-2') "
			+ " and pc.courrier_id = :val "
			+ " order by pc.id" , nativeQuery = true)
	Page<ProcessusCourrier> findAllPagebaleByIDCourriers(@Param("val") Long val , Pageable pageable);

	@Query("select pc"
			+ " from ProcessusCourrier pc "
			+ " where pc.statut not in ('-1','-2') "
			+ "and pc.id = :val")
	Optional<ProcessusCourrier> findByIdStatut(@Param("val") Long val);

	@Query(value= """
			select *
			from processus_courrier pc
			where pc.statut not in ('-1','-2')
			and processus_id_ = :val
			""" , nativeQuery = true)
	Optional<ProcessusCourrier> findByIdProcessusStatut(@Param("val") Long val);

	@Query("select "
			+ " case when count(pc)> 0 then true "
			+ " else false end "
			+ " from ProcessusCourrier pc "
			+ " where lower(pc.code) = lower(:val)  and pc.statut in('0','1')")
	boolean existsByCodeAdd(@Param("val") String val);


	@Query("select "
			+ " case when count(pc)> 0 then true "
			+ " else false end "
			+ " from ProcessusCourrier pc "
			+ " where lower(pc.libelle) = lower(:val)  and pc.statut in('0','1')")
	boolean existsByLibelleAdd(@Param("val") String val);

	@Query("select "
			+ " case when count(pc)> 0 then true "
			+ " else false end "
			+ " from ProcessusCourrier pc "
			+ " where lower(code) like lower(:val) and pc.statut not in('-1','-2')"
			+ " and pc.id <> :id ")
	boolean existsByCodeModif(@Param("val") String val, @Param("id") Long id);

	@Query("select "
			+ " case when count(pc)> 0 then true "
			+ " else false end "
			+ " from ProcessusCourrier pc "
			+ " where lower(libelle) like lower(:val) and pc.statut not in('-1','-2')"
			+ " and pc.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);
}
