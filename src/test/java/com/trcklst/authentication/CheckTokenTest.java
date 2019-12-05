package com.trcklst.authentication;


import com.trcklst.authentication.mock.TokenRequestParamsMock;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CheckTokenTest extends AuthenticationApplicationTests {

    @Test
    public void validTokenCheck() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret());

        ResultActions resultActions = sendAccessTokenRequest(TokenRequestParamsMock.PARAMS, headers);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        JSONObject token = getJsonToken(resultActions);
        String accessToken = token.getString("access_token");
        resultActions = sendCheckTokenRequest(accessToken);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    private ResultActions sendCheckTokenRequest(String token) throws Exception {
        return mockMvc.perform(post("/oauth/check_token")
                .param("token", token)
                .accept(MediaType.APPLICATION_JSON));
    }
}
