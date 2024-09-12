package com.exemple.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemple.security.entity.Agences;

public interface AgencesRepository extends JpaRepository<Agences, Long>{
	
	Optional<Agences> findById(Long id);
}
