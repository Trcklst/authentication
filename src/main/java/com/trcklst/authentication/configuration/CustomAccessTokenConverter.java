package com.trcklst.authentication.configuration;

import com.trcklst.authentication.core.db.User;
import com.trcklst.authentication.core.feign.GetSubscriptionDto;
import com.trcklst.authentication.core.feign.GetSubscriptionFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAccessTokenConverter extends JwtAccessTokenConverter {

    private final GetSubscriptionFeignService getSubscriptionFeignService;

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        OAuth2Authentication oAuth2Authentication = super.extractAuthentication(claims);
        oAuth2Authentication.setDetails(claims);
        return oAuth2Authentication;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            Map<String, Object> additionalInformation = getAdditionalInformation(authentication);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        }
        return super.enhance(accessToken, authentication);
    }

    private Map<String, Object> getAdditionalInformation(OAuth2Authentication authentication) {
        Integer userId = ((User) authentication.getPrincipal()).getId();
        GetSubscriptionDto getSubscriptionDto = getSubscriptionFeignService.getSubscription(userId);
        Map<String, Object> additionalInformation = new HashMap<>();
        additionalInformation.put("userId", userId);
        additionalInformation.put("subscription", getSubscriptionDto.getType());
        return additionalInformation;
    }

}
