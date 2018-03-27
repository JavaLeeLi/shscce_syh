package com.visionet.syh_mall.common.utils;

import java.util.Date;
import java.util.UUID;

/**
 * 系统生成唯一序列号工具类
 * @author xiaofb
 * @time 2017年10月13日
 */
public class SequenceUUID {
	
	
	/**
	 * 生产唯一订18位单号
	 * @param type 类型   B:交易订单   S:服务订单
	 *
	 * @return
	 */
	public static String getOrderIdByUUId(String type) {
		String date = DateUtil.convertToString(new Date(),DateUtil.YMD3);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0     
        // 4 代表长度为4     
        // d 代表参数为正数型
        return  date + String.format("%010d", hashCodeV) + type;
    }
}
