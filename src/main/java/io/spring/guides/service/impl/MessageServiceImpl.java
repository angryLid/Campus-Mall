package io.spring.guides.service.impl;

import io.spring.guides.mapper.CustomMessageMapper;
import io.spring.guides.mbg.dao.MessageMapper;
import io.spring.guides.mbg.entity.Message;
import io.spring.guides.mbg.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("messageService")
public class MessageServiceImpl {

    @Resource
    private MessageMapper mapper;

    @Resource
    private CustomMessageMapper customMessageMapper;

    public boolean addMessage(String content, Long applicant){
        Message message = new Message();
        message.setContent(content);
        message.setApplyDate(new Date());
        message.setApproved((byte)0);
        message.setUserId(applicant);

        int result = this.mapper.insert(message);
        return result == 1;
    }

    public boolean checkMessage(int id,Boolean status){
        Message message = this.mapper.selectByPrimaryKey(id);
        message.setApproved(status?(byte)1:(byte)2);

        int result = this.mapper.updateByPrimaryKey(message);
        return result == 1;
    }

    public List<Message> retrieveAll(){
        List<Message> origin = this.mapper.selectAll();
        return origin.stream().filter(message -> message.getApproved() == 0).collect(Collectors.toList());
    }

    public List<Message> retrieveCurrentUser(Long id){
        return this.customMessageMapper.getMessagesByUser(id);
    }
}
