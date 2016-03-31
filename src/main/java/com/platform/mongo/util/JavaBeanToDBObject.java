package com.platform.mongo.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

public class JavaBeanToDBObject {

	/**
	 * 将实体Bean对象转换成DBObject
	 * 
	 */
	public static <T> Document beanToDBObject(T bean) throws IllegalArgumentException, IllegalAccessException {
		if (bean == null)
			return null;
		Document dbObject = new Document();
		// 获取对象类的属性域
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			// 获取变量的属性名
			String varName = field.getName();
			// 修改访问控制权限
			boolean accessFlag = field.isAccessible();
			if (!accessFlag) {
				field.setAccessible(true);
			}
			Object param = field.get(bean);
			if (param == null) {
				continue;
			}else if(param instanceof ObjectId){
				ObjectId value = (ObjectId) param;
				dbObject.put(varName, value);
			}else if (param instanceof Integer) {
				// 判断变量的类型
				int value = ((Integer) param).intValue();
				dbObject.put(varName, value);
			} else if (param instanceof String) {
				String value = (String) param;
				dbObject.put(varName, value);
			} else if (param instanceof Double) {
				double value = ((Double) param).doubleValue();
				dbObject.put(varName, value);
			} else if (param instanceof Float) {
				float value = ((Float) param).floatValue();
				dbObject.put(varName, value);
			} else if (param instanceof Long) {
				long value = ((Long) param).longValue();
				dbObject.put(varName, value);
			} else if (param instanceof Boolean) {
				boolean value = ((Boolean) param).booleanValue();
				dbObject.put(varName, value);
			} else if (param instanceof Date) {
				Date value = (Date) param;
				dbObject.put(varName, value);
			} else if (param instanceof List) {
				List l = (List) param;
				if (l.isEmpty()||!l.get(0).getClass().getName().startsWith("java.lang")) {
					List<Document> value = new ArrayList<Document>();
					for (Object obj : l) {
						Document d = null;
						d = beanToDBObject(obj);
						value.add(d);
					}
					dbObject.put(varName, value);
				} else {
					List<Object> value = new ArrayList<Object>();
					for (Object obj : l) {
						value.add(obj);
					}
					dbObject.put(varName, value);
				}
			} else {
				Document value = beanToDBObject(param);
				dbObject.put(varName, value);
			}
			// 恢复访问控制权限
			field.setAccessible(accessFlag);
		}
		return dbObject;
	}

}
