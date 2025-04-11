package com.java6.demoJV6.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.java6.demoJV6.jpa.ImageJPA;


@Service
public class ImageServices {
	@Autowired
	ImageJPA imageJPA;
	
	public List<String> saveImages(List<MultipartFile> files) {
        List<String> fileNames = new ArrayList<>();
        try {
            Path filePath = Paths.get("images");
            Files.createDirectories(filePath);

            for (MultipartFile file : files) { 
            	if (file.isEmpty()) {
                    continue; 
                }
                String fileName = String.format("%s.%s", (new Date()).getTime(), file.getContentType().split("/")[1]);

                Files.copy(file.getInputStream(), filePath.resolve(fileName));

                fileNames.add(fileName);
                
                Thread.sleep(5);
            }
        } catch (Exception e) {
            
        }

        return fileNames;
    }
}
