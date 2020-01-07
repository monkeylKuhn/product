package com.simple.productInfo.task;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.simple.productInfo.model.DressProduct;
import com.simple.productInfo.model.DressResult;

public class Test {

	public static void main(String[] args) {
//		HttpClient httpclient = HttpClients.createDefault();

//		try {
//			URIBuilder builder = new URIBuilder(
//					"https://api.dresscode.cloud/channels/v2/api/feeds/en/clients/llf/products?channelKey=0198873e-1fde-4783-8719-4f1d0790eb6e");
//
//			URI uri = builder.build();
//			HttpGet request = new HttpGet(uri);
//			request.setHeader("Ocp-Apim-Subscription-Key", "107b04efec074c6f8f8abed90d224802");
//
//
//			HttpResponse response = httpclient.execute(request);
//			HttpEntity entity = response.getEntity();
//
//			if (entity != null) {
//				System.out.println(EntityUtils.toString(entity));
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		String url = "https://api.dresscode.cloud/channels/v2/api/feeds/en/clients/llf/stocks?channelKey=0198873e-1fde-4783-8719-4f1d0790eb6e";
		HashMap<String, String> head = new HashMap<String,String>();
		head.put("Ocp-Apim-Subscription-Key", "107b04efec074c6f8f8abed90d224802");
		try {
			String sendGetRequest = HttpClientUtil.sendGetRequest(url, 15000, head);
			System.out.println(sendGetRequest);
			DressResult result = JSONObject.parseObject(sendGetRequest, DressResult.class);
			
			List<DressProduct> dressProductList = result.getData();
			
			for (DressProduct dressProduct : dressProductList) {
				
				System.err.println(JSONObject.toJSONString(dressProduct));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}