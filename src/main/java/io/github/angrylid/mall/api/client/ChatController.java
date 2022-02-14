package io.github.angrylid.mall.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import io.github.angrylid.mall.dto.ChatMessage;

@Controller
public class ChatController {

    private SimpMessagingTemplate template;

    public ChatController(@Autowired SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage message) {
        template.convertAndSendToUser(message.getRecipientId(), "/queue/messages", message);
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}
