package com.java6.demoJV6;

import com.java6.demoJV6.entity.SizeEntity;
import com.java6.demoJV6.jpa.SizeJPA;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoJv6Application {

    public static void main(String[] args) {
        SpringApplication.run(DemoJv6Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(SizeJPA sizeJPA) {
        return (args) -> {
            // Kiểm tra xem bảng Sizes đã có dữ liệu chưa
            if (sizeJPA.count() == 0) {
                // Nếu chưa có, thêm dữ liệu mặc định
            	sizeJPA.save(new SizeEntity(0, "36"));
            	sizeJPA.save(new SizeEntity(0, "37"));
                sizeJPA.save(new SizeEntity(0, "38"));
                sizeJPA.save(new SizeEntity(0, "39"));
                sizeJPA.save(new SizeEntity(0, "40"));
                sizeJPA.save(new SizeEntity(0, "41"));
                sizeJPA.save(new SizeEntity(0, "42"));
                sizeJPA.save(new SizeEntity(0, "43"));
                sizeJPA.save(new SizeEntity(0, "44"));


            }
        };
    }
}
