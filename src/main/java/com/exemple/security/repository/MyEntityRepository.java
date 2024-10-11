package com.exemple.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemple.security.entity.MyEntity;

public interface MyEntityRepository extends JpaRepository<MyEntity, Long>{

}
