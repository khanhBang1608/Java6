package com.java6.demoJV6.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java6.demoJV6.entity.ProductSizeEntity;

public interface ProductSizeJPA extends JpaRepository<ProductSizeEntity, Integer> {
    List<ProductSizeEntity> findByProductId(int productId);
    public ProductSizeEntity findFirstByProductId(int productId);
	ProductSizeEntity findByProductIdAndSizeId(int productId, int sizeId);
	
	
	@Query(value="DELETE FROM product_sizes WHERE product_size_id = ?1", nativeQuery = true)
	public boolean deleteByProductSizeID(int id);

}
