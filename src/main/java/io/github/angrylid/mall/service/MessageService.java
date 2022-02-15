package io.github.angrylid.mall.service;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.generated.entity.Message;
import io.github.angrylid.mall.generated.entity.User;
import io.github.angrylid.mall.generated.mapper.MessageMapper;
import io.github.angrylid.mall.generated.mapper.UserMapper;

@Service
public class MessageService {

    private MessageMapper messageMapper;
    private UserMapper userMapper;

    public MessageService(@Autowired MessageMapper messageMapper, @Autowired UserMapper userMapper) {
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
    }

    public void insertOne(String senderTel, String recipientTel, String content, LocalDateTime createdAt) {

        Integer senderId = userMapper.selectOne(new QueryWrapper<User>().eq("telephone", senderTel)).getId();
        Integer recipientId = userMapper.selectOne(new QueryWrapper<User>().eq("telephone", recipientTel)).getId();
        Message message = new Message();
        message.setSenderId(senderId);
        message.setRecipientId(recipientId);
        message.setContent(content);
        message.setCreatedAt(createdAt);

        messageMapper.insert(message);
    }
}
