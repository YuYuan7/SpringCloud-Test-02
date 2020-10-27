package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeiginService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<CommonResult> get(@PathVariable("id") Long id){
        return paymentFeiginService.get(id);
    }

    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeout(){
        //客户端一般默认等待一秒
        return paymentFeiginService.paymentFeignTimeout();
    }

}
