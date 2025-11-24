package com.uis.collabchat;

import com.uis.collabchat.dtos.SamplePostDTO;
import com.uis.collabchat.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.uis.collabchat.service.MessageService;
import java.time.Instant;

@RestController
public class HelloController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hi.. welcome to Spring Boot REST345!";
    }

    @PostMapping(path = "/simplePost", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public SamplePostDTO simplePost(@RequestBody SamplePostDTO payload) {
        String msg = payload.getMessage();
        messageService.saveMessage(msg);
        if (msg != null) {
            payload.setMessage(msg + ": processed by server at " +
                    Instant.now().toString());
        }
        return payload;
    }

    @GetMapping("/message/{id}")
    public Message getMessage(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }
}