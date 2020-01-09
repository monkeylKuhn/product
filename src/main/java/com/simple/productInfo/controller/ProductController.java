 package com.simple.productInfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple.productInfo.DTO.OrderDTO;
import com.simple.productInfo.DTO.SupplierOrderDTO;
import com.simple.productInfo.DTO.WarehouseOrderDTO;
import com.simple.productInfo.config.MailProperties;
import com.simple.productInfo.controller.param.CreateOrderParam;
import com.simple.productInfo.controller.param.FindOrderListParam;
import com.simple.productInfo.controller.param.FindSupplierOrderListParam;
import com.simple.productInfo.mapper.OrderMapper;
import com.simple.productInfo.task.TestStockTask;
import com.simple.productInfo.task.TestTask;
import com.simple.productInfo.utils.PageInfo;

@RestController
@RequestMapping("/product")
 public class ProductController {

    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    TestTask testTask;
    @Autowired
    TestStockTask testStockTask;
    @Autowired
    MailProperties mailProperties;
    @Autowired
    JavaMailSender javaMailSender;
    @GetMapping("/mail")
    public Object sendMail(){
        
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getFrom());
        mailMessage.setTo(mailProperties.getTo());
     
        mailMessage.setSubject("测试发送邮件");
        mailMessage.setText("hello world");
     
        javaMailSender.send(mailMessage);
        
        
        return true;
    }
    
    
    @GetMapping("/spu")
    public Object fetchSpu(){
    	testTask.fetchProduct();
        return true;
    }
    
    @GetMapping("/sku")
    public Object fetchSku(){
    	testStockTask.fetchStock();
        return true;
    }
    
    
    @PostMapping("/order")
    public String createOrder(@RequestBody CreateOrderParam createOrderParam) {
        //检验 TODO
        
        //获取商品信息 验证库存 价格是否发生改变
        
        //调用api接口锁商品 
        Integer flag = orderMapper.insert(createOrderParam);
        
        return null;
    }
    
    @PostMapping("/order/list")
    public PageInfo<OrderDTO> findOrderList(@RequestBody FindOrderListParam findOrderListParam){
        PageInfo<OrderDTO> pageInfo = new PageInfo<>(findOrderListParam.getPageNum(), findOrderListParam.getPageSize());
        
        //检索条件  排序
        pageInfo.setTotal(orderMapper.count());
        if(pageInfo.getTotal()>0) {
            List<OrderDTO> orders = orderMapper.findList(pageInfo.getOffset(),pageInfo.getPageSize());
            pageInfo.setItems(orders);
        }
        return pageInfo;
    }
    
    // 供应商
    @PostMapping("/supplier/order/list")
    public PageInfo<SupplierOrderDTO> findSupplierOrderList(@RequestBody FindSupplierOrderListParam findSupplierOrderListParam){
        PageInfo<SupplierOrderDTO> pageInfo = new PageInfo<>(findSupplierOrderListParam.getPageNum(), findSupplierOrderListParam.getPageSize());
        pageInfo.setTotal(orderMapper.supplierCount(findSupplierOrderListParam.getSupplierName()));
        if(pageInfo.getTotal()>0) {
            List<SupplierOrderDTO> orders = orderMapper.findListBySupplierName(findSupplierOrderListParam.getSupplierName(),findSupplierOrderListParam.getPageNum(),findSupplierOrderListParam.getPageSize());
            pageInfo.setItems(orders);
        }
        return pageInfo;
    }
    
    //仓库
    @PostMapping("/warehouse/order/list")
    public PageInfo<WarehouseOrderDTO> findWarehouserOrderList(@RequestBody FindOrderListParam findOrderListParam){
        PageInfo<WarehouseOrderDTO> pageInfo = new PageInfo<>(findOrderListParam.getPageNum(), findOrderListParam.getPageSize());
        pageInfo.setTotal(orderMapper.count());
        if(pageInfo.getTotal()>0) {
            List<WarehouseOrderDTO> orders = orderMapper.findWarehouseList(pageInfo.getOffset(),pageInfo.getPageSize());
            pageInfo.setItems(orders);
        }
        return pageInfo;
    }
    
    
    
}
