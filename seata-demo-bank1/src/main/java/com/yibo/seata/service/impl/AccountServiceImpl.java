package com.yibo.seata.service.impl;

import com.yibo.seata.apiservice.Bank2Client;
import com.yibo.seata.domain.entity.Account;
import com.yibo.seata.dto.TransferRequest;
import com.yibo.seata.mapper.AccountMapper;
import com.yibo.seata.service.AccountService;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired(required = false)
    AccountMapper accountMapper;

    @Autowired
    private Bank2Client bank2Client;

    @Override
    public boolean decreaseMoney(BusinessActionContext businessActionContext, TransferRequest transferRequest) {
        log.info("账户服务：第一阶段开始：检查余额、冻结金额");

        Account account = accountMapper.selectByPrimaryKey(transferRequest.getUserId());
        accountMapper.tryDecreaseMoney(transferRequest.getUserId(), transferRequest.getMoney());
        log.info("账户服务：第一阶段结束：检查余额、冻结金额");

        TransferRequest transferRequest2 = new TransferRequest();
        transferRequest2.setUserId(2L);
        transferRequest2.setMoney(transferRequest.getMoney());
        bank2Client.transfer(transferRequest);

        return true;
    }

    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        log.info("账户服务：第一阶段开始：检查余额、冻结金额");
        TransferRequest transferRequest = (TransferRequest) businessActionContext.getActionContext("transferRequest");

        Account account = accountMapper.selectByPrimaryKey(transferRequest.getUserId());
        accountMapper.confirmDecreaseMoney(transferRequest.getUserId(), transferRequest.getMoney());
        log.info("账户服务：第一阶段结束：检查余额、冻结金额");

        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        log.info("账户服务：第一阶段开始：检查余额、冻结金额");
        TransferRequest transferRequest = (TransferRequest) businessActionContext.getActionContext("transferRequest");

        Account account = accountMapper.selectByPrimaryKey(transferRequest.getUserId());
        accountMapper.cancelDecreaseMoney(transferRequest.getUserId(), transferRequest.getMoney());
        log.info("账户服务：第一阶段结束：检查余额、冻结金额");

        return true;
    }
}
