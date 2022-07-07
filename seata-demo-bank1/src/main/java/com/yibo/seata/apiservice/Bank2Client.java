package com.yibo.seata.apiservice;

import com.yibo.seata.dto.TransferRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

/**
 * @Author: huangyibo
 * @Date: 2020/10/8 20:12
 * @Description:
 */

@FeignClient(value="seata-bank2")
public interface Bank2Client {

    //远程调用微服务
    @PostMapping("/bank2/transfer")
    String transfer(@RequestBody TransferRequest transferRequest);
}
