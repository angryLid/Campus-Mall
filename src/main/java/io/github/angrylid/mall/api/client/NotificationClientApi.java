package io.github.angrylid.mall.api.client;

import org.springframework.web.bind.annotation.GetMapping;

import io.github.angrylid.mall.dto.CustomResponse;

@ClientVersion("/notification")
public class NotificationClientApi {

    @GetMapping("/")
    public CustomResponse<?> getNotification() {
        return CustomResponse.success("Here is Notification");
    }
}
