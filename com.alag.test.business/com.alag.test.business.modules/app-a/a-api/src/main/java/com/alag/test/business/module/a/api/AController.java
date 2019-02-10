package com.alag.test.business.module.a.api;

import com.alag.test.business.core.common.response.ServerResponse;
import com.alag.test.business.module.a.api.model.AUser;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("a")
public interface AController {

    @RequestMapping("getUserInfo")
    ServerResponse<AUser> getUserInfo();

    @RequestMapping("addMoney/{money}")
    ServerResponse addMoney(long money);

    @RequestMapping("minusMoney")
    ServerResponse minusMoney(long money);

    @RequestMapping("transfer")
    ServerResponse transfer();
}
