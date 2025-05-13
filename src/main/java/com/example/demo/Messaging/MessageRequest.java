package com.example.demo.Messaging;

import lombok.Data;

@Data
public class MessageRequest {
    private Long recipientId;
    private String content;
}