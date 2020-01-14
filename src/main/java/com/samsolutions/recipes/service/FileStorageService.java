package com.samsolutions.recipes.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
public interface FileStorageService {
    String storeFile(MultipartFile file) throws IOException;

    Resource loadFileAsResource(String fileName) throws Exception;

}
