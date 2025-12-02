package com.uis.collabchat.repository;

import com.uis.collabchat.entity.SharedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SharedFileRepository extends JpaRepository<SharedFile, Long> {
    List<SharedFile> findBySharedToUserId(String sharedToUserId);
}

