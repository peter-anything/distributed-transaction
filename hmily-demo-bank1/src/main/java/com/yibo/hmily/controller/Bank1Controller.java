package com.yibo.hmily.controller;

import com.yibo.hmily.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: huangyibo
 * @Date: 2020/10/8 20:13
 * @Description:
 */

@RestController
@RequestMapping("/bank1")
public class Bank1Controller {

    @Autowired
    private AccountInfoService accountInfoService;

    @GetMapping("/transfer/{amount}")
    public String transfer(@PathVariable("amount") Long amount){
        accountInfoService.updateAccountBalance("1",amount);
        return "success";
    }
}
