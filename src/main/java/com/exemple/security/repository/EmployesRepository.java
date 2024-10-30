package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.security.entity.Employes;

@Repository
public interface EmployesRepository extends JpaRepository<Employes, Long>{

	@Override
	Optional<Employes> findById(Long id);

	@Query("select e"
			+ " from Employes e "
			+ " where e.statut in ('0','1') "
			+ " order by e.libelle ")
	List<Employes> findAllWithStatus();


	@Query("select e"
			+ " from Employes e "
			+ " where (e.statut = '1' OR e.statut = '0')"
			+ " and e.id = :val ")
	Optional<Employes> findByIdStatut(@Param("val") Long val);

	@Query("select e"
			+ " from Employes e "
			+ " where e.statut in ('0','1') "
			+ " order by e.libelle ")
    Page<Employes> findallStatutsPa(Pageable pageable);

	@Query("select "
			+ " case when count(e)> 0 then true "
			+ " else false end "
			+ " from Employes e "
			+ " where lower(e.code) = lower(:val)  and e.statut in('0','1')")
	boolean existsByCodeAdd(@Param("val") String val);

	@Query("select "
			+ " case when count(e)> 0 then true "
			+ " else false end "
			+ " from Employes e "
			+ " where lower(e.matricule) = lower(:val)  and e.statut in('0','1')")
	boolean existsByMatriculeAdd(@Param("val") String val);


	@Query("select "
			+ " case when count(e)> 0 then true "
			+ " else false end "
			+ " from Employes e "
			+ " where lower(e.libelle) = lower(:val)  and e.statut in('0','1')")
	boolean existsByLibelleAdd(@Param("val") String val);

	@Query("select "
			+ " case when count(e)> 0 then true "
			+ " else false end "
			+ " from Employes e "
			+ " where lower(code) like lower(:val) and e.statut not in('-1','-2')"
			+ " and e.id <> :id ")
	boolean existsByCodeModif(@Param("val") String val, @Param("id") Long id);

	@Query("select "
			+ " case when count(e)> 0 then true "
			+ " else false end "
			+ " from Employes e "
			+ " where lower(libelle) like lower(:val) and e.statut not in('-1','-2')"
			+ " and e.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);

	@Query("select "
			+ " case when count(e)> 0 then true "
			+ " else false end "
			+ " from Employes e "
			+ " where lower(e.matricule) = lower(:val)  and e.statut in('0','1') "
			+ " and e.id <> :id ")
	boolean existsByMatriculeModif(@Param("val") String val ,@Param("id") Long id);
}
