package io.github.angrylid.mall.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import io.github.angrylid.mall.dto.ChatMessage;
import io.github.angrylid.mall.service.MessageService;

@Controller
public class ChatController {

    private SimpMessagingTemplate template;
    private MessageService messageService;

    public ChatController(@Autowired SimpMessagingTemplate template, @Autowired MessageService messageService) {
        this.template = template;
        this.messageService = messageService;
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage message) {
        messageService.insertOne(
                message.getSenderId(),
                message.getRecipientId(),
                message.getContent(),
                message.getCreatedAt());
        template.convertAndSendToUser(message.getRecipientId(), "/queue/messages", message);
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}
