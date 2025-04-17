package com.java6.demoJV6.bean;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductBean {
	@NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    @Min(value=10000 ,message = "Giá phải lớn hơn 10.000")
    private long price;

    @NotNull(message = "Danh mục không được để trống")
    private Integer categoryId;

    private Boolean status = true;

    private List<MultipartFile> images;
    
    public String validateImageFiles() {
    	if (images == null || images.isEmpty()) {
            return "Bạn phải thêm ít nhất 3 ảnh";
        }
        long totalSize = 0;
        long size = (2 * 1024 * 1024);
        for (MultipartFile file : images) {
            totalSize += file.getSize();
        }
        if (images.size() < 3) {
            return "Bạn phải thêm ít nhất 3 ảnh";
        }

        
        if (totalSize > size) {
            return "Tổng dung lượng ảnh không được vượt quá 2MB";
        }     

        return null;
    }
    public String validateImageFiles2() {
    	if (images == null || images.isEmpty()) {
            return null;
        }
        long totalSize = 0;
        long size = (2 * 1024 * 1024);
        for (MultipartFile file : images) {
            totalSize += file.getSize();
        }

        
        if (totalSize > size) {
            return "Tổng dung lượng ảnh không được vượt quá 2MB";
        } 

        return null;
    }
}
