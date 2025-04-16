package com.java6.demoJV6.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java6.demoJV6.entity.AddressEntity;

public interface AddressJPA extends JpaRepository<AddressEntity, Integer> {
    List<AddressEntity> findByUserId(Integer userId);
}
