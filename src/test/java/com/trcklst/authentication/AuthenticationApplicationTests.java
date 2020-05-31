package com.trcklst.authentication;

import com.trcklst.authentication.configurations.DatabaseConfiguration;
import com.trcklst.authentication.core.feign.GetSubscriptionDto;
import com.trcklst.authentication.core.feign.GetSubscriptionFeignService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ActiveProfiles("test")
@Import({DatabaseConfiguration.class})
class AuthenticationApplicationTests {

    private static final String ACCESS_TOKEN_URI = "/oauth/token";

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private Filter springSecurityFilterChain;
    @Autowired
    protected OAuth2ClientProperties oAuth2ClientProperties;
    @MockBean
    protected GetSubscriptionFeignService getSubscriptionFeignService;

    protected MockMvc mockMvc;

    @PostConstruct
    void init() {
        Mockito.when(getSubscriptionFeignService.getSubscription(ArgumentMatchers.any()))
                .thenReturn(new GetSubscriptionDto());
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    void contextLoads() {
    }

    protected ResultActions sendAccessTokenRequest(MultiValueMap<String, String> params, HttpHeaders headers) throws Exception {
        return mockMvc.perform(post(ACCESS_TOKEN_URI)
                .params(params)
                .headers(headers)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON));
    }

    protected JSONObject getJsonToken(ResultActions result) {
        try {
            String tokenString = result.andReturn().getResponse().getContentAsString();
            return new JSONObject(tokenString);
        } catch (Exception e) {
            return null;
        }
    }

}
