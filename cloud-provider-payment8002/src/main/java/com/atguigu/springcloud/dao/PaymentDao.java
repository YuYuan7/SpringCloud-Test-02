package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
//@Repository  使用此注解在插入数据时可能出现问题
public interface PaymentDao {
    int create(Payment payment);
    Payment getPaymentById(@Param("id") Long id);
}
