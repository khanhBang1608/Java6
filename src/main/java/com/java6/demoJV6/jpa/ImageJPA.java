package com.java6.demoJV6.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java6.demoJV6.entity.ImageEntity;

public interface ImageJPA extends JpaRepository<ImageEntity, Integer>  {
	@Query(value="DELETE FROM image WHERE product_id = ?1", nativeQuery = true)
	public boolean deleteByProductID(int id);
	@Query(value="SELECT * FROM image WHERE product_id = ?1", nativeQuery = true)
	public List<ImageEntity> findAllImageByProductId(int id);
}
