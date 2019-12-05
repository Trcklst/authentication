package com.trcklst.authentication.mock;

import com.trcklst.authentication.core.db.AuthoritiesType;
import com.trcklst.authentication.core.db.User;

public class UserMock {

    public static final User VALID_USER = getValidUser();

    private static User getValidUser() {
        return User.builder()
                .id(1)
                .username("user")
                .password("password")
                .active(true)
                .authority(AuthoritiesType.ROLE_USER)
                .build();
    }
}
