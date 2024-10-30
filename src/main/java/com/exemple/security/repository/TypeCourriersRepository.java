package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exemple.security.entity.TypeCourriers;

public interface TypeCourriersRepository extends JpaRepository<TypeCourriers, Long>{

	@Override
	Optional<TypeCourriers> findById(Long id);

	@Query("select t"
			+ " from TypeCourriers t "
			+ " where t.statut in ('0','1') "
			+ " order by t.libelle")
	List<TypeCourriers> findAllWithStatus();


	@Query("select t"
			+ " from TypeCourriers t "
			+ " where t.statut in ('0','1')"
			+ "and t.id = :val")
	Optional<TypeCourriers> findByIdStatut(@Param("val") Long val);

	@Query("select t"
			+ " from TypeCourriers t "
			+ " where t.statut in ('0','1') "
			+ " order by t.libelle")
    Page<TypeCourriers> findallStatutsPa(Pageable pageable);

	@Query("select "
			+ " case when count(t)> 0 then true "
			+ " else false end "
			+ " from TypeCourriers t "
			+ " where lower(t.code) = lower(:val)  and t.statut in('0','1')")
	boolean existsByCodeAdd(@Param("val") String val);


	@Query("select "
			+ " case when count(t)> 0 then true "
			+ " else false end "
			+ " from TypeCourriers t "
			+ " where lower(t.libelle) = lower(:val)  and t.statut in('0','1')")
	boolean existsByLibelleAdd(@Param("val") String val);

	@Query("select "
			+ " case when count(t)> 0 then true "
			+ " else false end "
			+ " from TypeCourriers t "
			+ " where lower(code) like lower(:val) and t.statut not in('-1','-2')"
			+ " and t.id <> :id ")
	boolean existsByCodeModif(@Param("val") String val, @Param("id") Long id);

	@Query("select "
			+ " case when count(t)> 0 then true "
			+ " else false end "
			+ " from TypeCourriers t "
			+ " where lower(libelle) like lower(:val) and t.statut not in('-1','-2')"
			+ " and t.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);
}
