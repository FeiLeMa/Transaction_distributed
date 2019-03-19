package com.alag.test.business.module.message.api;

import com.alag.test.business.core.common.page.PageBean;
import com.alag.test.business.core.common.page.PageParam;
import com.alag.test.business.module.message.api.exceptions.MessageBizException;
import com.alag.test.business.module.message.api.model.TransactionMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.plugin2.message.Message;

import java.util.Map;

@RequestMapping("message")
public interface MessageController {


    /**
     * 预存储消息
     * @param transactionMessage
     * @return
     */
    @RequestMapping("saveMessageWaitingConfirm")
    public int saveMessageWaitingConfirm(TransactionMessage transactionMessage);

    /**
     * 确认并发送消息.
     */
    @RequestMapping("confirmAndSendMessage")
    public void confirmAndSendMessage(String messageId) throws MessageBizException;

    /**
     * 根据消息Id获取消息
     */
    @RequestMapping("getMessageByMessageId")
    public TransactionMessage getMessageByMessageId(String messageId);

    /**
     * 存储并发送
     */
    @RequestMapping("saveAndSendMessage")
    public int saveAndSendMessage(TransactionMessage transactionMessage) throws MessageBizException;

    /**
     * 直接发送消息到MQ
     */
    @RequestMapping("directSendMessage")
    public void directSendMessage(TransactionMessage transactionMessage) throws MessageBizException;

    /**
     * 根据messageId重发这条消息
     */
    @RequestMapping("reSendMessageByMessageId")
    public void reSendMessageByMessageId(String messageId) throws MessageBizException;

    /**
     * 将消息标记为死亡
     */
    public void setMessageToAreadlyDead(String messageId) throws MessageBizException;

    /**
     * 根据消息ID删除消息
     */
    public void deleteMessageByMessageId(String messageId) throws MessageBizException;


    /**
     * 重发指定MQ队列中所有死亡队列消息
     * @param queueName 队列
     * @param batchSize 按批处理
     * @throws MessageBizException
     */
    public void reSendAllDeadMessageByQueueName(String queueName, int batchSize) throws MessageBizException;

    /**
     * 分页查询
     * @param pageParam
     * @param paramMap
     * @return
     */
    public PageBean<TransactionMessage> listPage(PageParam pageParam, Map<String, Object> paramMap);
}
