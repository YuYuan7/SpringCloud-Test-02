package com.atguigu.springcloud.controller;

import com.alibaba.fastjson.JSON;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.ILoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    private static final String URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private ILoadBalancer loadBalancer;

    @GetMapping(value = "/consumer/payment/create")
    public CommonResult creat(Payment payment){
        return restTemplate.postForObject(URL + "/payment/create" ,payment ,CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult get(@PathVariable("id") Long id){
        return restTemplate.getForObject(URL + "/payment/get/" + id ,CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/create2")
    public CommonResult creat2(Payment payment){
        ResponseEntity<CommonResult> entity = restTemplate.postForEntity(URL + "/payment/create", payment, CommonResult.class);
        System.out.println(" >>>>> " + JSON.toJSONString(entity));
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommonResult(405,"操作失败");
        }

    }

    @GetMapping(value = "/consumer/payment/getForEntity/{id}")
    public CommonResult getPayment2(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity =  restTemplate.getForEntity(URL + "/payment/get/" + id ,CommonResult.class);
        System.out.println(" >>>>> " + JSON.toJSONString(entity));
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommonResult(405,"操作失败");
        }
    }

    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLb(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances == null || instances.size() <= 0){
            return null;
        }
        ServiceInstance instance = loadBalancer.instance(instances);
        URI uri = instance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb",String.class);
    }

    // ====================> zipkin+sleuth
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin()
    {
        String result = restTemplate.getForObject("http://localhost:8001"+"/payment/zipkin/", String.class);
        return result;
    }

}
