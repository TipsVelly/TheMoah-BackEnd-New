package com.themoah.themoah.domain.admin.service;

import com.themoah.themoah.domain.admin.dto.MessageDto;
import com.themoah.themoah.domain.admin.entity.Message;
import com.themoah.themoah.domain.admin.repository.MessageQueryRepository;
import com.themoah.themoah.domain.admin.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageQueryRepository queryRepository;

    public void save(MessageDto dto) {
        Optional<Message> findMessage = messageRepository.findByMessageCode(dto.getMessageCode());
        if (findMessage.isPresent()) {
            throw new RuntimeException("이미 존재하는 메시지 코드입니다.");
        }else{
            Message message = Message.builder()
                    .title(dto.getTitle())
                    .message(dto.getMessage())
                    .messageCode(dto.getMessageCode())
                    .description(dto.getDescription())
                    .build();
            messageRepository.save(message);
        }
    }
    @Transactional
    public void updateMessage(MessageDto dto) {
        Optional<Message> optionalMessage = messageRepository.findById(dto.getId());
        if (optionalMessage.isPresent()) {
            Message findMessage = optionalMessage.get();
            findMessage.setMessage(dto.getMessage());
            messageRepository.save(findMessage);
        }
    }
    public List<MessageDto> findAll(MessageDto dto) {
        List<Message> messageList = queryRepository.findAll(dto);
        List<MessageDto> messageDtoList = new ArrayList<>();
        for(Message message : messageList){
            MessageDto messageDto = MessageDto.builder()
                    .id(message.getId())
                    .title(message.getTitle())
                    .message(message.getMessage())
                    .messageCode(message.getMessageCode())
                    .description(message.getDescription())
                    .build();
            messageDtoList.add(messageDto);
        }
        return messageDtoList;
    }

}
