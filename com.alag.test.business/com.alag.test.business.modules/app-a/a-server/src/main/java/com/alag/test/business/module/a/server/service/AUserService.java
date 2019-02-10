package com.alag.test.business.module.a.server.service;

import com.alag.test.business.module.a.api.model.AUser;

public interface AUserService {
    AUser getUserInfo();

    int addMoney(long money);

    int minusMoney(long money);

    boolean transfer(int i);
}
