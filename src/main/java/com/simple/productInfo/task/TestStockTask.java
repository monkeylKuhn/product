package com.simple.productInfo.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.simple.productInfo.mapper.DressProductMapper;
import com.simple.productInfo.mapper.DressSkuMapper;
import com.simple.productInfo.model.DressProduct;
import com.simple.productInfo.model.DressSkuSize;
import com.simple.productInfo.model.DressStock;
import com.simple.productInfo.model.DressStockResult;

@Component
public class TestStockTask {
	
	@Autowired
	DressProductMapper dressProductMapper;
	
	@Autowired
	DressSkuMapper dressSkuMapper;

	@Scheduled(cron = "0 0/20 * * * ?")
	public void fetchProduct() {
		int total = 0;
		String url = "https://api.dresscode.cloud/channels/v2/api/feeds/en/clients/llf/stocks?channelKey=0198873e-1fde-4783-8719-4f1d0790eb6e";
		HashMap<String, String> head = new HashMap<String,String>();
		head.put("Ocp-Apim-Subscription-Key", "107b04efec074c6f8f8abed90d224802");
		try {
			String sendGetRequest = HttpClientUtil.sendGetRequest(url, 25000, head);
			DressStockResult result = JSONObject.parseObject(sendGetRequest, DressStockResult.class);
			sendGetRequest = null;
			List<DressStock> dressProductList = result.getData();
			if (CollectionUtils.isEmpty(dressProductList)) {
				return;
			}
			
			// 1 更新中  2库存更新 3价格更新 4均有更新 5未更新且库存不为0  6新插入数据
			// 全部设置状态为 1 更新中
			// 查询当前表是否存在 productID+size
			// 如果存在 比较库存及价格是否一致
			// 一致 -- 不进行操作
			// 不一致 -- 更新并设置不同更新状态
			// 如果不存在，插入，并设置更新状态为 4库存价格均更新
			// 查询处理完后，表中进行了更新的商品  2 3 4状态，返回提示
			// 查询状态1 且库存不为0的商品，返回提示库存更新为0
			// 设置  状态1  既更新中，未被更新操作的商品库存为0 
			
			int updateStatus = dressSkuMapper.updateStatus(1);
			for (DressStock dressStock : dressProductList) {
			    DressSkuSize dressSkuSize = dressSkuMapper.selectByProductIDandSize(dressStock.getProductID(), dressStock.getSize());
			    if (dressSkuSize != null) {
			        String price = dressSkuSize.getPrice();
			        String retailPrice = dressSkuSize.getRetailPrice();
			        String stock = dressSkuSize.getStock();
			        
			        boolean priceE = dressStock.getPrice().equals(price);
			        boolean retailPriceE = dressStock.getRetailPrice().equals(retailPrice);
			        boolean stockE = dressStock.getStock().equals(stock);
			        
			        if (stockE&&priceE&&retailPriceE) {
                        // 数据都一致,更新状态为5
			            dressSkuMapper.updateStatusById(5, dressSkuSize.getId());
			        }else {
			            int status = 4;
			            boolean flag = stockE && (priceE||retailPriceE);
			            if (!flag) {
			                status = stockE ? 3 : 2;
                        }
			            dressSkuSize.setStatus(status);
			            dressSkuSize.setPrice(dressStock.getPrice());
			            dressSkuSize.setRetailPrice(dressStock.getRetailPrice());
			            dressSkuSize.setStock(dressStock.getStock());
			            dressSkuMapper.update(dressSkuSize);
                    }
                }else {
                    // 不存在，插入数据
                    dressSkuMapper.insert(dressStock);
                }
            }
			
			
			
			// 查询有库存更新的   2  3  4
			
			// 查询新插入的  6
			
			// 查询库存要更新为0的    1 and stock>0
			
			// 更新库存为0
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}