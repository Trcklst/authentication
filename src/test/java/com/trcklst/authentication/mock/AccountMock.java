package com.trcklst.authentication.mock;

import com.trcklst.authentication.core.Account;
import com.trcklst.authentication.core.AuthoritiesType;

public class AccountMock {

    public static final Account ACCOUNT_VALID_USER = getAccountValidUser();

    private static Account getAccountValidUser() {
        return Account.builder()
                .id(1)
                .username("user")
                .password("password")
                .active(true)
                .authority(AuthoritiesType.ROLE_USER)
                .build();
    }
}
