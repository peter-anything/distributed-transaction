package com.yibo.seata.service;

import com.yibo.seata.dto.TransferRequest;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface AccountService {
    @TwoPhaseBusinessAction(name="transferAction",commitMethod = "commit",rollbackMethod = "rollback")
    boolean increaseMoney(BusinessActionContext businessActionContext,
                        @BusinessActionContextParameter(paramName = "transferRequest") TransferRequest transferRequest);
    boolean commit(BusinessActionContext businessActionContext);
    boolean rollback(BusinessActionContext businessActionContext);
}