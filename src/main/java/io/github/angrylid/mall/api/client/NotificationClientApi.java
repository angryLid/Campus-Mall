package io.github.angrylid.mall.api.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import io.github.angrylid.mall.api.annotation.ClientController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.response.ChatNotification;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.service.MessageService;

@ClientController("/notification")
public class NotificationClientApi {

    private MessageService messageService;

    public NotificationClientApi(@Autowired MessageService messageService) {
        this.messageService = messageService;
    }

    @TokenRequired
    @GetMapping("/")
    public CustomResponse<?> getNotification(@RequestAttribute("id") Integer id) {
        List<ChatNotification> notifications = messageService.selectChats(id);

        return CustomResponse.success(notifications);
    }
}
