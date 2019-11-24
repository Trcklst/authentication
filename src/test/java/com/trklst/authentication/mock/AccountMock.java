package com.trklst.authentication.mock;

import com.trklst.authentication.core.Account;
import com.trklst.authentication.core.AuthoritiesType;

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
