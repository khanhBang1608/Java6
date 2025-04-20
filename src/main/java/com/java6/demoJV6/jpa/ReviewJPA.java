package com.java6.demoJV6.jpa;

import com.java6.demoJV6.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewJPA extends JpaRepository<ReviewEntity, Long> {
}
