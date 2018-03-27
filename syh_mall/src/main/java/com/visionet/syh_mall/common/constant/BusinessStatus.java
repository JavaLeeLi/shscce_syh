package com.visionet.syh_mall.common.constant;

/**
 * 响应状态码和描述
 * @author xiaofb
 * @time 2017年9月17日
 */
public enum BusinessStatus {
	
	OK("10000","成功"),
	SESSION_TIME_OUT("10001","您的会话已超时，请刷新后重试！"),
	LANDING_FAILURE("10002","您的登录已失效，请重新登录！"),
	NO_PRIVILEGED_USER("10003","您无此操作权限！"),
	NO_ACCESS_MANAGER("10004","您无此操作权限！"),
	USER_NOTEXIST("10005","该用户不存在，请确认用户名有误！"),
	PWD_ERROR("10006","您的密码有误，请重新输入！"),
	UNCERTIFIED_MEMBER("10007","请您先提交个人/企业信息至平台认证！"),
	BLACKLIST_MEMBER("10008","您的账号存在异常，请联系平台管理员！"),
	FROZEN_SHOP("10009","您的店铺已冻结，请联系平台管理员！"),
	NO_ACCESS("10010","禁止访问！"),
	PARAME_LENGTH_OVER("10011","您输入的内容长度超出平台限制！"),
	CONTENT_QUANTITY_OVER("10012","您增加的内容数量已超出平台限制！"),
	NULL_LIMIT("10013","您输入的内容不完整，请确认必填项已填写！"),
	NAME_REPETITION("10014","您输入的名称已存在！"),
	RESOURCES_ERROR("10015","您访问的资源不存在！"),
	AUCTION_OVER("10016","该拍卖已结束！"),
	PURCHASE_OVER("10017","该求购已结束！"),
	PAY_EXCEPTION("10018","支付平台异常"),
	LICENSE_FAILED("10019","授权失败，请重新尝试！"),
	MOBILE_VERIFI_FAILED("10020","验证手机失败，请确认手机号与验证码！"),
	VALIDATION_EXPIRED("10021","您的短信验证码已过期，请重新获取！"),
	UNLOGIN("10022","请先登录"),
	DATE_ERROR("10023","数据有误"),
	UNDERSTOCK("10100","库存不足！"),
	SUPPLY_BEYOND_DEMAND("10101","供应量超出需求"),
	SUPPLY_UNDER_MINDEMAND("10102","供应量小于最低供应数"),
	DEMAND_OVER("10103","需求量已满"),
	SUPPLY_BEYOND_RESIDUE_DEMAND("10104","供货量大于剩余需求量"),
	ORDER_NOT_END("10105","订单未完成"),
	NOT_PAY_ORDER("10106","未支付的订单"),
	ORDER_IN_DISPOSE("10107","订单正在处理"),
	ORDER_LOSE_EFFICACY("10108","订单失效"),
	ORDER_SNAPSHOT_SAVE_LOSE("10109","快照保存失败"),
	AUCTION_NOT_START("10110","该拍卖未开始！"),
	ADDRESS_NOT_NULL("10111","地址不能为空"),
	INTERFACE_ERROR("10112","第三方接口调用失败"),
	APPLY_REFUND_ERROR("10113","请勿重复申请"),
	ATTENTION_ERROR("10114","无法关注自身店铺"),
	FAVORITE_ERROR("10115","无法收藏自己商品"),
	BUY_DATE_ERROR("10116","请填写求购截止时间"),
	AUCTION_DATE_ERROR("10117","请完善拍卖时间"),
	CANT_USE_COUPON("10118","不满足优惠券使用条件"),
	GROUP_ERROR("10119","您已参团"),
	DISCOUNTTIME_ERROR("10120","超过购买限制"),
	FULFILREMIT_ERROR("10121","该价位已存在满减满送活动"),
	AUCTION_ERROR("10122","请勿对自己的商品进行加价！"),
	AUCTION1_ERROR("10123","当前出价不是最高价请刷新页面"),
	AUCTION2_ERROR("10124","账户可用余额不足"),
	CREATE_MBR_ERROR("10125","第三方会员注册失败"),
	AUCTION_UPDATE_ERROR("10126","竞拍中的商品禁止修改"),
	GOODS_UNDERCARRIAGE("10127","商品已下架，禁止购买"),
	GROUP_USER_ERROR("10128","超出团购人数限制"),
	AUCTION_DATE_ERROR2("10129","拍卖开始时间必须是将来的时间！"),
	AUCTION_DATE_ERROR3("10130","拍卖开始时间超出允许的最长时间："),
	AUCTION_DATE_ERROR4("10131","拍卖的进行时间超出可允许的最长时间："),
	AUCTION_DATE_ERROR5("10134","拍卖的结束时间不能小于开始时间"),
	PURCHASE_DATE_ERROR2("10132","求购结束时间必须是将来的时间！"),
	PURCHASE_DATE_ERROR3("10133","求购结束时间超出允许的最长时间："),
	GROUP_BUY_ERROR("10134","该商品已存在团购"),
	BUY_GOODS_ERROR("10135","求购商品需求量不足"),
	NO_EVALUATION_ERROR("10136","鉴评结果不存在"),
	THREE_SPACE_ERROR("10137","第三方接口调用失败_企业个人银行卡不允许解绑"),
	USER_NO_SHOP("10200","会员没有店铺"),
	SHOP_NO_REVIEW("10201","店铺不正常"),
	SHOP_NO_RENZHENG("10204","店铺没有认证"),
	SHOP_DONGJIE("10205","店铺被冻结"),
	SHOP_EMPLOREY("10202","店铺客服没传雇主ID,或者其他客服传了ID"),
	SMS_FAIL("10203","短信发送失败"),
	USER_LAHEI("10301","用户已被拉黑!"),
	USER_NAME("10302","用户昵称已存在"),
	UNKNOWN_ERROR("19999","服务器忙"),
	;
	
	//SUCCESS
	public static final String SUCCESS = "SUCCESS";
	//true
    public static final String RESULT_TRUE = "true";
    //false
    public static final String RESULT_FALSE = "false";
    
    //描述
    private final String desc;
    public String getDesc() {
		return desc;
	}
    //编码
    private final String code;
	public String getCode() {
		return code;
	}
	private BusinessStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	/**
	 * 根据code获取desc
	 * @param code
	 * @return
	 */
	public static String getDescByCode(String code) {  
		BusinessStatus[] enums = BusinessStatus.values();  
        for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getCode().equals(code)) {  
                return enums[i].getDesc();  
            }  
        }  
        return "";  
    } 
	
	/**
	 * 根据desc获取code
	 * @param desc
	 * @return
	 */
	public static String getCodeByDesc(String desc) {  
		BusinessStatus[] enums = BusinessStatus.values();  
        for (int i = 0; i < enums.length; i++) {  
            if(enums[i].getDesc().equals(desc)){  
                return enums[i].getCode();  
            }  
        }  
        return "";  
    } 
	
}
