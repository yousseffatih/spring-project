package com.exemple.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.security.entity.Numero;

@Repository
public interface NumeroRepository extends JpaRepository<Numero, Long>{

	@Query("SELECT COUNT(n) "
		 + "FROM Numero n "
	     + "WHERE n.veleur LIKE :prefix%")
    long countByValeurStartingWith(@Param("prefix") String prefix);

	Numero findTopByPfixOrderByPositionDesc(String pfix);

	List<Numero> findByCode(String code);

}
