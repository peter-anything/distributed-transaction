package com.yibo.seata.controller;

import com.yibo.seata.dto.TransferRequest;
import com.yibo.seata.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: huangyibo
 * @Date: 2020/10/8 20:13
 * @Description:
 */

@RestController
@RequestMapping("/bank2")
public class Bank2Controller {

    @Autowired
    private AccountService accountService;

    //张三转账
    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest transferRequest){
        accountService.increaseMoney(null, transferRequest);
        return "success";
    }
}
