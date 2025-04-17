package com.java6.demoJV6.controller;

import com.java6.demoJV6.dto.UserDTO;
import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userService.findAllUsers();

        return users.stream()
                   .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getAvatar(),
                        user.getStatus(),
                        user.getRole()))
                   .collect(Collectors.toList());
    }
    
    @PutMapping("/users/{id}/status")
    public UserDTO updateUserStatus(@PathVariable Integer id, @RequestBody Boolean status) {
        UserEntity updatedUser = userService.updateUserStatus(id, status);
        if (updatedUser != null) {
            return new UserDTO(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getAvatar(),
                updatedUser.getStatus(),
                updatedUser.getRole()
            );
        }
        return null; 
    }
}
