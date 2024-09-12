package com.exemple.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemple.security.entity.Fonctions;


public interface FonctionsRepository extends JpaRepository<Fonctions, Long>{
	
	Optional<Fonctions> findById(Long id);
}
