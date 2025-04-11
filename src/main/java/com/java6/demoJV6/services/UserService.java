package com.java6.demoJV6.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java6.demoJV6.bean.RegisterBean;
import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.jpa.UserJPA;

@Service
public class UserService {

    @Autowired
    private UserJPA userJPA;

    // Đăng ký tài khoản mới
    public UserEntity registerUser(RegisterBean registerBean) {
        UserEntity user = new UserEntity();
        user.setEmail(registerBean.getEmail());
        user.setPassword(registerBean.getPassword()); // Nên mã hóa mật khẩu trước khi lưu
        user.setName(registerBean.getFullName());
        user.setDateCreated(LocalDateTime.now());
        user.setStatus(true); // Mặc định là hoạt động
        user.setRole(1); // Mặc định role 1 là người dùng thường
        user.setAvatar(null); // Có thể cập nhật sau
        user.setOtp(null);
        user.setOtpExpiry(null);
        return userJPA.save(user);
    }

    // Kiểm tra email đã tồn tại
    public boolean isEmailExists(String email) {
        return userJPA.findByEmail(email).isPresent();
    }

    // Tìm user theo email
    public Optional<UserEntity> findByEmail(String email) {
        return userJPA.findByEmail(email);
    }

    // Lưu user (update hoặc thêm mới)
    public UserEntity save(UserEntity user) {
        return userJPA.save(user);
    }

    // Tìm user theo ID
    public Optional<UserEntity> findById(Integer id) {
        return userJPA.findById(id);
    }

    // Xóa user theo ID
    public void deleteById(Integer id) {
        userJPA.deleteById(id);
    }
}