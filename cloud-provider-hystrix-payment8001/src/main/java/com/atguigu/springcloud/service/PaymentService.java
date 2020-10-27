package com.atguigu.springcloud.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentService {
    /**
     * 正常访问
     *
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id);

    /**
     * 超时访问
     *
     * @param id
     * @return
     */
    String paymentInfo_TimeOut(Integer id);

    /**
     * 服务熔断
     * @param id
     * @return
     */
    String paymentCircuitBreaker(@PathVariable("id") Integer id);
}
