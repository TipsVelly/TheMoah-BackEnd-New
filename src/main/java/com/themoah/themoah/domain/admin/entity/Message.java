package com.themoah.themoah.domain.admin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "message_id")
    private Long id;
    private String messageCode;
    private String title;
    private String message;
    private String description;
}
