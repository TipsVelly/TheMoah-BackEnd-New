package com.themoah.themoah.domain.admin.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.themoah.themoah.domain.admin.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.themoah.themoah.domain.admin.entity.QMessage.message1;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MessageQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Message> findByMessageCode(String codeGroup){
        return queryFactory.select(message1)
                .from(message1)
                .where(
                        likeMessageCode(codeGroup)
                )
                .fetch();
    }

    private BooleanExpression likeMessageCode(String codeGroup) {

        if(Objects.equals(codeGroup, "All")){
            codeGroup="";
        }
        return (message1.messageCode.like("%" + codeGroup + "%"));
    }
}
