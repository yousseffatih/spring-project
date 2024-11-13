package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exemple.security.entity.Rapport;

public interface RapportRepository extends JpaRepository<Rapport, Long>{

	@Query(value = """
			SELECT *
			FROM rapport r
			WHERE r.statut IN ('1', '0')
			ORDER BY orderrp
			 """,
		nativeQuery = true )
	List<Rapport> findAllWithStatut();

	@Query("select r"
			+ " from Rapport r "
			+ " where r.statut in ('0','1')"
			+ "and r.id = :val")
	Optional<Rapport> findByIdStatut(@Param("val") Long val);


}
