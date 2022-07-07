package com.yibo.hmily.service;

import java.math.BigDecimal;

public interface AccountService {
    void tryDecreaseMoney(Long userId, BigDecimal money);

    void tryDecreaseMoneyFault(Long userId, BigDecimal money);
}
