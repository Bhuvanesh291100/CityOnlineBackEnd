package com.city.online.api.service;

import com.city.online.api.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@Service
public class FileStorageService {

   // private final Path foodFileStorageLocation;

    @Value("${file.upload.food.images:/food/uploads}")
    String foodImageUploadPath;

   /* @Autowired
    public FileStorageService() {
        this.foodFileStorageLocation = Paths.get(foodImageUploadPath)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.foodFileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }*/

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path foodFileStorageLocation = Paths.get(foodImageUploadPath)
                    .toAbsolutePath().normalize();
            Files.createDirectories(foodFileStorageLocation);
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new BusinessException("Sorry! Filename contains invalid path sequence", "Sorry! Filename contains invalid path sequence", HttpStatus.INTERNAL_SERVER_ERROR, null);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = foodFileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!");
        }
    }

    public Resource loadFileAsResource(String fileName) throws IOException{
        try {
            Path foodFileStorageLocation = Paths.get(foodImageUploadPath)
                    .toAbsolutePath().normalize();
            Files.createDirectories(foodFileStorageLocation);
            Path filePath = foodFileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }
}