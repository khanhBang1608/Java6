package com.java6.demoJV6.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java6.demoJV6.entity.UserEntity;

public interface UserJPA extends JpaRepository<UserEntity,Integer>{
	Optional<UserEntity> findByEmail(String email);

}