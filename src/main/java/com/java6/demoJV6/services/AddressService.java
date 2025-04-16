package com.java6.demoJV6.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java6.demoJV6.dto.AddressDTO;
import com.java6.demoJV6.entity.AddressEntity;
import com.java6.demoJV6.jpa.AddressJPA;

@Service
public class AddressService {
    
    @Autowired
    private AddressJPA addressjpa;

    public AddressDTO getAddressById(Integer id) {
        AddressEntity address = addressjpa.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
        
        // Kiểm tra nếu user không phải là null trước khi lấy id
        String userId = address.getUser() != null ? String.valueOf(address.getUser().getId()) : null;
        
        return new AddressDTO(
            address.getId(),
            address.getCustomerName(),
            address.getPhone(),
            address.getAddress(),
            userId // Trả về null nếu user không có
        );
    }
}
