package com.trcklst.authentication.core.feign;

import com.trcklst.getsubscription.api.GetSubscriptionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("subscription")
public interface GetSubscriptionFeignService {

    @GetMapping("/api/subscription/{userId}")
    GetSubscriptionDto getSubscription(@PathVariable Integer userId);
}
