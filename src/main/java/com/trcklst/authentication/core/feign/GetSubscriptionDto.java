package com.trcklst.authentication.core.feign;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetSubscriptionDto {

    private Integer id;
    private SubscriptionType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer userId;

}
