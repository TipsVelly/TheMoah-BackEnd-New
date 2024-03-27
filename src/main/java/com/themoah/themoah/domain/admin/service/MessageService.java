package com.themoah.themoah.domain.admin.service;

import com.themoah.themoah.domain.admin.dto.MessageDto;
import com.themoah.themoah.domain.admin.entity.Message;
import com.themoah.themoah.domain.admin.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public List<MessageDto> findAll() {
        List<Message> messageList = messageRepository.findAll();
        List<MessageDto> messageDtoList = new ArrayList<>();
        for(Message message : messageList){
            MessageDto dto = MessageDto.builder()
                    .id(message.getId())
                    .title(message.getTitle())
                    .message(message.getMessage())
                    .description(message.getDescription())
                    .build();
            messageDtoList.add(dto);
        }
        return messageDtoList;
    }

    public void save(MessageDto dto) {
        Message message = Message.builder()
                .title(dto.getTitle())
                .message(dto.getMessage())
                .description(dto.getDescription())
                .build();
        messageRepository.save(message);
    }

    public void updateMessage(Long id, String message) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            Message findMessage = optionalMessage.get();
            findMessage.setMessage(message);
        }
    }

}
