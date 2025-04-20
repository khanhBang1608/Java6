package com.java6.demoJV6.controller;

import com.java6.demoJV6.bean.UserBean;
import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.services.UserService;
import com.java6.demoJV6.utils.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class ChangePassController {

    @Autowired
    private UserService userService;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserBean userBean) {

        // 0. Kiểm tra confirmPassword
        if (!userBean.getNewPassword().equals(userBean.getConfirmPassword())) {
            return ResponseEntity
                .badRequest()
                .body("Xác nhận mật khẩu mới không khớp.");
        }

        // 1. Tìm user
        Optional<UserEntity> optionalUser = userService.findById(userBean.getId());
        if (optionalUser.isEmpty()) {
            return ResponseEntity
                .badRequest()
                .body("Người dùng không tồn tại.");
        }
        UserEntity user = optionalUser.get();

        // 2. Kiểm tra mật khẩu hiện tại
        String hashedCurrent = PasswordUtil.hashPassword(userBean.getCurrentPassword());
        if (!hashedCurrent.equals(user.getPassword())) {
            return ResponseEntity
                .badRequest()
                .body("Mật khẩu hiện tại không đúng.");
        }

        // 3. Cập nhật mật khẩu mới
        String hashedNew = PasswordUtil.hashPassword(userBean.getNewPassword());
        user.setPassword(hashedNew);
        userService.save(user);

        return ResponseEntity.ok("Đổi mật khẩu thành công.");
    }


}