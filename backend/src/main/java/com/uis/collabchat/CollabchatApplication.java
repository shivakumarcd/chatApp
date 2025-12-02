package com.uis.collabchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CollabchatApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollabchatApplication.class, args);
        log.info("Welcome to my spring boot app. Version 13-Nov-1900");
    }
}
