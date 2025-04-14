package com.java6.demoJV6.bean;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileBean {
    @NotBlank(message = "tên không được để trống")
    private String fullName;

   @NotBlank(message = "email không được để trống")
   @Email(message = "Email không hợp lệ")
    private String email; 
   
   private String avatar;

}
