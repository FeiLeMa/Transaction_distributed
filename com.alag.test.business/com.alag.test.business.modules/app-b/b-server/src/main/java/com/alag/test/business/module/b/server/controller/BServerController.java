package com.alag.test.business.module.b.server.controller;

import com.alag.test.business.core.common.response.ServerResponse;
import com.alag.test.business.module.b.api.BController;
import com.alag.test.business.module.b.api.model.BUser;
import com.alag.test.business.module.b.server.service.BUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("b")
public class BServerController implements BController {

    @Autowired
    private BUserService bUserService;

    @RequestMapping("getUserInfo")
    @Override
    public ServerResponse<BUser> getUserInfo() {
        BUser bUser = bUserService.getUserInfo();

        return ServerResponse.createBySuccess(bUser);
    }
    @RequestMapping("addMoney")
    @Override
    public ServerResponse addMoney(@RequestParam(name = "money",defaultValue = "20")long money) {
        int ret = bUserService.addMoney(money);
        if (ret>0){
            return ServerResponse.createBySuccessMessage("agou的账户余额增加了20元");
        }
        return ServerResponse.createBySuccessMessage("agou添加金额失败");
    }
    @RequestMapping("minusMoney")
    @Override
    public ServerResponse minusMoney(@RequestParam(name = "money",defaultValue = "20")long money) {
        int ret = bUserService.minusMoney(money);
        if (ret>0){
            return ServerResponse.createBySuccessMessage("agou的账户余额减少了20元");
        }
        return ServerResponse.createBySuccessMessage("agou添加金额失败");
    }
}
