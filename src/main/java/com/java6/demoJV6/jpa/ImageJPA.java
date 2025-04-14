package com.java6.demoJV6.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java6.demoJV6.entity.ImageEntity;

public interface ImageJPA extends JpaRepository<ImageEntity, Integer>  {

}
