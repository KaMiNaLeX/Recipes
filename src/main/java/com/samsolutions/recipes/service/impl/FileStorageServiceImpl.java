package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.config.property.FileStorageProperty;
import com.samsolutions.recipes.service.FileStorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Log4j2
@Service
public class FileStorageServiceImpl implements FileStorageService {
    private Path fileStorageLocation;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperty fileStorageProperty) {
        this.fileStorageLocation = Paths.get(fileStorageProperty.getUploadDir())
                .toAbsolutePath().normalize();
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(System.currentTimeMillis() + file.getOriginalFilename());

        Path targetLocation = Paths.get(this.fileStorageLocation + "/" + fileName).toAbsolutePath().normalize();
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return "http://localhost:4200/getFile/" + fileName;
    }

    @Override
    public Resource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = Paths.get(this.fileStorageLocation + "/" + fileName).toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                log.error(new Exception("File not found " + fileName));
                return null;
            }
        } catch (Exception ex) {
            log.error(new Exception("File not found " + fileName, ex));
            return null;
        }
    }
}
