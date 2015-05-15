package com.westernstory.api.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtil {
	
	private static Gson gson = new Gson(); 
	
	/**
	 * 对象转字符串
	 * @param object
	 * @return
	 */
	public static String object2json(Object object) {
		return gson.toJson(object);
	}
	/**
	 * 字符串转对象
	 * @param str
	 * @param cls
	 * @return
	 */
	public static Object json2object(String str, Class<?> cls) {
		return  gson.fromJson(str, cls);   
	}
	/**
	 * 复杂对象转Json
	 * @param list
	 * @return
	 */
	public static String tojson(List<?> list, Type type) {
		return gson.toJson(list, type);
	}
	/**
	 * Json转复杂对象
	 * @param json
	 * @param type
	 * @return
	 */
	public static Object fromjson(String json, Type type) {
		return gson.fromJson(json, type);
	}
	
	/**
	 * Json 转 List
	 * @param json
	 * @return
	 */
	public static List<?> json2list(String json, Type type) {
		return  gson.fromJson(json, type);
	}
}
