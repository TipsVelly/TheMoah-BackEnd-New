package com.themoah.themoah.domain.admin.repository;


import com.themoah.themoah.domain.admin.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findByMessageCode(String messageCode);
}
