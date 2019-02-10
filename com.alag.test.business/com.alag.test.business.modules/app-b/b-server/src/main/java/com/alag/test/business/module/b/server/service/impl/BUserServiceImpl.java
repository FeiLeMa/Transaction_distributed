package com.alag.test.business.module.b.server.service.impl;

import com.alag.test.business.module.b.api.model.BUser;
import com.alag.test.business.module.b.server.mapper.BUserMapper;
import com.alag.test.business.module.b.server.service.BUserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BUserServiceImpl implements BUserService {
    private Logger logger = LogManager.getLogger(BUserServiceImpl.class);
    @Autowired
    private BUserMapper bUserMapper;
    @Override
    public BUser getUserInfo() {
        return bUserMapper.selectByPrimaryKey(1);
    }

    @Override
    public int addMoney(long money) {
        int ret = bUserMapper.addMoneyById(1, money);
        logger.info("agou的账户余额添加了20元");
        return ret;
    }
    @Transactional
    @Override
    public int minusMoney(long money) {
        int ret = bUserMapper.minusMoneyById(1, money);
        logger.info("agou的账户余额减少了20元");
//        logger.info("恭喜agou中奖了+120元");
//        int a = 4/0;
//        bUserMapper.addMoneyById(1, 120);

        return ret;
    }
}
