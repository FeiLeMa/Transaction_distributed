package com.alag.test.business.module.message.server.service.impl;

import com.alag.test.business.core.common.enums.PublicEnum;
import com.alag.test.business.core.common.page.PageBean;
import com.alag.test.business.core.common.page.PageParam;
import com.alag.test.business.module.message.api.enums.MessageStatusEnum;
import com.alag.test.business.module.message.api.model.TransactionMessage;
import com.alag.test.business.module.message.server.controller.MessageServerController;
import com.alag.test.business.module.message.server.mapper.TransactionMessageMapper;
import com.alag.test.business.module.message.server.service.MessageService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LogManager.getLogger(MessageServerController.class);

    @Autowired
    TransactionMessageMapper transactionMessageMapper;
    @Autowired
    JmsTemplate jmsTemplate;
    private TransactionMessage message;


    @Override
    public int saveMsgWaitingConfirm(TransactionMessage message) {
        message.setEditTime(new Date());
        message.setStatus(MessageStatusEnum.WAITING_CONFIRM.name());
        message.setAreadlyDead(PublicEnum.NO.name());
        message.setMessageSendTimes(0);
        return transactionMessageMapper.insert(message);
    }

    @Override
    public TransactionMessage getMsgById(String messageId) {
        return transactionMessageMapper.getMsgById(messageId);
    }

    @Override
    public void confirmAndSendMsg(TransactionMessage message) {
        message.setStatus(MessageStatusEnum.SENDING.name());
        message.setEditTime(new Date());
        transactionMessageMapper.updateByPrimaryKeySelective(message);
        jmsTemplate.setDefaultDestinationName(message.getConsumerQueue());
        jmsTemplate.send((session -> {
            return session.createTextMessage(message.getMessageBody());
        }));
    }

    @Override
    public int saveAndSendMsg(TransactionMessage transactionMessage) {
        transactionMessage.setAreadlyDead(PublicEnum.NO.name());
        transactionMessage.setStatus(MessageStatusEnum.SENDING.name());
        transactionMessage.setEditTime(new Date());
        transactionMessage.setMessageSendTimes(0);
        int result = transactionMessageMapper.insert(transactionMessage);
        jmsTemplate.setDefaultDestinationName(transactionMessage.getConsumerQueue());
        jmsTemplate.send((session -> {
            return session.createTextMessage(transactionMessage.getMessageBody());
        }));
        return result;
    }

    @Override
    public void directSendMsg(TransactionMessage transactionMessage) {
        jmsTemplate.setDefaultDestinationName(transactionMessage.getConsumerQueue());
        jmsTemplate.send((session -> {
            return session.createTextMessage(transactionMessage.getMessageBody());}));
    }

    @Override
    public void reSendMsg(TransactionMessage transactionMessage) {
        int maxTimes = Integer.valueOf(transactionMessage.getMessageSendTimes());
        if (transactionMessage.getMessageSendTimes() >= maxTimes) {
            transactionMessage.setAreadlyDead(PublicEnum.YES.name());
        }
        transactionMessage.setEditTime(new Date());
        transactionMessage.setMessageSendTimes(transactionMessage.getMessageSendTimes() + 1);
        transactionMessageMapper.updateByPrimaryKeySelective(transactionMessage);
        jmsTemplate.setDefaultDestinationName(transactionMessage.getConsumerQueue());
        jmsTemplate.send((session -> {
            return session.createTextMessage(transactionMessage.getMessageBody());}));
    }

    @Override
    public void setMsgStatusToAreadlyDead(TransactionMessage transactionMessage) {
        transactionMessage.setAreadlyDead(PublicEnum.YES.name());
        transactionMessage.setEditTime(new Date());
        transactionMessageMapper.updateByPrimaryKeySelective(transactionMessage);
    }

    @Override
    public void deleteMsgByMsgId(String messageId) {
        transactionMessageMapper.deleteByPrimaryKey(messageId);
    }

    @Override
    public void ReSendAllDeadMsgByQueueName(String queueName, int numPerPage) {
        int pageNum = 1;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("consumerQueue", queueName);
        paramMap.put("areadlyDead", PublicEnum.YES.name());
        paramMap.put("lPageSortType", "ASC");


        Map<String, TransactionMessage> messageMap = new HashMap<String, TransactionMessage>();
        List<Object> recordList = new ArrayList<Object>();
        int pageCount = 1;

        PageBean pageBean = this.lPage(new PageParam(pageNum, numPerPage), paramMap);
        recordList = pageBean.getRecordList();
        if (pageBean == null || recordList.isEmpty()) {
            logger.info("recordList is Empty");
            return;
        }

        pageCount = pageBean.getTotalCount();
        for (final Object obj : recordList) {
            TransactionMessage message = (TransactionMessage) obj;
            messageMap.put(message.getMessageId(), message);
        }

        for (pageNum = 2; pageNum <= pageCount; pageNum++) {
            pageBean = this.lPage(new PageParam(pageNum, numPerPage),paramMap);
            recordList = pageBean.getRecordList();

            if (recordList == null || recordList.isEmpty()) {
                break;
            }

            for (final Object obj : recordList) {
                final TransactionMessage message = (TransactionMessage) obj;
                messageMap.put(message.getMessageId(), message);
            }
        }

        recordList = null;
        pageBean = null;

        for (Map.Entry<String, TransactionMessage> entries : messageMap.entrySet()) {
            TransactionMessage message = entries.getValue();
            message.setEditTime(new Date());
            message.setMessageSendTimes(message.getMessageSendTimes() + 1);
            transactionMessageMapper.updateByPrimaryKeySelective(message);

            jmsTemplate.setDefaultDestinationName(message.getConsumerQueue());
            jmsTemplate.send((session -> {
                return session.createTextMessage(message.getMessageBody());}));
        }
        
    }


    /**
     * 分页查询
     * @param pageParam
     * @param paramMap
     * @return
     */
    @Override
    public PageBean lPage(PageParam pageParam, Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap<String,Object>();
        }


        //统计总记录数
        Long totalCount = transactionMessageMapper.countMessageInDeadByQueueName(pageParam);
        // 校验当前页数
        int currentPage = PageBean.checkCurrentPage(totalCount.intValue(), pageParam.getNumPerPage(), pageParam.getPageNum());
        pageParam.setPageNum(currentPage); // 为当前页重新设值
        // 校验页面输入的每页记录数numPerPage是否合法
        int numPerPage = PageBean.checkNumPerPage(pageParam.getNumPerPage()); // 校验每页记录数
        pageParam.setNumPerPage(numPerPage); // 重新设值

        // 根据页面传来的分页参数构造SQL分页参数
        paramMap.put("pageFirst", (pageParam.getPageNum() - 1) * pageParam.getNumPerPage());
        paramMap.put("pageSize", pageParam.getNumPerPage());
        paramMap.put("startRowNum", (pageParam.getPageNum() - 1) * pageParam.getNumPerPage());
        paramMap.put("endRowNum", pageParam.getPageNum() * pageParam.getNumPerPage());

        // 获取分页数据集
        List<Object> list = transactionMessageMapper.selectDeadMsgByQName(paramMap);

        Object isCount = paramMap.get("isCount"); // 是否统计当前分页条件下的数据：1:是，其他为否
        if (isCount != null && "1".equals(isCount.toString())) {
            //Map<String, Object> countResultMap = transactionMessageMapper.selectOne(getStatement(SQL_COUNT_BY_PAGE_PARAM), paramMap);
            //return new PageBean(pageParam.getPageNum(), pageParam.getNumPerPage(), totalCount.intValue(), list, countResultMap);
            return null;
        } else {
            // 构造分页对象
            return new PageBean(pageParam.getPageNum(), pageParam.getNumPerPage(), totalCount.intValue(), list);
        }

    }


}
