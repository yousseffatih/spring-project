package com.exemple.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemple.security.entity.Activites;

public interface ActivitesRepository extends JpaRepository<Activites, Long>{
	
	Optional<Activites> findById(Long id);
}
