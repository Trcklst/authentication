package com.trcklst.authentication;

import com.trcklst.authentication.mock.TokenRequestParamsMock;
import com.trcklst.authentication.mock.UserMock;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class GetTokenTest extends AuthenticationApplicationTests {

    @Test
    void validAuthenticationTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret());

        ResultActions resultActions = sendAccessTokenRequest(TokenRequestParamsMock.PARAMS, headers);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        JSONObject token = getJsonToken(resultActions);
        Assertions.assertNotNull(token);
        Assertions.assertNotNull(token.getString("access_token"));
        Assertions.assertEquals(1, token.getInt("userId"));
    }

    @Test
    void invalidClientAuthenticationTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String badClientId = String.format("%s BAD", oAuth2ClientProperties.getClientId());
        headers.setBasicAuth(badClientId, oAuth2ClientProperties.getClientSecret());

        ResultActions resultActions = sendAccessTokenRequest(TokenRequestParamsMock.PARAMS, headers);
        resultActions.andExpect(MockMvcResultMatchers.status().isUnauthorized());
        JSONObject token = getJsonToken(resultActions);
        if (token != null)
            Assertions.assertThrows(JSONException.class, () -> token.getString("access_token"));
    }

    @Test
    void invalidUserAuthenticationTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(TokenRequestParamsMock.PARAMS);
        String badPassword = String.format("%s BAD", UserMock.VALID_USER.getPassword());
        params.set("password", badPassword);

        ResultActions resultActions = sendAccessTokenRequest(params, headers);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        JSONObject token = getJsonToken(resultActions);
        if (token != null)
            Assertions.assertThrows(JSONException.class, () -> token.getString("access_token"));
    }
}
