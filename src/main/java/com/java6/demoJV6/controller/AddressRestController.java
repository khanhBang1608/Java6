package com.java6.demoJV6.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.java6.demoJV6.dto.AddressDTO;
import com.java6.demoJV6.entity.AddressEntity;
import com.java6.demoJV6.jpa.AddressJPA;
import com.java6.demoJV6.jpa.UserJPA;
import com.java6.demoJV6.services.AddressService;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin(origins = "http://localhost:5173") // Vue port
public class AddressRestController {

    @Autowired
    private AddressJPA addressjpa;

    @Autowired
    private UserJPA userjpa;
    
    @Autowired
    private AddressService addressService;

    // Lấy danh sách địa chỉ theo userId
    @GetMapping("/user/{userId}")
    public List<AddressDTO> getAddressesByUserId(@PathVariable Integer userId) {
        List<AddressEntity> entities = addressjpa.findByUserId(userId);
        return entities.stream().map(address -> {
            AddressDTO dto = new AddressDTO();
            dto.setId(address.getId());
            dto.setCustomerName(address.getCustomerName());
            dto.setPhone(address.getPhone());
            dto.setAddress(address.getAddress());
            dto.setUserId(address.getUser().getId().toString());
            return dto;
        }).toList();
    }


    // Thêm địa chỉ
    @PostMapping("/create")
    public AddressEntity create(@RequestBody AddressEntity address) {
        return addressjpa.save(address); 
    }

   
    // Cập nhật địa chỉ
    @PutMapping("/{id}")
    public AddressEntity updateAddress(@PathVariable Integer id, @RequestBody AddressEntity address) {
        address.setId(id);
        return addressjpa.save(address);
    }
    // Lấy địa chỉ theo ID
    @GetMapping("/{id}")
    public AddressDTO getAddressById(@PathVariable Integer id) {
        return addressService.getAddressById(id);
    }


    // Xoá địa chỉ
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        addressjpa.deleteById(id); 
    }
}
