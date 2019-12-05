package com.trcklst.authentication.configuration;

import com.trcklst.authentication.core.db.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessTokenConverter extends JwtAccessTokenConverter {

    public CustomAccessTokenConverter() {
        setSigningKey("123");
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) super.enhance(accessToken, authentication);
        Integer userId = ((User) authentication.getPrincipal()).getId();
        oAuth2AccessToken.getAdditionalInformation().put("userId", userId);
        return oAuth2AccessToken;
    }

}
