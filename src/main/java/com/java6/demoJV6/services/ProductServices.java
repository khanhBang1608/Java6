package com.java6.demoJV6.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.java6.demoJV6.dto.ProductDTO;
import com.java6.demoJV6.dto.ProductSizeDTO;
import com.java6.demoJV6.dto.SizeDTO;
import com.java6.demoJV6.entity.SizeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java6.demoJV6.bean.ProductBean;
import com.java6.demoJV6.entity.CategoryEntity;
import com.java6.demoJV6.entity.ImageEntity;
import com.java6.demoJV6.entity.ProductEntity;
import com.java6.demoJV6.jpa.CategoryJPA;
import com.java6.demoJV6.jpa.ImageJPA;
import com.java6.demoJV6.jpa.ProductJPA;

@Service
public class ProductServices {

    @Autowired
    private ProductJPA productJPA;

    @Autowired
    private CategoryJPA categoryJPA;

    @Autowired
    private ImageJPA imageJPA;

    @Autowired
    private ImageServices imageServices;

//    public List<ProductDTO> getAllProductDTOs() {
//        List<ProductEntity> products = productJPA.findAll();
//
//        return products.stream().map(product -> {
//            List<String> imageNames = product.getImages().stream()
//                    .map(ImageEntity::getFileName)
//                    .collect(Collectors.toList());
//
//            List<ProductSizeDTO> sizeDTOs = product.getProductSizes().stream().map(productSize -> {
//                SizeEntity size = productSize.getSize();
//                SizeDTO sizeDTO = new SizeDTO(size.getId(), size.getName());
//                return new ProductSizeDTO(productSize.getId(), sizeDTO, productSize.getStock());
//            }).collect(Collectors.toList());
//
//            return new ProductDTO(
//                    product.getId(),
//                    product.getName(),
//                    product.getPrice(),
//                    imageNames,
//                    sizeDTOs
//            );
//        }).collect(Collectors.toList());
//    }

    public void createProduct(ProductBean bean) {
        ProductEntity entity = new ProductEntity();
        entity.setName(bean.getName());
        entity.setDescription(bean.getDescription());
        entity.setPrice(bean.getPrice());
        entity.setStatus(bean.getStatus());

        Optional<CategoryEntity> category = categoryJPA.findById(bean.getCategoryId());
        category.ifPresent(entity::setCategory);

        productJPA.save(entity);

        List<String> fileNames = imageServices.saveImages(bean.getImages());
        List<ImageEntity> images = new ArrayList<>();
        for (String filename : fileNames) {
            ImageEntity img = new ImageEntity();
            img.setProduct(entity);
            img.setName(filename);
            images.add(img);
        }

        imageJPA.saveAll(images);
    }

    public void updateProduct(int id, ProductBean bean) {
        Optional<ProductEntity> optional = productJPA.findById(id);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+bean);
        if (optional.isPresent()) {
            ProductEntity entity = optional.get();
            entity.setName(bean.getName());
            entity.setDescription(bean.getDescription());
            entity.setPrice(bean.getPrice());
            entity.setStatus(bean.getStatus());

            Optional<CategoryEntity> category = categoryJPA.findById(bean.getCategoryId());
            category.ifPresent(entity::setCategory);

            productJPA.save(entity);

            
            
            List<String> fileNames = imageServices.saveImages(bean.getImages());

            try {				
				if(fileNames.size() >= 1) {
					System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
					imageJPA.deleteByProductID(id); 
				}
		    	
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
            List<ImageEntity> images = new ArrayList<>();
            for (String filename : fileNames) {
                ImageEntity img = new ImageEntity();
                img.setProduct(entity);
                img.setName(filename);
                images.add(img);
            }

            imageJPA.saveAll(images);
            
        }
    }

}
