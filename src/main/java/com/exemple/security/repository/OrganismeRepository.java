package com.exemple.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemple.security.entity.Organisme;

public interface OrganismeRepository extends JpaRepository<Organisme, Long>{
	
	Optional<Organisme> findById(Long id);
}
