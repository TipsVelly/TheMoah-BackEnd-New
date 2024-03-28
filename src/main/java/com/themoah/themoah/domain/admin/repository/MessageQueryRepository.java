package com.themoah.themoah.domain.admin.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.themoah.themoah.domain.admin.dto.MessageDto;
import com.themoah.themoah.domain.admin.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.themoah.themoah.domain.admin.entity.QMessage.message1;

@Repository
@RequiredArgsConstructor
public class MessageQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Message> findAll(MessageDto dto){
        return queryFactory.select(message1)
                .from(message1)
                .where(
                        likeMessageCode(dto.getMessageCode())
                )
                .fetch();
    }

    private BooleanExpression likeMessageCode(String messageCode) {
        if (StringUtils.hasText(messageCode)) {
            return (message1.messageCode.like("%" + messageCode + "%"));
        }
        return null;
    }
}
