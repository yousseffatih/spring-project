package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.security.entity.ProcessusModel;

@Repository
public interface ProcessusModelRepository extends JpaRepository<ProcessusModel, Long>{
	@Override
	Optional<ProcessusModel> findById(Long id);

	@Query("select p"
			+ " from ProcessusModel p "
			+ " where p.statut in ('0','1')")
	List<ProcessusModel> findAllWithStatus();


	@Query(value = "select p.*"
			+ " from processus_model p "
			+ " where p.statut in ('0','1') "
			+ " and p.type_courriers_id = :val "
			+ " Order by orderpm", nativeQuery = true)
	List<ProcessusModel> findAllByTypeID(@Param("val") Long val);

	@Query(value = "select p.*"
			+ " from processus_model p "
			+ " where p.statut in ('0','1') "
			+ " and p.type_courriers_id = :val "
			+ " order by orderpm", nativeQuery = true)
	Page<ProcessusModel> findAllByTypeIDPagabel(@Param("val") Long val , Pageable pageable);

	@Query("select p"
			+ " from ProcessusModel p "
			+ " where (p.statut = '1' OR p.statut = '0')"
			+ "and p.id = :val")
	Optional<ProcessusModel> findByIdStatut(@Param("val") Long val);

	@Query("select p"
			+ " from ProcessusModel p "
			+ " where p.statut in ('0','1')")
    Page<ProcessusModel> findallStatutsPa(Pageable pageable);

	@Query("select "
			+ " case when count(p)> 0 then true "
			+ " else false end "
			+ " from ProcessusModel p "
			+ " where lower(p.code) = lower(:val)  and p.statut in('0','1')")
	boolean existsByCodeAdd(@Param("val") String val);

	@Query(value ="select "
			+ " case when count(p)> 0 then true "
			+ " else false end "
			+ " from processus_model p "
			+ " where p.type_courriers_id = :typeId "
			+ " and p.orderpm = :val "
			+ " and p.statut in('0','1') " , nativeQuery = true)
	boolean existsByOrderProADD(@Param("val") Integer val , @Param("typeId") Long typeId);

	@Query("select "
			+ " case when count(p)> 0 then true "
			+ " else false end "
			+ " from ProcessusModel p "
			+ " where lower(p.libelle) = lower(:val)  and p.statut in('0','1')")
	boolean existsByLibelleAdd(@Param("val") String val);

	@Query("select "
			+ " case when count(p)> 0 then true "
			+ " else false end "
			+ " from ProcessusModel p "
			+ " where lower(code) like lower(:val) and p.statut not in('-1','-2')"
			+ " and p.id <> :id ")
	boolean existsByCodeModif(@Param("val") String val, @Param("id") Long id);

	@Query("select "
			+ " case when count(p)> 0 then true "
			+ " else false end "
			+ " from ProcessusModel p "
			+ " where lower(libelle) like lower(:val) and p.statut not in('-1','-2') "
			+ " and p.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);

	@Query(value ="select "
			+ " case when count(p)> 0 then true "
			+ " else false end "
			+ " from processus_model p "
			+ " where p.type_courriers_id = :typeId "
			+ " and p.orderpm = :val and  p.statut not in('-1','-2') "
			+ " and p.id <> :id" , nativeQuery = true)
	boolean existsByOrderProMod(@Param("val") Integer val , @Param("typeId") Long typeId , @Param("id") Long id);
}
