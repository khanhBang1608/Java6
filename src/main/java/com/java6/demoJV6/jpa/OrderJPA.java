package com.java6.demoJV6.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java6.demoJV6.entity.OrderEntity;

public interface OrderJPA extends JpaRepository<OrderEntity,Integer>{
	Optional<OrderEntity> findByUserId(Integer userId);
	List<OrderEntity> findByUserIdOrderByOrderDateDesc(Integer userId);
}