package com.trklst.authentication;

import com.trklst.authentication.configurations.DatabaseConfiguration;
import com.trklst.authentication.mock.AccountMock;
import com.trklst.authentication.mock.TokenRequestParamsMock;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ActiveProfiles("test")
@Import({DatabaseConfiguration.class})
class AuthenticationApplicationTests {

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    @PostConstruct
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void validAuthenticationTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret());

        ResultActions resultActions = sendRequest(TokenRequestParamsMock.PARAMS, headers);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        JSONObject token = getJsonToken(resultActions);
        Assertions.assertNotNull(token);
        Assertions.assertNotNull(token.getString("access_token"));
    }

    @Test
    void invalidClientAuthenticationTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String badClientId = String.format("%s BAD", oAuth2ClientProperties.getClientId());
        headers.setBasicAuth(badClientId, oAuth2ClientProperties.getClientSecret());

        ResultActions resultActions = sendRequest(TokenRequestParamsMock.PARAMS, headers);
        resultActions.andExpect(MockMvcResultMatchers.status().isUnauthorized());
        JSONObject token = getJsonToken(resultActions);
        if (token != null)
            Assertions.assertThrows(JSONException.class, () -> token.getString("access_token"));
    }

    @Test
    void invalidUserAuthenticationTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret());

        String badPassword = String.format("%s BAD", AccountMock.ACCOUNT_VALID_USER.getPassword());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(TokenRequestParamsMock.PARAMS);
        params.set("password", badPassword);

        ResultActions resultActions = sendRequest(params, headers);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        JSONObject token = getJsonToken(resultActions);
        if (token != null)
            Assertions.assertThrows(JSONException.class, () -> token.getString("access_token"));
    }

    private ResultActions sendRequest(MultiValueMap<String, String> params, HttpHeaders headers) throws Exception {
        return mockMvc.perform(post("/oauth/token")
                .params(params)
                .headers(headers)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON));
    }

    private JSONObject getJsonToken(ResultActions result) {
        try {
            String tokenString = result.andReturn().getResponse().getContentAsString();
            return new JSONObject(tokenString);
        } catch (Exception e) {
            return null;
        }
    }

}
