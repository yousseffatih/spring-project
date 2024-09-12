package com.exemple.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemple.security.entity.Villes;

public interface VillesRepository extends JpaRepository<Villes, Long>{
	
	Optional<Villes> findById(Long id);
	
}
