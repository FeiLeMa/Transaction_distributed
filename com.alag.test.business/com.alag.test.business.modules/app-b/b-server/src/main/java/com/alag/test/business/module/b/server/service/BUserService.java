package com.alag.test.business.module.b.server.service;

import com.alag.test.business.module.b.api.model.BUser;

public interface BUserService {
    BUser getUserInfo();

    int addMoney(long money);

    int minusMoney(long money);
}
