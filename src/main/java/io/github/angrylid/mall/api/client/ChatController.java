package io.github.angrylid.mall.api.client;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import io.github.angrylid.mall.dto.ChatMessage;
import io.github.angrylid.mall.service.MessageService;

@Controller
public class ChatController {

    private SimpMessagingTemplate template;
    private MessageService messageService;

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    public ChatController(@Autowired SimpMessagingTemplate template, @Autowired MessageService messageService) {
        this.template = template;
        this.messageService = messageService;
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage message) {
        messageService.insertOne(
                message.getSenderTel(),
                message.getRecipientTel(),
                message.getContent());
        template.convertAndSendToUser(message.getRecipientTel(), "/queue/messages", message);
    }

    @MessageMapping("/history")
    public void sendMessage(@Payload ChatMessage message) {
        List<ChatMessage> messages = messageService.selectMessages(message.getSenderTel(), message.getRecipientTel());
        logger.warn("History Payload:{}", message);
        template.convertAndSendToUser(message.getSenderTel(), "/queue/messages", messages);
    }

}
