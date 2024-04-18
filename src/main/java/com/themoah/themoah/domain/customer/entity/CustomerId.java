package com.themoah.themoah.domain.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Builder
public class CustomerId implements Serializable {
    @Column(name="indust_code")
    private String industCode;
    @Column(name="cust_code")
    private String custCode;


}
