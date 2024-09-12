package com.exemple.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemple.security.entity.Employes;

public interface EmployesRepository extends JpaRepository<Employes, Long>{
	
	Optional<Employes> findById(Long id);
}
