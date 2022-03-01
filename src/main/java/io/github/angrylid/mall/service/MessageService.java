package io.github.angrylid.mall.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.dto.response.ChatMessage;
import io.github.angrylid.mall.dto.response.ChatNotification;
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

    /**
     * 插入一条消息
     * 
     * @param senderTel    消息发送者的手机号
     * @param recipientTel 消息接收者的手机号
     * @param content      消息内容
     * @param createdAt    消息发送时间
     */
    public void insertOne(String senderTel, String recipientTel, String content) {

        Integer senderId = userMapper.selectOne(new QueryWrapper<User>().eq("telephone", senderTel)).getId();
        Integer recipientId = userMapper.selectOne(new QueryWrapper<User>().eq("telephone", recipientTel)).getId();
        Message message = new Message();
        message.setSenderId(senderId);
        message.setRecipientId(recipientId);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());

        messageMapper.insert(message);
    }

    public List<ChatMessage> selectMessages(String senderTel, String recipientTel) {
        Integer senderId = userMapper.selectOne(new QueryWrapper<User>().eq("telephone", senderTel)).getId();
        Integer recipientId = userMapper.selectOne(new QueryWrapper<User>().eq("telephone", recipientTel)).getId();

        Map<Integer, String> map = new HashMap<>();
        map.put(senderId, senderTel);
        map.put(recipientId, recipientTel);

        List<Message> received = messageMapper
                .selectList(new QueryWrapper<Message>().eq("sender_id", recipientId).eq("recipient_id", senderId));
        List<Message> sent = messageMapper
                .selectList(new QueryWrapper<Message>().eq("sender_id", senderId).eq("recipient_id", recipientId));

        List<Message> combined = Stream.of(sent, received).flatMap(Collection::stream).collect(Collectors.toList());
        combined.sort(Comparator.comparing(Message::getCreatedAt));

        List<ChatMessage> chatMessages = new ArrayList<>();

        combined.forEach(m -> {
            ChatMessage chatMessage = new ChatMessage(map.get(m.getSenderId()), map.get(m.getRecipientId()),
                    m.getContent(),
                    m.getCreatedAt().toString());
            chatMessages.add(chatMessage);
        });
        return chatMessages;
    }

    /**
     * 检索最后留言
     * 
     * @param userId 用户id
     */
    public List<ChatNotification> selectChats(Integer userId) {
        Set<Integer> ids = new HashSet<>();
        List<ChatNotification> chatNotifications = new ArrayList<>();
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();

        queryWrapper.select("distinct sender_id, recipient_id").eq("sender_id", userId);
        messageMapper.selectList(queryWrapper).forEach(action -> {
            ids.add(action.getRecipientId());

        });

        queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct sender_id, recipient_id").eq("recipient_id", userId);
        messageMapper.selectList(queryWrapper).forEach(action -> {
            ids.add(action.getSenderId());
        });

        for (Integer id : ids) {
            List<Message> lastSentList = messageMapper.selectList(new QueryWrapper<Message>().eq("sender_id", userId)
                    .eq("recipient_id", id).orderByDesc("created_at"));

            List<Message> lastRecievedList = messageMapper.selectList(new QueryWrapper<Message>().eq("sender_id", id)
                    .eq("recipient_id", userId).orderByDesc("created_at"));

            Message lastSent;
            Message lastRecieved;
            Message last;

            if (lastSentList.isEmpty()) {
                lastSent = null;
            } else {
                lastSent = lastSentList.get(0);
            }

            if (lastRecievedList.isEmpty()) {
                lastRecieved = null;
            } else {
                lastRecieved = lastRecievedList.get(0);
            }

            if (lastSent == null) {
                last = lastRecieved;
            } else if (lastRecieved == null) {
                last = lastSent;
            } else {
                last = lastSent.getCreatedAt().isAfter(lastRecieved.getCreatedAt()) ? lastSent : lastRecieved;
            }

            User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", id));
            ChatNotification chatNotification = new ChatNotification(
                    user.getTelephone(), user.getNickname(), last.getCreatedAt().toString(), last.getContent());
            chatNotifications.add(chatNotification);
        }

        return chatNotifications;
    }
}
