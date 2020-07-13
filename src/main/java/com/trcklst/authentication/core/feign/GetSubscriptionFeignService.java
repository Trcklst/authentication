package com.trcklst.authentication.core.feign;

import com.trcklst.getsubscription.api.GetSubscriptionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("subscription")
public interface GetSubscriptionFeignService {

    @GetMapping("/api/subscription/")
    GetSubscriptionDto getSubscription(@RequestHeader("userId") Integer userId);
}
