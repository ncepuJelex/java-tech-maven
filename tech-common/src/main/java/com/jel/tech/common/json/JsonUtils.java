package com.jel.tech.common.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON 工具类
 * @author Jelex
 *
 *	if you want to parse the json String without using the relevant JavaBean Object,
 *	maybe JsonObject and JsonArray are your choice,move to the GsonTest#test3()
 *	and GsonTest#test4(),which are two simple examples as how to parse the json string
 *	in another way.
 */
public class JsonUtils {

	private static final Gson gsonWithPrettyPrinting = new GsonBuilder().serializeNulls().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private static final Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	private JsonUtils() {}
	
	public static <E> String toJson(E obj) {
		return gson.toJson(obj);
	}
	
	public static <E> String toJsonWithPrettyPrint(E obj) {
		return gsonWithPrettyPrinting.toJson(obj);
	}
	
	/**
	 * note:you can build the typeOfSrc in this way:
	 * 	Type fooType = new TypeToken<Foo<Bar>>() {}.getType();
	 * 	where class Bar means the generic class type.
	 * Actually,the toJson with one param is sufficient.
	 * 
	 * @param obj:the generic class
	 * @param typeOfSrc
	 * @return
	 */
	public static <E> String toJson(E obj,Type typeOfSrc) {
			return gson.toJson(obj,typeOfSrc);
	}
	
	public static <E> E fromJson(String jsonStr,Class<E> clazz) {
		return gson.fromJson(jsonStr, clazz);
	}
	
	/**
	 * 可从json格式转换为javabean,Map,List,for example:
	 * @param jsonStr
	 * @param typeOfSrc
	 * @return
	 * 
	 *An sexample:
	 public class Point {
		private double x;
		private double y;
	 }
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
		
		and the total result would be like this:
		
			{"start":{"x":1.0,"y":5.0},"end":{"x":3.0,"y":9.0},"circle":{"x":2.0,"y":6.0}}
			{start=Point [x=1.0, y=5.0], end=Point [x=3.0, y=9.0], circle=Point [x=2.0, y=6.0]}
			Point [x=2.0, y=6.0]
			3
	 */
	public static <E> E fromJson(String jsonStr,Type typeOfSrc) {
		return gson.fromJson(jsonStr, typeOfSrc);
	}
	
}
