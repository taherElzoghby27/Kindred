package com.spring.boot.social.utils;

import com.spring.boot.social.exceptions.NotFoundResourceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadFileLocal {
    private UploadFileLocal() {
    }

    public static String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new NotFoundResourceException("not.found.media");
        }
        try {
            final String UPLOAD_DIR = "uploads/";
            // 1.make sure upload directory exist
            File uploadsDir = new File(UPLOAD_DIR);
            if (!uploadsDir.exists()) {
                uploadsDir.mkdir();
            }
            // 2. Build the path: uploads/filename.ext
            Path filePath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
            // 3. Save file to the path
            Files.write(filePath, file.getBytes());
            return file.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }
}
