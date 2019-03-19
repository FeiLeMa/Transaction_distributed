package com.alag.test.business.module.message.server.controller;

import com.alag.test.business.core.common.enums.PublicEnum;
import com.alag.test.business.core.common.page.PageBean;
import com.alag.test.business.core.common.page.PageParam;
import com.alag.test.business.module.message.api.MessageController;
import com.alag.test.business.module.message.api.exceptions.MessageBizException;
import com.alag.test.business.module.message.api.model.TransactionMessage;
import com.alag.test.business.module.message.server.service.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.LogManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin2.message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageServerController implements MessageController {
    private static final Logger logger = LogManager.getLogger(MessageServerController.class);

    @Autowired
    MessageService messageService;

    @Override
    @RequestMapping("saveMessageWaitingConfirm")
    public int saveMessageWaitingConfirm(TransactionMessage message) {
        if (message == null) {
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL, "保存的消息为空");
        }

        if (StringUtils.isEmpty(message.getConsumerQueue())) {
            throw new MessageBizException(MessageBizException.MESSAGE_CONSUMER_QUEUE_IS_NULL, "消息的消费队列不能为空 ");
        }

        return messageService.saveMsgWaitingConfirm(message);

    }

    @Override
    @RequestMapping("confirmAndSendMessage")
    public void confirmAndSendMessage(String messageId) throws MessageBizException {
        final TransactionMessage message = getMessageByMessageId(messageId);
        if (message == null) {
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL, "根据消息id查找的消息为空");
        }
        messageService.confirmAndSendMsg(message);
    }


    @Override
    public TransactionMessage getMessageByMessageId(String messageId) {
        return messageService.getMsgById(messageId);
    }

    @Override
    @RequestMapping("saveAndSendMessage")
    public int saveAndSendMessage(TransactionMessage transactionMessage) throws MessageBizException {
        if (transactionMessage == null) {
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL, "保存消息为空");
        }
        if (StringUtils.isEmpty(transactionMessage.getConsumerQueue())) {
            throw new MessageBizException(MessageBizException.MESSAGE_CONSUMER_QUEUE_IS_NULL, "消息队列不能为空");
        }

        return messageService.saveAndSendMsg(transactionMessage);
    }

    @Override
    @RequestMapping("directSendMessage")
    public void directSendMessage(TransactionMessage transactionMessage) throws MessageBizException {
        if (transactionMessage == null) {
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL, "保存消息为空");
        }
        if (StringUtils.isEmpty(transactionMessage.getConsumerQueue())) {
            throw new MessageBizException(MessageBizException.MESSAGE_CONSUMER_QUEUE_IS_NULL, "消息队列不能为空");
        }
        messageService.directSendMsg(transactionMessage);
    }

    @Override
    @RequestMapping("reSendMessageByMessageId")
    public void reSendMessageByMessageId(String messageId) throws MessageBizException {
        final TransactionMessage transactionMessage = getMessageByMessageId(messageId);
        if (transactionMessage == null) {
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL, "此消息Id查询结果为空");
        }
        int maxTimes = Integer.valueOf(transactionMessage.getMessageSendTimes());

        messageService.reSendMsg(transactionMessage);


    }

    @Override
    @RequestMapping("setMessageToAreadlyDead")
    public void setMessageToAreadlyDead(String messageId) throws MessageBizException {
        final TransactionMessage transactionMessage = getMessageByMessageId(messageId);
        if (transactionMessage == null) {
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL, "此消息Id查询结果为空");
        }
        messageService.setMsgStatusToAreadlyDead(transactionMessage);
    }

    @Override
    @RequestMapping("deleteMessageByMessageId")
    public void deleteMessageByMessageId(String messageId) throws MessageBizException {
        final TransactionMessage transactionMessage = getMessageByMessageId(messageId);
        if (transactionMessage == null) {
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL, "此消息ID查询结果为空");
        } else {
            messageService.deleteMsgByMsgId(messageId);
        }
    }

    @Override
    @RequestMapping("reSendAllDeadMessageByQueueName")
    public void reSendAllDeadMessageByQueueName(String queueName, int batchSize) throws MessageBizException {
        logger.info("==>reSendAllDeadMessageByQueueName");

        int numPerPage = 1000;
        if (batchSize > 0 && batchSize < 100) {
            numPerPage = 100;
        } else if (batchSize > 100 && batchSize < 5000) {
            numPerPage = batchSize;
        } else if (batchSize > 5000) {
            numPerPage = 5000;
        } else {
            numPerPage = 1000;
        }

        messageService.ReSendAllDeadMsgByQueueName(queueName, numPerPage);
    }

    @Override
    @RequestMapping("listPage")
    public PageBean<TransactionMessage> listPage(PageParam pageParam, Map<String, Object> paramMap) {
        return messageService.lPage(pageParam,paramMap);
    }


}
