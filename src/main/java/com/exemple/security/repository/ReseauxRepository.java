package com.exemple.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemple.security.entity.Reseaux;

public interface ReseauxRepository extends JpaRepository<Reseaux, Long>{
	
	Optional<Reseaux> findById(Long id);
}
