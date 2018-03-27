package com.visionet.syh_mall.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.beans.BeanMap;

public class MapUtils {
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {    
        Class type = bean.getClass();    
        Map returnMap = new HashMap();    
        BeanInfo beanInfo = Introspector.getBeanInfo(type);    
    
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();    
        for (int i = 0; i< propertyDescriptors.length; i++) {    
            PropertyDescriptor descriptor = propertyDescriptors[i];    
            String propertyName = descriptor.getName();    
            if (!propertyName.equals("class")) {    
                Method readMethod = descriptor.getReadMethod();    
                Object result = readMethod.invoke(bean, new Object[0]);    
                if (result != null) {    
                    returnMap.put(propertyName, result);    
                } else {    
                    returnMap.put(propertyName, "");    
                }    
            }    
        }    
        return returnMap;    
    }
	
	   /** 
	 * 将对象装换为map 
	 * @param bean 
	 * @return 
	 */  
	public static <T> Map<String, Object> beanToMap(T bean) {  
	    Map<String, Object> map = new HashMap<String, Object>();
	    if (bean != null) {  
	        BeanMap beanMap = BeanMap.create(bean);  
	        for (Object key : beanMap.keySet()) {  
	            map.put(key+"", beanMap.get(key));  
	        }             
	    }  
	    return map;  
	}  
	
	/** 
	 * 将map装换为javabean对象 
	 * @param map 
	 * @param bean 
	 * @return 
	 */ 
	public static <T> T mapToBean(Map<String, Object> map,T bean) {  
	    BeanMap beanMap = BeanMap.create(bean);  
	    beanMap.putAll(map);  
	    return bean;  
	}  
}
