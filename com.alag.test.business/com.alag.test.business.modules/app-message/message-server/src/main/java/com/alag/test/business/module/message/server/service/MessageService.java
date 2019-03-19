package com.alag.test.business.module.message.server.service;

import com.alag.test.business.core.common.page.PageBean;
import com.alag.test.business.core.common.page.PageParam;
import com.alag.test.business.module.message.api.model.TransactionMessage;

import java.util.Map;

public interface MessageService {
    int saveMsgWaitingConfirm(TransactionMessage message);

    TransactionMessage getMsgById(String messageId);

    void confirmAndSendMsg(TransactionMessage message);

    int saveAndSendMsg(TransactionMessage transactionMessage);

    void directSendMsg(TransactionMessage transactionMessage);

    void reSendMsg(TransactionMessage transactionMessage);

    void setMsgStatusToAreadlyDead(TransactionMessage transactionMessage);

    void deleteMsgByMsgId(String messageId);

    void ReSendAllDeadMsgByQueueName(String queueName, int numPerPage);

    PageBean<TransactionMessage> lPage(PageParam pageParam, Map<String, Object> paramMap);
}
