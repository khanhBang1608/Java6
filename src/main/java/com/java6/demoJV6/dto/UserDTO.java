package com.java6.demoJV6.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String avatar;
    private boolean status;
    private Integer role;
}
