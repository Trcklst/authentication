package com.trcklst.authentication.mock;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TokenRequestParamsMock {

    public static final MultiValueMap<String, String> PARAMS = createParams();

    private static MultiValueMap<String, String> createParams() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", UserMock.VALID_USER.getUsername());
        params.add("password", UserMock.VALID_USER.getPassword());
        params.add("grant_type", "password");
        return params;
    }
}
