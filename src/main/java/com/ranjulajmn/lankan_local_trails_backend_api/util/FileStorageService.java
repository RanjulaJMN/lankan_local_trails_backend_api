package com.ranjulajmn.lankan_local_trails_backend_api.util;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final String uploadDir = "uploads/";

    public String saveFile(MultipartFile file) {

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            return fileName;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("File upload failed");
        }
    }

    public void deleteFile(String imageUrl) {
        try {
            if (imageUrl == null) return;

            // imageUrl = /images/123_file.jpg
            String fileName = imageUrl.replace("/images/", "");

            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            Path path = Paths.get(uploadDir + fileName);

            Files.deleteIfExists(path);

            System.out.println("Deleted file: " + path.toAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete file");
        }
    }
}
