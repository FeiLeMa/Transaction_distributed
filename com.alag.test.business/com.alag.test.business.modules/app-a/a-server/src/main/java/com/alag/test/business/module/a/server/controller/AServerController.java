package com.alag.test.business.module.a.server.controller;

import com.alag.test.business.core.common.response.ServerResponse;
import com.alag.test.business.module.a.api.AController;
import com.alag.test.business.module.a.api.model.AUser;
import com.alag.test.business.module.a.server.service.AUserService;
import com.alag.test.business.module.b.feign.controller.BServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("a")
public class AServerController implements AController {
    @Autowired
    private AUserService aUserService;


    @RequestMapping("getUserInfo")
    @Override
    public ServerResponse<AUser> getUserInfo() {
        AUser aUser = aUserService.getUserInfo();

        return ServerResponse.createBySuccess(aUser);
    }
    @RequestMapping("addMoney")
    @Override
    public ServerResponse addMoney(@RequestParam(name = "money",defaultValue = "20")long money) {
        int ret = aUserService.addMoney(money);
        if (ret>0){
            return ServerResponse.createBySuccessMessage("alan的账户余额增加了20元");
        }
        return ServerResponse.createBySuccessMessage("alan添加金额失败");
    }
    @RequestMapping("minusMoney")
    @Override
    public ServerResponse minusMoney(@RequestParam(name = "money",defaultValue = "20")long money) {
        int ret = aUserService.minusMoney(money);
        if (ret>0){
            return ServerResponse.createBySuccessMessage("alan的账户余额减少了20元");
        }
        return ServerResponse.createBySuccessMessage("alan添加金额失败");
    }

    @RequestMapping("transfer")
    @Override
    public ServerResponse transfer() {
        if(aUserService.transfer(20)){
            return ServerResponse.createBySuccessMessage("alan向agou转了20元");
        }
        return ServerResponse.createByErrorMessage("转账失败");
    }
}
