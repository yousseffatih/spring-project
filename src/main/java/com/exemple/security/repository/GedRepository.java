package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exemple.security.entity.Ged;






public interface GedRepository extends JpaRepository<Ged, Long>{

	@Query(value="SELECT * "
			+ "FROM ged g "
			+ "WHERE g.statut in ('0','1')"
			+ "AND g.context_file LIKE :val% ",nativeQuery = true)
	List<Ged> findAllByStartFileName(@Param("val") String val);

	@Query(value="SELECT * "
			+ "FROM ged g "
			+ "WHERE g.statut in ('0','1')"
			+ "AND g.id  = :val",nativeQuery = true)
	Optional<Ged> findByIdStatut(@Param("val") Long val);

}
