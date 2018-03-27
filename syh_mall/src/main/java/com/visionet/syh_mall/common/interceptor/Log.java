package com.visionet.syh_mall.common.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
	String name() default "无操作名称";

	String model() default "无模块名称";
	
	/*String log() default "无日志"; 
	
	String field() default "无字段信息";//日志中需要获取的信息例(字段中文名称,参数类型,属性的字段名)如:"用户帐号,User,user_name"
*/	
}
