package com.java6.demoJV6.entity;

import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "user_id", nullable = false)

    private Integer id;

    
    @Column(name = "full_name", nullable = false, columnDefinition = "NVARCHAR(100)")
    private String name;

    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    
    @Column(name = "avatar", length = 255)
    private String avatar;

    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "role", nullable = false)
    private Integer role;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AddressEntity> addresses;
    
    @Column(name = "otp", length = 255)
    private String otp;

    @Column(name = "otp_expiry")
    private LocalDateTime otpExpiry;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CartEntity cart;
    

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FavoriteEntity> favorites;
    
    
    
    public enum Role {
        ADMIN(0),
        USER(1);
    
        private final int value;
    
        Role(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    
        public static Role fromValue(int value) {
            for (Role role : Role.values()) {
                if (role.getValue() == value) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Invalid role value: " + value);
        }
    }
    public Role getRoleEnum() {
        return Role.fromValue(this.role);
    }
}

