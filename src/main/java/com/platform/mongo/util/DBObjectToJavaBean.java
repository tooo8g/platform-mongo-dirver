package com.platform.mongo.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.bson.Document;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class DBObjectToJavaBean {
	
	 public static <T> Document beanToDBObject(T bean)
	            throws IllegalArgumentException, IllegalAccessException {
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
	            } else if (param instanceof Integer) {
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
	            }
	            // 恢复访问控制权限
	            field.setAccessible(accessFlag);
	        }
	        return dbObject;
	    }
	
	 /**
	     * 将DBObject转换成Bean对象
	     * 
	     */
	    public static <T> T dbObjectToBean(Document dbObject, T bean)
	            throws IllegalAccessException, InvocationTargetException,
	            NoSuchMethodException {
	        if (bean == null) {
	            return null;
	        }
	        Field[] fields = bean.getClass().getDeclaredFields();
	        for (Field field : fields) {
	            String varName = field.getName();
	            Object object = dbObject.get(varName);
	            if (object != null) {
	                BeanUtils.setProperty(bean, varName, object);
	            }

	        }
	        return bean;
	    }

	    // 取出Mongo中的属性值，为bean赋值
	    public static <T> void setProperty(T bean, String varName, T object) {
	        varName = varName.substring(0, 1).toUpperCase() + varName.substring(1);
	        try {
	            String type = object.getClass().getName();
	            // 类型为String
	            if (type.equals("java.lang.String")) {
	                Method m = bean.getClass().getMethod("get" + varName);
	                String value = (String) m.invoke(bean);
	                if (value == null) {
	                    m = bean.getClass()
	                            .getMethod("set" + varName, String.class);
	                    m.invoke(bean, object);
	                }
	            }
	            // 类型为Integer
	            if (type.equals("java.lang.Integer")) {
	                Method m = bean.getClass().getMethod("get" + varName);
	                String value = (String) m.invoke(bean);
	                if (value == null) {
	                    m = bean.getClass().getMethod("set" + varName,
	                            Integer.class);
	                    m.invoke(bean, object);
	                }
	            }
	            // 类型为Boolean
	            if (type.equals("java.lang.Boolean")) {
	                Method m = bean.getClass().getMethod("get" + varName);
	                String value = (String) m.invoke(bean);
	                if (value == null) {
	                    m = bean.getClass().getMethod("set" + varName,
	                            Boolean.class);
	                    m.invoke(bean, object);
	                }
	            }
	        } catch (NoSuchMethodException e) {
	            e.printStackTrace();
	        } catch (SecurityException e) {
	            e.printStackTrace();
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        } catch (IllegalArgumentException e) {
	            e.printStackTrace();
	        } catch (InvocationTargetException e) {
	            e.printStackTrace();
	        }
	    }
	
	public static <T> List<T> cursorToList(DBCursor cursor, Class<T> clazz)
			throws Exception {
		List<T> list = new ArrayList<T>();
		while (cursor.hasNext()) {
			Document dbObj = (Document) cursor.next();
			T t = propertySetter(dbObj, clazz);
			list.add(t);
		}
		return list;
	}

	public static <T> T propertySetter(Document dbObj, Class<T> clazz)
			throws Exception {
		if (dbObj == null)
			return null;
		T t = clazz.newInstance();
		recyleSetter(dbObj, t);
		return t;
	}

	/***
	 * 递归所有属性
	 * 
	 * @param dbObj
	 * @param bean
	 * @throws Exception
	 */
	private static void recyleSetter(Document dbObj, Object bean) throws Exception {
		Iterator<String> it = dbObj.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object value = dbObj.get(key);
			recyleValueJutisy(key, value, bean);
		}
	}

	@SuppressWarnings("rawtypes")
	private static void recyleValueJutisy(String key, Object value, Object bean)
			throws Exception {
		if (value instanceof BasicDBList) {
			BasicDBList dblist = (BasicDBList) value;
			Iterator<Object> it = dblist.iterator();
			Class<?> type = null;
			try {
				type = getPropertyType(bean, key);
				List list = new ArrayList();
				while (it.hasNext()) {
					Object object = it.next();
					if (object instanceof Document) {
						Document dbItem = (Document) object;
						Object o = type.newInstance();
						recyleSetter(dbItem, o);
						list.add(o);
					} else if (object instanceof String) {
						list.add(object);
					}
				}
				BeanUtils.setProperty(bean, key, list);
			} catch (NoSuchFieldException e) {
			}

		} else if (value instanceof Document) {
			Document dbItem = (Document) value;
			Class<?> type = getPropertyType(bean, key);
			Class tclazz = Timestamp.class;
			if (type == tclazz) {
				// 时间类型
				Object otime = dbItem.get("time");
				if (otime != null) {
					long time = Long.parseLong(String.valueOf(otime));
					Timestamp st = new Timestamp(time);
					BeanUtils.setProperty(bean, key, st);
				}
			} else {
				Object o = type.newInstance();
				recyleSetter(dbItem, o);
				BeanUtils.setProperty(bean, key, o);
			}
		} else {
			Class<?> clazz = bean.getClass();
			Field field;
			try {
				field = clazz.getDeclaredField(key);
				if (field != null) {
					if (value != null) {
						if ("".equals(value)) {
							if (field.getType() == String.class)
								BeanUtils.setProperty(bean, key, value);
						} else {
								BeanUtils.setProperty(bean, key, value);
						}
					}

				}
			} catch (NoSuchFieldException e) {
			}

		}
	}

	/**
	 * @param bean
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static Class getPropertyType(Object bean, String key) throws Exception {
		Class<?> clazz = bean.getClass();
		Field f = clazz.getDeclaredField(key);
		Class t = f.getType();
		if (t.getName().startsWith("java.lang")) {
			return t;
		}
		if (t.isAssignableFrom(List.class) || t.isAssignableFrom(Set.class)
				|| t.isAssignableFrom(Vector.class)) {
			Type gt = f.getGenericType();
			if (gt == null) {
				return t;
			}
			if (gt instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) gt;
				Class genericClazz = (Class) pt.getActualTypeArguments()[0];
				if (genericClazz != null) {
					return genericClazz;
				}
			}
		}
		return t;
	}
}