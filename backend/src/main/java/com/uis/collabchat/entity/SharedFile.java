package com.uis.collabchat.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shared_files")
public class SharedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String storedFileName; // Actual saved file (e.g. report_6f7a2.pdf)    private Long sharedFromUserId;
    private String sharedFromUserId;
    private String sharedToUserId;
    private LocalDateTime sharedAt;
    private String filePath;

    public SharedFile() {}

    public SharedFile(String fileName, String storedFileName, String sharedFromUserId, String sharedToUserId, String filePath) {
        this.fileName = fileName;
        this.storedFileName = storedFileName;
        this.sharedFromUserId = sharedFromUserId;
        this.sharedToUserId = sharedToUserId;
        this.filePath = filePath;
        this.sharedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getStoredFileName() { return storedFileName; }
    public void setStoredFileName(String storedFileName) { this.storedFileName = fileName; }
    public String getFilePath() { return fileName; }
    public void setFilePath(String fileName) { this.fileName = fileName; }
    public String getSharedFromUserId() { return sharedFromUserId; }
    public void setSharedFromUserId(String sharedFromUserId) { this.sharedFromUserId = sharedFromUserId; }
    public String getSharedToUserId() { return sharedToUserId; }
    public void setSharedToUserId(String sharedToUserId) { this.sharedToUserId = sharedToUserId; }
    public LocalDateTime getSharedAt() { return sharedAt; }
    public void setSharedAt(LocalDateTime sharedAt) { this.sharedAt = sharedAt; }
}


