package com.java6.demoJV6.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java6.demoJV6.entity.OrderDetailEntity;

public interface OderDetailJPA extends JpaRepository<OrderDetailEntity,Integer>{
}
