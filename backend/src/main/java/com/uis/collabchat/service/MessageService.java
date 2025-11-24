package com.uis.collabchat.service;
import com.uis.collabchat.entity.Message;
import com.uis.collabchat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    public Message saveMessage(String content) {
        System.out.println("In MessageService.saveMessage(): Saving message: " + content);
        Message message = new Message(content, System.currentTimeMillis());
        return messageRepository.save(message);
    }

    public Message getMessageById(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        return message.orElse(null);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> searchMessages(String keyword) {
        return messageRepository.findByContentContaining(keyword);
    }
}
