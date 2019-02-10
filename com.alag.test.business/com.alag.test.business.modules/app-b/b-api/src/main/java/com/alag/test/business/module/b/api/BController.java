package com.alag.test.business.module.b.api;

import com.alag.test.business.core.common.response.ServerResponse;
import com.alag.test.business.module.b.api.model.BUser;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("b")
public interface BController {

    @RequestMapping("getUserInfo")
    ServerResponse<BUser> getUserInfo();

    @RequestMapping("addMoney")
    ServerResponse addMoney(long money);

    @RequestMapping("minusMoney")
    ServerResponse minusMoney(long money);
}
