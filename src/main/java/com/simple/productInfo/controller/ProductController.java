package com.simple.productInfo.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simple.productInfo.DTO.ExportDTO;
import com.simple.productInfo.DTO.OrderDTO;
import com.simple.productInfo.DTO.SupplierOrderDTO;
import com.simple.productInfo.DTO.WarehouseOrderDTO;
import com.simple.productInfo.controller.param.CreateOrderParam;
import com.simple.productInfo.controller.param.FindOrderListParam;
import com.simple.productInfo.controller.param.FindSupplierOrderListParam;
import com.simple.productInfo.controller.param.SupplierUpdateParam;
import com.simple.productInfo.controller.param.WarehouseUpdateParam;
import com.simple.productInfo.mapper.DressProductMapper;
import com.simple.productInfo.mapper.OrderMapper;
import com.simple.productInfo.model.DressProduct;
import com.simple.productInfo.model.DressResult;
import com.simple.productInfo.model.DressSkuSize;
import com.simple.productInfo.model.ShippingAddress;
import com.simple.productInfo.model.SubmitOrder;
import com.simple.productInfo.task.HttpClientUtil;
import com.simple.productInfo.task.TestStockTask;
import com.simple.productInfo.task.TestTask;
import com.simple.productInfo.utils.EasyPoiUtils;
import com.simple.productInfo.utils.PageInfo;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private DressProductMapper dressProductMapper;

    @Autowired
    TestTask testTask;
    @Autowired
    TestStockTask testStockTask;

    @GetMapping("/spu")
    public Object fetchSpu() {
        testTask.fetchProduct();
        return true;
    }

    @GetMapping("/sku")
    public Object fetchSku() {
        testStockTask.fetchStock();
        return true;
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        List<ExportDTO> skuExport = dressProductMapper.export();
        EasyPoiUtils.exportExcel(skuExport, null, null, ExportDTO.class, "商品信息.xls",
            response);
    }

    @PostMapping("/order")
    public String createOrder(@RequestBody CreateOrderParam createOrderParam) {
        // 检验 TODO

        // 获取商品信息 验证库存 价格是否发生改变
        DressProduct dressProduct = getDressProductBySku(createOrderParam.getSku());
        if (chechSku(dressProduct, createOrderParam)) {
            return "商品信息需要更新";
        }
        // 调用api接口锁商品
        submitOrder(createOrderParam);

        Integer flag = orderMapper.insert(createOrderParam);

        return null;
    }

    private void submitOrder(CreateOrderParam param) {
        SubmitOrder submit = new SubmitOrder();
        submit.setChannelOrderID(param.getOrderNo());
        submit.setChannelOrderCreated(LocalDateTime.now().toString());
        submit.setProductID(param.getSku());
        submit.setSize(param.getSize());
        submit.setSoldUnits(1);
        // 提交的价格 TODO
        // submit.setUnitSellingPrice(unitSellingPrice);
        ShippingAddress shippingAddress = new ShippingAddress();
        BeanUtils.copyProperties(param, shippingAddress);
        submit.setShippingAddress(shippingAddress);

        String url =
            "https://api.dresscode.cloud/channels/v2/api/feeds/en/clients/llf/orders/items?channelKey=channelKey=0198873e-1fde-4783-8719-4f1d0790eb6e";
        HashMap<String, String> head = new HashMap<String, String>();
        head.put("Ocp-Apim-Subscription-Key", "107b04efec074c6f8f8abed90d224802");
        head.put("Content-Type", JSON.toJSONString(submit));
        try {
            String sendGetRequest = HttpClientUtil.sendGetRequest(url, 25000, head);
            // DressResult result = JSONObject.parseObject(sendGetRequest, DressResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验商品
     * 
     * @param dressProduct
     * @param dressProduct2
     * @return
     */
    private boolean chechSku(DressProduct dressProduct, CreateOrderParam createOrderParam) {
        if (!dressProduct.getPrice().equals(createOrderParam.getPrice())
            || !dressProduct.getRetailPrice().equals(createOrderParam.getRetailPrice())) {
            return true;
        }
        DressSkuSize dressSkuSize = new DressSkuSize();
        for (DressSkuSize size : dressProduct.getSizes()) {
            if (size.getSize().equals(createOrderParam.getSize()))
                dressSkuSize = size;
        }
        if (dressSkuSize == null || Integer.parseInt(dressSkuSize.getStock()) == 0) {
            return true;
        }
        return false;
    }

    @PostMapping("/order/list")
    public PageInfo<OrderDTO> findOrderList(@RequestBody FindOrderListParam findOrderListParam) {
        PageInfo<OrderDTO> pageInfo = new PageInfo<>(findOrderListParam.getPageNum(), findOrderListParam.getPageSize());

        // 检索条件 排序
        pageInfo.setTotal(orderMapper.count());
        if (pageInfo.getTotal() > 0) {
            List<OrderDTO> orders = orderMapper.findList(pageInfo.getOffset(), pageInfo.getPageSize());
            pageInfo.setItems(orders);
        }
        return pageInfo;
    }

    // 供应商
    @PostMapping("/supplier/order/list")
    public PageInfo<SupplierOrderDTO>
        findSupplierOrderList(@RequestBody FindSupplierOrderListParam findSupplierOrderListParam) {
        PageInfo<SupplierOrderDTO> pageInfo =
            new PageInfo<>(findSupplierOrderListParam.getPageNum(), findSupplierOrderListParam.getPageSize());
        pageInfo.setTotal(orderMapper.supplierCount(findSupplierOrderListParam.getSupplierName()));
        if (pageInfo.getTotal() > 0) {
            List<SupplierOrderDTO> orders =
                orderMapper.findListBySupplierName(findSupplierOrderListParam.getSupplierName(),
                    findSupplierOrderListParam.getPageNum(), findSupplierOrderListParam.getPageSize());
            pageInfo.setItems(orders);
        }
        return pageInfo;
    }

    // 仓库
    @PostMapping("/warehouse/order/list")
    public PageInfo<WarehouseOrderDTO> findWarehouserOrderList(@RequestBody FindOrderListParam findOrderListParam) {
        PageInfo<WarehouseOrderDTO> pageInfo =
            new PageInfo<>(findOrderListParam.getPageNum(), findOrderListParam.getPageSize());
        pageInfo.setTotal(orderMapper.count());
        if (pageInfo.getTotal() > 0) {
            List<WarehouseOrderDTO> orders =
                orderMapper.findWarehouseList(pageInfo.getOffset(), pageInfo.getPageSize());
            pageInfo.setItems(orders);
        }
        return pageInfo;
    }

    @PostMapping("/supplier")
    public Boolean supplierUpdate(@RequestBody SupplierUpdateParam param) {
        OrderDTO order = new OrderDTO();
        order.setId(param.getId());
        order.setSupplierDeliveryStatus(param.getStatus());
        order.setSupplierDeliveryNo(param.getDeliveryNo());
        if (param.getStatus() == 1) {
            order.setSupplierTime(LocalDateTime.now().plusHours(7L));
        }
        return orderMapper.UpdateOrder(order) > 0;
    }

    @PostMapping("/warehouse")
    public Boolean warehouseUpdate(@RequestBody WarehouseUpdateParam param) {
        OrderDTO order = new OrderDTO();
        order.setId(param.getId());
        order.setWarehouseStatus(param.getStatus());
        order.setWarehouseDeliveryNo(param.getDeliveryNo());
        order.setQuality(param.getQuality());
        if (param.getStatus() == 1) {
            order.setWarehouseTime(LocalDateTime.now().plusHours(7L));
        }
        return orderMapper.UpdateOrder(order) > 0;
    }

    /**
     * 获取对应商品校验
     * 
     * @param productId
     * @return
     */
    private DressProduct getDressProductBySku(String productId) {
        String url = "https://api.dresscode.cloud/channels/v2/api/feeds/en/clients/llf/products/" + productId
            + "?channelKey=0198873e-1fde-4783-8719-4f1d0790eb6e";
        HashMap<String, String> head = new HashMap<String, String>();
        head.put("Ocp-Apim-Subscription-Key", "107b04efec074c6f8f8abed90d224802");
        try {
            String sendGetRequest = HttpClientUtil.sendGetRequest(url, 25000, head);
            DressResult result = JSONObject.parseObject(sendGetRequest, DressResult.class);
            return result.getData().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
