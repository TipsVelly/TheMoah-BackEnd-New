package com.themoah.themoah.domain.admin.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private String description;
    private String messageCode;
    private String message;
    private String title;

    public MessageDto(String description, String message, String title, String messageCode) {
        this.description = description;
        this.message = message;
        this.title = title;
        this.messageCode = messageCode;
    }


}
