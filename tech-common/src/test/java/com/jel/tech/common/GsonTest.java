package com.jel.tech.common;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
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

}
