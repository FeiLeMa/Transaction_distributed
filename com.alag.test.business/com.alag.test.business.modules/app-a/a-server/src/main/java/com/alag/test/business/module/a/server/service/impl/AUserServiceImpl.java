package com.alag.test.business.module.a.server.service.impl;

import com.alag.test.business.core.common.response.ServerResponse;
import com.alag.test.business.module.a.api.model.AUser;
import com.alag.test.business.module.a.server.mapper.AUserMapper;
import com.alag.test.business.module.a.server.service.AUserService;
import com.alag.test.business.module.b.feign.controller.BServiceFeign;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AUserServiceImpl implements AUserService {
    private Logger logger = LogManager.getLogger(AUserServiceImpl.class);
    @Autowired
    private AUserMapper aUserMapper;
    @Autowired
    private BServiceFeign bServiceFeign;
    @Override
    public AUser getUserInfo() {
        return aUserMapper.selectByPrimaryKey(1);
    }

    @Override
    public int addMoney(long money) {
        int ret = aUserMapper.addMoneyById(1, money);
        logger.info("alan的账户余额添加了20元");
        return ret;
    }
    @Transactional
    @Override
    public int minusMoney(long money) {
        int ret = aUserMapper.minusMoneyById(1, money);
        logger.info("alan的账户余额减少了20元");
//        logger.info("恭喜alan中奖了+120元");
//        aUserMapper.addMoneyById(1, 120);

        return ret;
    }
    @Transactional
    @Override
    public boolean transfer(int money) {
        ServerResponse serverResponse = bServiceFeign.addMoney(money);
        int a = 3 / 0;
        if (serverResponse.isSuccess()) {
            int ret = aUserMapper.minusMoneyById(1, money);
            if (ret>0){
                return true;
            }
        }
        return false;
    }


}
