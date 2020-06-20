package com.trcklst.authentication.configuration;

import com.trcklst.authentication.core.db.User;
import com.trcklst.authentication.core.feign.GetSubscriptionFeignService;
import com.trcklst.getsubscription.api.GetSubscriptionDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class CustomAccessTokenConverter extends JwtAccessTokenConverter {

    private final static String KEY = "secret-key";

    private final GetSubscriptionFeignService getSubscriptionFeignService;

    public CustomAccessTokenConverter(GetSubscriptionFeignService getSubscriptionFeignService) {
        this.getSubscriptionFeignService = getSubscriptionFeignService;
        setVerifierKey(KEY);
        setSigningKey(KEY);
    }

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
        User user = (User) authentication.getPrincipal();
        if (user == null)
            return null;
        GetSubscriptionDto getSubscriptionDto = callSubscriptionService(user.getId());
        Map<String, Object> additionalInformation = new HashMap<>();
        additionalInformation.put("userId", user.getId());
        additionalInformation.put("subscription", getSubscriptionDto.getType());
        additionalInformation.put("email", user.getUsername());
        return additionalInformation;
    }

    private GetSubscriptionDto callSubscriptionService(Integer userId) {
        try {
            return getSubscriptionFeignService.getSubscription(userId);
        } catch (Exception e) {
            log.error("Error while requesting subscription service with userId : {}", userId);
            return new GetSubscriptionDto();
        }
    }

}
