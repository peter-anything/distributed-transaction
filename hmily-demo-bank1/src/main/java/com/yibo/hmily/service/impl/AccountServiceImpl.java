package com.yibo.hmily.service.impl;

import com.yibo.hmily.domain.entity.Account;
import com.yibo.hmily.mapper.AccountMapper;
import com.yibo.hmily.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.dromara.hmily.annotation.Hmily;


import java.math.BigDecimal;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired(required = false)
    AccountMapper accountMapper;

    @Override
    @Hmily(confirmMethod = "confirmDecreaseMoney", cancelMethod = "cancelDecreaseMoney")
    public void tryDecreaseMoney(Long userId, BigDecimal money) {
        log.info("账户服务：第一阶段开始：检查余额、冻结金额");

        Account account = accountMapper.selectByPrimaryKey(userId);
        if (account.getResidue().compareTo(money) < 0) {
            throw new RuntimeException("余额不足。" +
                    "实际余额：" + account.getResidue() + "，购买金额：" + money);
        }
       accountMapper.tryDecreaseMoney(userId, money);
        log.info("账户服务：第一阶段结束：检查余额、冻结金额");
    }

    public void confirmDecreaseMoney(Long userId, BigDecimal money) {
        log.info("账户服务：第二阶段提交开始：释放冻结的金额、增加已用金额");

        accountMapper.confirmDecreaseMoney(userId, money);

        log.info("账户服务：第二阶段提交结束：释放冻结的金额、增加已用金额");
    }

    public void cancelDecreaseMoney(Long userId, BigDecimal money) {
        log.info("账户服务：第二阶段回滚开始：释放冻结的金额、增加回原来余额");

        accountMapper.cancelDecreaseMoney(userId, money);

        log.info("账户服务：第二阶段回滚结束：释放冻结的金额、增加回原来余额");
    }

    @Override
    @Hmily(confirmMethod = "confirmDecreaseMoneyFault", cancelMethod = "cancelDecreaseMoneyFault")
    public void tryDecreaseMoneyFault(Long userId, BigDecimal money) {
        log.info("账户服务：第一阶段开始：检查余额、冻结金额");

        Account account = accountMapper.selectByPrimaryKey(userId);
        if (account.getResidue().compareTo(money) < 0) {
            throw new RuntimeException("余额不足。" +
                    "实际余额：" + account.getResidue() + "，购买金额：" + money);
        }
        accountMapper.tryDecreaseMoney(userId, money);

        log.info("账户服务：第一阶段结束：检查余额、冻结金额");
    }

    public void confirmDecreaseMoneyFault(Long userId, BigDecimal money) {
        log.info("账户服务：第二阶段提交开始：释放冻结的金额、增加已用金额");

        int i = 1 / 0;
        accountMapper.confirmDecreaseMoney(userId, money);

        log.info("账户服务：第二阶段提交结束：释放冻结的金额、增加已用金额");
    }

    public void cancelDecreaseMoneyFault(Long userId, BigDecimal money) {
        log.info("账户服务：第二阶段回滚开始：释放冻结的金额、增加回原来余额");

        int i = 1 / 0;

        accountMapper.cancelDecreaseMoney(userId, money);

        log.info("账户服务：第二阶段回滚结束：释放冻结的金额、增加回原来余额");
    }
}
