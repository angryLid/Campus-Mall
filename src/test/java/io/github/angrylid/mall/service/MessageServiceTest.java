package io.github.angrylid.mall.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MessageServiceTest {

    private MessageService messageService;

    public MessageServiceTest(@Autowired MessageService messageService) {
        this.messageService = messageService;
    }

    @Test
    void testSelectChats() {
        messageService.selectChats(1);
    }
}
