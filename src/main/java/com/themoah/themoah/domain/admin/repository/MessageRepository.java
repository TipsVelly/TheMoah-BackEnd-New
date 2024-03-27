package com.themoah.themoah.domain.admin.repository;


import com.themoah.themoah.domain.admin.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
