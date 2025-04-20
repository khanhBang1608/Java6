package com.java6.demoJV6.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java6.demoJV6.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDetailJPA extends JpaRepository<OrderDetailEntity,Integer>{
}
