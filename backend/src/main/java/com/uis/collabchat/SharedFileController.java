package com.uis.collabchat;

import com.uis.collabchat.entity.SharedFile;
import com.uis.collabchat.repository.SharedFileRepository;
import com.uis.collabchat.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:3000")
public class SharedFileController {

    private final SharedFileRepository repository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public SharedFileController(SharedFileRepository repository) {
        this.repository = repository;
    }

    // ✅ POST /api/files/share
    @PostMapping("/share")
    public SharedFile shareFile(@RequestParam("file") MultipartFile file,
                                @RequestParam("sharedToUserId") String toUserId,
                                @RequestHeader(value = "Authorization", required = false) String auth) throws IOException {
        //@RequestParam("sharedFromUserId") String fromUserId,
        //

        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid token");
        }
        String token = auth.substring(7);// Remove "Bearer " prefix???
        try {
            Jws<Claims> claims = JwtUtil.validateToken(token);
            String user = claims.getBody().getSubject();
            // Ensure directory exists
            Files.createDirectories(Paths.get(uploadDir));

            // Original filename
            String originalName = file.getOriginalFilename();

            // Generate unique stored filename using UUID
            String extension = "";
            int dotIndex = originalName.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = originalName.substring(dotIndex);
            }
            String storedFileName = UUID.randomUUID() + extension;
            String filePath = uploadDir + "/" + storedFileName;

            // Save file
            file.transferTo(new File(filePath));

            // Save metadata
            SharedFile sharedFile = new SharedFile();
            sharedFile.setFileName(originalName);
            sharedFile.setStoredFileName(storedFileName);
            sharedFile.setFilePath(filePath);
            sharedFile.setSharedFromUserId(user);
            sharedFile.setSharedToUserId(toUserId);
            sharedFile.setSharedAt(LocalDateTime.now());

            return repository.save(sharedFile);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }


    }

    // ✅ GET /api/files/shared-to/{userId}
    @GetMapping("/shared-to/")
    public List<SharedFile> getFilesSharedToUser(@RequestHeader(value = "Authorization", required = false) String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            return  new ArrayList<>();
        }
        String token = auth.substring(7);// Remove "Bearer " prefix???
        try {
            Jws<Claims> claims = JwtUtil.validateToken(token);
            String user = claims.getBody().getSubject();
            return repository.findBySharedToUserId(user);
        } catch (Exception e) {
            return  new ArrayList<>();
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws IOException {
        SharedFile sharedFile = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id " + id));

        File file = new File(sharedFile.getFilePath());
        if (!file.exists()) {
            throw new RuntimeException("File not found on disk: " + sharedFile.getFilePath());
        }

        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + sharedFile.getFileName() + "\"")
                .body(resource);
    }
}


