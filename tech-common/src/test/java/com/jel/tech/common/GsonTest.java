package com.jel.tech.common;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jel.tech.common.json.JsonUtils;
import com.jel.tech.common.model.Point;

public class GsonTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Map<String, Point> map = new HashMap<String,Point>();
		map.put("start", new Point(1.0,5.0));
		map.put("end", new Point(3.0,9.0));
		map.put("circle", new Point(2.0,6.0));
		
		String json = JsonUtils.toJson(map);
		System.out.println(json);
		
		Type typeOfSrc = new TypeToken<Map<String,Point>>(){}.getType();
		Map<String,Point> retMap = JsonUtils.fromJson(json, typeOfSrc );
		
		System.out.println(retMap);
		System.out.println(MapUtils.getObject(retMap, "circle"));
		System.out.println(retMap.size());
	}
	
	/**
	 * json from and to list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void test2() {
		
		List<Point> list = new ArrayList<>();
		list.add(new Point(1.0,3.0));
		list.add(new Point(1.0,4.0));
		list.add(new Point(1.0,5.0));
		
		String json = JsonUtils.toJson(list);
		System.out.println(json);
		//warning:这样转换的结果内部为LinkedHashMap，须使用下面方法转换list
		List list2 = JsonUtils.fromJson(json, List.class);
		System.out.println(list2);
		list2.stream().forEach(l->System.out.println(l));
		
		List<Point> list3 = JsonUtils.fromJson(json, new TypeToken<List<Point>>(){}.getType());
		System.out.println(list3);
		list3.stream().forEach(l->System.out.println(l));
	}
	
	/**
	 * 测试gson解析json字符串
	 */
	@Test
	public void test3() {
		String jsonStr = "{\"start\":{\"x\":1.0,\"y\":5.0},\"end\":{\"x\":3.0,\"y\":9.0},\"circle\":{\"x\":2.0,\"y\":6.0}}";
		JsonElement jsonElement = new JsonParser().parse(jsonStr);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		System.out.println(jsonElement.toString());
		System.out.println(jsonObject.toString());
		JsonElement jsonElement2 = jsonObject.get("end");
		System.out.println(jsonElement2.toString());
		double d = jsonElement2.getAsJsonObject().get("x").getAsDouble();
		System.out.println(d);
		
		System.out.println("---><=======");
		
		Iterator<Entry<String, JsonElement>> iterator = jsonObject.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, JsonElement> next = iterator.next();
			String key = next.getKey();
			/*
			 * 下面这两行任一种方法都行
			 */
			JsonObject object = jsonObject.getAsJsonObject(key);
//			JsonObject object = next.getValue().getAsJsonObject();
			
			System.out.println(key + "=" + object.toString());
			System.out.println("object["+object.get("x").getAsDouble()+","+object.get("y").getAsDouble()+"]");
		}
		
	}
	
	@Test
	public void test4() {
		String jsonStr = "[{\"x\":1.0,\"y\":3.0},{\"x\":1.0,\"y\":4.0},{\"x\":1.0,\"y\":5.0}]";
		JsonElement jsonElement = new JsonParser().parse(jsonStr);
		JsonArray jsonArray = jsonElement.getAsJsonArray();
		System.out.println(jsonElement);
		System.out.println(jsonArray);
		JsonObject jsonObject = jsonArray.get(2).getAsJsonObject();
		System.out.println(jsonObject);
		double y = jsonObject.get("y").getAsDouble();
		System.out.println(y);
		System.out.println("hhhhh---------");
		
		/*for(JsonElement ele : jsonArray) {
			JsonObject object = ele.getAsJsonObject();
			double x = object.get("x").getAsDouble();
			y = object.get("y").getAsDouble();
			System.out.println(x + ":" + y);
		}*/
		
		jsonArray.forEach(ele->System.out.println(ele));
	}
	
}

	