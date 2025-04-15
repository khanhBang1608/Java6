package com.java6.demoJV6.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java6.demoJV6.bean.RegisterBean;
import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.jpa.UserJPA;
import com.java6.demoJV6.utils.PasswordUtil;

@Service
public class UserService {

    @Autowired
    private UserJPA userJPA;

    // Đăng ký tài khoản mới

public UserEntity registerUser(RegisterBean registerBean) {
    UserEntity user = new UserEntity();
    user.setEmail(registerBean.getEmail());

    // Mã hóa mật khẩu bằng SHA-256
    String hashedPassword = PasswordUtil.hashPassword(registerBean.getPassword());
    user.setPassword(hashedPassword);

    user.setName(registerBean.getFullName());
    user.setDateCreated(LocalDateTime.now());
    user.setStatus(true);
    user.setRole(1);
    user.setAvatar(null);
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
    
    public boolean isEmailExistsForOtherUsers(String email, Integer currentUserId) {
        Optional<UserEntity> userOpt = userJPA.findByEmail(email);
        return userOpt.isPresent() && !userOpt.get().getId().equals(currentUserId);
    }


    // Lưu user (update hoặc thêm mới)
    public UserEntity save(UserEntity user) {
        return userJPA.save(user);
    }

    // Tìm user theo ID
    public Optional<UserEntity> findById(Integer id) {
        return userJPA.findById(id);
    }
    
    public UserEntity findUserByEmail(String email) {
        return userJPA.findByEmail(email).orElse(null);
    }


    // Xóa user theo ID
    public void deleteById(Integer id) {
        userJPA.deleteById(id);
    }
}