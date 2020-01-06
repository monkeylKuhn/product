 package com.simple.productInfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple.productInfo.mapper.DressProductMapper;
import com.simple.productInfo.model.DressProduct;
import com.simple.productInfo.task.TestTask;

@RestController
@RequestMapping("/query")
 public class productController {

    @Autowired
    private DressProductMapper dressProductMapper;
    
    @Autowired
    TestTask testTask;
    
//    @Autowired
//    private RoleMapper roleMapper;
    
    @GetMapping("/")
    public Object queryById(){
        
//        String url = "https://api.dresscode.cloud/channels/v2/api/feeds/en/clients/llf/products?channelKey=0198873e-1fde-4783-8719-4f1d0790eb6e";
//        HashMap<String, String> head = new HashMap<String,String>();
//        head.put("Ocp-Apim-Subscription-Key", "107b04efec074c6f8f8abed90d224802");
//        try {
//            String sendGetRequest = HttpClientUtil.sendGetRequest(url, 15000, head);
//            System.out.println(sendGetRequest);
//            DressResult result = JSONObject.parseObject(sendGetRequest, DressResult.class);
//            
//            List<DressProduct> dressProductList = result.getData();
//            Integer num = dressProductMapper.insertProduct(dressProductList);
//            System.out.println(num);
//            
//            for (DressProduct dressProduct : dressProductList) {
//                
//                System.err.println(JSONObject.toJSONString(dressProduct));
//            }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
//        List<DressProduct> dressProductList = new ArrayList<DressProduct>();
//        DressProduct d1 = new DressProduct();
//        d1.setProductID("123");
//        dressProductList.add(d1);
//        DressProduct d2 = new DressProduct();
//        dressProductList.add(d2);
//        d2.setProductID("1234");
//        DressProduct d3 = new DressProduct();
//        dressProductList.add(d3);
//        d3.setProductID("12345");
//        DressProduct d4 = new DressProduct();
//        dressProductList.add(d4);
//        d4.setProductID("123456");
//        System.out.println(d1);
////        int num = 
//            int insertProduct = dressProductMapper.insertProduct(d1);
//        System.out.println(insertProduct);
//        System.out.println(dressProductList);
       // System.out.println(dressProductMapper.select());
//        System.out.println(roleMapper.insert());
    	testTask.fetchProduct();
        return true;
    }
    
}
