package com.java6.demoJV6.bean;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryBean {

    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;

    @NotNull(message = "Trạng thái không được để trống")
    private Boolean status = true;

}
