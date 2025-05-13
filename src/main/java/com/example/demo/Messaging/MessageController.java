package com.example.demo.Messaging;

import com.example.demo.security.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    private final MessageService messageService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody MessageRequest request
    ) {
        if (request.getRecipientId() == null || request.getContent() == null || request.getContent().isBlank()) {
            throw new IllegalArgumentException("Recipient ID and message content must not be null or empty");
        }

        String token = authHeader.replace("Bearer ", "");
        Claims claims = jwtUtils.parseToken(token);

        Message message = messageService.sendMessage(request, claims);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Message>> findAll(@RequestHeader("Authorization") String token) {
        return messageService.getAll(token);
    }


}
