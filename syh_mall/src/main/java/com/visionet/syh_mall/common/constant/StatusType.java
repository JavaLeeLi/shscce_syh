package com.visionet.syh_mall.common.constant;

/**
 * 业务状态类型
 * @author xiaofb
 * @time 2017年8月25日
 */
public interface StatusType {
	
	/*---------------- 商品状态类型 ---------------------*/
	public static final String GOODS_UNDERCARRIAGE = "goods_undercarriage"; //已下架
	public static final String GOODS_GROUNDING = "goods_grounding"; 		//销售中  	
	public static final String GOODS_PENDING = "goods_pending"; 			//待审核
	public static final String GOODS_REFUSE = "goods_refused"; 				//已拒绝
	
	/*---------------- 文件/图片用途 ---------------------*/
	public static final int FILE_USER = 1; 		//用户
	public static final int FILE_SHOP = 2; 		//店铺	
	public static final int FILE_GOODS = 0; 	//商品
	public static final int FILE_PLATFORM = 3; 	//平台
	public static final int FILE_OTHER = 9; 	//其它
	
}
