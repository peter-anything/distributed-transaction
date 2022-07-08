package com.yibo.seata.controller;

import com.yibo.seata.dto.TransferRequest;
import com.yibo.seata.service.AccountService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: huangyibo
 * @Date: 2020/10/8 20:13
 * @Description:
 */

@RestController
@RequestMapping("/bank1")
public class Bank1Controller {

    @Autowired
    private AccountService accountService;

//    @GlobalTransactional
    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest transferRequest){
        accountService.decreaseMoney(null, transferRequest);
        return "success";
    }
}