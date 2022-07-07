package com.yibo.seata.service.service.impl;

import com.yibo.seata.domain.entity.Account;
import com.yibo.seata.dto.TransferRequest;
import com.yibo.seata.mapper.AccountMapper;
import com.yibo.seata.service.service.AccountService;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired(required = false)
    AccountMapper accountMapper;

    @Override
    @Transactional
    public boolean increaseMoney(BusinessActionContext businessActionContext, TransferRequest transferRequest) {
        log.info("账户服务：第一阶段开始：检查余额、冻结金额");

        Account account = accountMapper.selectByPrimaryKey(transferRequest.getUserId());
        accountMapper.tryIncreaseMoney(transferRequest.getUserId(), transferRequest.getMoney());
        log.info("账户服务：第一阶段结束：检查余额、冻结金额");

        return true;
    }

    @Override
    @Transactional
    public boolean commit(BusinessActionContext businessActionContext) {
        TransferRequest transferRequest = (TransferRequest) businessActionContext.getActionContext("transferRequest");
        accountMapper.confirmIncreaseMoney(transferRequest.getUserId(), transferRequest.getMoney());
        log.info("账户服务：第一阶段结束：检查余额、冻结金额");
        return true;
    }

    @Override
    @Transactional
    public boolean rollback(BusinessActionContext businessActionContext) {
        TransferRequest transferRequest = (TransferRequest) businessActionContext.getActionContext("transferRequest");
        accountMapper.cancelIncreaseMoney(transferRequest.getUserId(), transferRequest.getMoney());
        log.info("账户服务：第一阶段结束：检查余额、冻结金额");
        return false;
    }
}
