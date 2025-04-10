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

    @Size(min = 1, message = "Vui lòng chọn ít nhất một ảnh")
    private List<MultipartFile> images;
}
