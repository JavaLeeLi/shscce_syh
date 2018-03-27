package com.visionet.syh_mall.common.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.vo.goods.SearchEvaluationVo;
import com.visionet.syh_mall.vo.shop.ShopQo;

/**
 * 参数装换
 * 
 * @author xiaofb
 * @time 2017年8月28日
 */
public class DynamicParamConvert {

	/**
	 * @Title: commissionSettlement @Description: 佣金列表 @param @param
	 *         param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> commissionList(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("commissionTallyID"))) {
			map.put("EQ_id", param.get("commissionTallyID"));
		}
        // 创建开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_createTime", startTime);
		}
		// 创建结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_createTime", endTime);
		}

		if (!StringUtils.isEmpty(param.get("userNo"))) {
			map.put("LIKE_userId", param.get("userNo"));
		}
		if (!StringUtils.isEmpty(param.get("commissionStatusCode"))) {
			map.put("EQ_commissionStatusCode", param.get("commissionStatusCode"));
		}
		return map;
	}
	
	/**
	 * 鉴评结果检索
	 * @param 
	 * @return Map<String,Object>
	 * @throws
	 */
	public static Map<String,Object> searchEvaluation(SearchEvaluationVo evaluationVo){
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(evaluationVo.getAcceptance())){
			map.put("LIKE_acceptance", evaluationVo.getAcceptance());
		}
		if(!StringUtils.isEmpty(evaluationVo.getCollectEvaluationCode())){
			map.put("LIKE_collectEvaluationCode", evaluationVo.getCollectEvaluationCode());
		}
		if(!StringUtils.isEmpty(evaluationVo.getCollectName())){
			map.put("LIKE_collectName", evaluationVo.getCollectName());
		}
		if(!StringUtils.isEmpty(evaluationVo.getCollectTypeCode())){
			map.put("LIKE_collectTypeCode", evaluationVo.getCollectTypeCode());
		}
		if(!StringUtils.isEmpty(evaluationVo.getDivision())){
			map.put("LIKE_evaluationDivision", evaluationVo.getDivision());
		}
		if(!StringUtils.isEmpty(evaluationVo.getEvaluationEndTime())){
			map.put("LTE_createTime", evaluationVo.getEvaluationEndTime());
		}
		if(!StringUtils.isEmpty(evaluationVo.getEvaluationStartTime())){
			map.put("GTE_createTime", evaluationVo.getEvaluationStartTime());
		}
		if(!StringUtils.isEmpty(evaluationVo.getEvaluationTypeCode())){
			map.put("LIKE_evaluationTypeCode", evaluationVo.getEvaluationTypeCode());
		}
		if(!StringUtils.isEmpty(evaluationVo.getFactorCode())){
			map.put("LIKE_factorCode", evaluationVo.getFactorCode());
		}
		if(!StringUtils.isEmpty(evaluationVo.getFirstEvaluation())){
			map.put("EQ_firstEvaluation", evaluationVo.getFirstEvaluation());
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * @Title: getSearchRecognize @Description: 搜索鉴评结果 @param @param
	 *         param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> getSearchRecognize(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 客户id
		if (!StringUtils.isEmpty(param.get("ids"))) {
			map.put("IN_ownerId", param.get("ids"));
		}
		// 商品名称关键字
		if (!StringUtils.isEmpty(param.get("goodsName"))) {
			map.put("LIKE_goodsName", param.get("goodsName"));
		}
		// 鉴评码关键字
		if (!StringUtils.isEmpty(param.get("recognizeCode"))) {
			map.put("LIKE_recognizedCode", param.get("recognizeCode"));
		}
		map.put("EQ_isOfficialRecognized", 1);
		return map;
	}

	/**
	 * @Title: searchChildMbrs @Description: 搜索下级分销会员 @param @param
	 *         param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> searchChildMbrs(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("userIds"))) {
			map.put("IN_id", param.get("userIds"));
		}
		if (!StringUtils.isEmpty(param.get("userId"))) {
			map.put("IN_id", param.get("userId"));
		}
		// 会员名
		if ((String) param.get("userName") != "") {
			map.put("LIKE_aliasName", param.get("userName"));
		}
		if (!StringUtils.isEmpty(param.get("userTypeCode"))) {
			map.put("EQ_userTypeCode", param.get("userTypeCode"));
		}
		// 创建开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_createTime", startTime);
		}
		// 创建结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_createTime", endTime);
		}
		// 未删除
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * 搜索分销会员 @param @return Map<String,Object> @throws
	 */
	public static Map<String, Object> searchChannel(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("mbrName"))){
			map.put("LIKE_loginName", param.get("mbrName"));
		}
		if(!StringUtils.isEmpty(param.get("userId"))){
			map.put("EQ_id", param.get("userId"));
		}
		if(!StringUtils.isEmpty(param.get("mbrWechatName"))){
			map.put("LIKE_aliasName", param.get("mbrName"));
		}
		if(!StringUtils.isEmpty(param.get("mbrPhone"))){
			map.put("LIKE_phone", param.get("mbrName"));
		}
		// 订单流水号
		if (!StringUtils.isEmpty(param.get("buyOrderNo"))) {
			map.put("EQ_buyOrderNo", param.get("buyOrderNo"));
		}
		// 买家用户账号
		if (!StringUtils.isEmpty(param.get("buyUserAccount"))) {
			map.put("EQ_buyUserAccount", param.get("buyUserAccount"));
		}
		if (!StringUtils.isEmpty(param.get("buyUserName"))) {
			map.put("EQ_buyUserName", param.get("buyUserName"));
		}
		//买家父级买家用户账号
		if (!StringUtils.isEmpty(param.get("buyFatherUserAccount"))) {
			map.put("EQ_buyFatherUserAccount", param.get("buyFatherUserAccount"));
		}
		
		if (!StringUtils.isEmpty(param.get("buyFatherUserName"))) {
			map.put("EQ_buyFatherUserName", param.get("buyFatherUserName"));
		}
		
		//买家祖父级账号
		if (!StringUtils.isEmpty(param.get("buyGrandFatherUserAccount"))) {
			map.put("EQ_buyGrandFatherUserAccount", param.get("buyGrandFatherUserAccount"));
		}
		if (!StringUtils.isEmpty(param.get("buyGrandFatherUserName"))) {
			map.put("EQ_buyGrandFatherUserName", param.get("buyGrandFatherUserName"));
		}
		
		// 卖家用户账号
		if (!StringUtils.isEmpty(param.get("sellUserAccount"))) {
			map.put("EQ_sellUserAccount", param.get("sellUserAccount"));
		}
		if (!StringUtils.isEmpty(param.get("sellUserName"))) {
			map.put("EQ_sellUserName", param.get("sellUserName"));
		}
		//卖家父级买家用户账号
		if (!StringUtils.isEmpty(param.get("sellFatherUserAccount"))) {
			map.put("EQ_sellFatherUserAccount", param.get("sellFatherUserAccount"));
		}
		if (!StringUtils.isEmpty(param.get("sellFatherUserName"))) {
			map.put("EQ_sellFatherUserName", param.get("sellFatherUserName"));
		}
		//卖家祖父级账号
		if (!StringUtils.isEmpty(param.get("sellGrandFatherUserAccount"))) {
			map.put("EQ_sellGrandFatherUserAccount", param.get("sellGrandFatherUserAccount"));
		}
		if (!StringUtils.isEmpty(param.get("sellGrandFatherUserName"))) {
			map.put("EQ_sellGrandFatherUserName", param.get("sellGrandFatherUserName"));
		}
		
		// 创建开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_createTime", startTime);
		}
		// 创建结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_createTime", endTime);
		}
		return map;
	}

	/**
	 * @Title: searchMbrIntegral @Description: 查询会员积分 @param @param
	 *         param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> searchMbrIntegral(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 用户昵称关键字
		if (!StringUtils.isEmpty(param.get("mbrName"))) {
			map.put("LIKE_loginName", param.get("mbrName"));
		}
		// 创建开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_createTime", startTime);
		}
		// 创建结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_createTime", endTime);
		}
		// 未删除
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * 
	 * @Title: getSyhserviceList @Description: 按条件查询服务内容 @param @param
	 *         syhservice @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> getSyhserviceList(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 服务类型
		if (!StringUtils.isEmpty(param.get("serviceTypeCode"))) {
			map.put("EQ_serviceTypeCode", param.get("serviceTypeCode"));
		}
		// 服务介绍类型
		if (!StringUtils.isEmpty(param.get("serviceDescType"))) {
			map.put("EQ_serviceDescType", param.get("serviceDescType"));
		}
		// 是否删除
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * 取得下级的销售额 @param @return Map<String,Object> @throws
	 */
	public static Map<String, Object> getSonChannelTrade(String Id, Object startTime, Object endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 分销用户ID
		if (!StringUtils.isEmpty(Id)) {
			map.put("IN_retailUserId", Id);
		}
		// 创建开始时间
		if (!StringUtils.isEmpty(startTime)) {
			map.put("GTE_createTime", startTime);
		}
		// 创建结束时间
		if (!StringUtils.isEmpty(endTime)) {
			map.put("LTE_createTime", DateUtil.seekDate(new Date((Long) endTime), 1));
		}
		// 已结算的流水
		map.put("EQ_settlementStatus", "commission_accepted");
		// 分销业务类型（0:会员分销，1：商品分销）
		map.put("EQ_retailType", 0);
		// 未删除
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * @Title: searchTags @Description: 搜索标签的方法 @param @param
	 *         param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */

	public static Map<String, Object> searchTags(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询没删除的方法
		map.put("EQ_isDeleted", 0);
		// 标签类型
		if (!StringUtils.isEmpty(param.get("tagType"))) {
			map.put("EQ_tagType", param.get("tagType").toString());
		}
		// 标签名字
		if (!StringUtils.isEmpty(param.get("tagName"))) {
			map.put("LIKE_tagName", param.get("tagName"));
		}
		return map;
	}

	/**
	 * @throws Exception
	 * 
	 * @Title: searchMbr @Description: 搜索会员的方法 @param @param
	 *         param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> searchMbr(Map<String, Object> param) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询没删除的方法
		map.put("EQ_isDeleted", 0);
		// 会员微信昵称
		if ((String) param.get("mbrWechatName") != "") {
			map.put("LIKE_aliasName", param.get("mbrWechatName"));
		}
		// 会员登录
		if ((String) param.get("mbrName") != "") {
			map.put("LIKE_loginName", param.get("mbrName"));
		}
		// 会员电话
		if (!StringUtils.isEmpty(param.get("mbrPhone"))) {
			map.put("LIKE_phone", param.get("mbrPhone"));
		}
		// 会员认证类型编码
		if (!StringUtils.isEmpty(param.get("mbrTypeCode"))) {
			map.put("EQ_userTypeCode", param.get("mbrTypeCode"));
		}
		// 会员标签
		if (!StringUtils.isEmpty(param.get("ids"))) {
			map.put("IN_id", param.get("ids"));
		}
		// 会员状态
		if (!StringUtils.isEmpty(param.get("userStatusCode"))) {
			map.put("EQ_userStatusCode", param.get("userStatusCode"));
		}
		// 创建开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_createTime", startTime);
		}
		// 创建结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_createTime", endTime);
		}
		return map;
	}

	/**
	 * 
	 * @Title: searchReservation @Description: 搜索服务预约的方法 @param @param
	 *         param @param @return @param @throws Exception 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> searchReservation(Map<String, Object> param) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询没删除
		map.put("EQ_isDeleted", 0);
		// 服务类型
		if (!StringUtils.isEmpty(param.get("serviceTypeCode"))) {
			map.put("EQ_serviceTypeCode", param.get("serviceTypeCode"));
		}
		// 服务Id
		if (!StringUtils.isEmpty(param.get("reservationID"))) {
			map.put("EQ_id", param.get("reservationID"));
		}
		// 服务预约关键字
		if (!StringUtils.isEmpty(param.get("customerName"))) {
			map.put("LIKE_reservationName", param.get("customerName"));
		}
		// 服务开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_reservationTime", startTime);
		}
		// 服务结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_reservationTime", endTime);
		}
		return map;
	}

	/**
	 * 
	 * @Title: getAssistants @Description: 客服列表的搜索 @param @param
	 *         param @param @return @param @throws Exception 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> getAssistants(Map<String, Object> param) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询没删除
		map.put("EQ_isDeleted", 0);
		// 客服类型
		if (!StringUtils.isEmpty(param.get("customerServiceType"))) {
			map.put("EQ_customerType", param.get("customerServiceType"));
		}
		//
		if (!StringUtils.isEmpty(param.get("ids"))) {
			map.put("IN_id", param.get("ids"));
		}
		return map;
	}

	/**
	 * @Title: searchCancApplys @Description: 搜索退货申请 @param @param
	 *         param @param @return @param @throws Exception 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> searchCancApplys(Map<String, Object> param) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 订单Ids
		if (!StringUtils.isEmpty(param.get("orderIds")) && StringUtils.isEmpty(param.get("orderId"))) {
			map.put("IN_orderId", param.get("orderIds"));
		}
		if (StringUtils.isEmpty(param.get("orderIds")) && !StringUtils.isEmpty(param.get("orderId"))) {
			map.put("IN_orderId", param.get("orderId"));
		}
		if (!StringUtils.isEmpty(param.get("orderIds")) && !StringUtils.isEmpty(param.get("orderId"))) {
			List<String> orderIds = (List<String>) param.get("orderIds");
			List<String> orderId = (List<String>) param.get("orderId");
			orderIds.retainAll(orderId);
			map.put("IN_orderId", orderIds);
		}
		// 买家ID
		if (!StringUtils.isEmpty(param.get("buyerId"))) {
			map.put("IN_buyerId", param.get("buyerId"));
		}
		// 卖家ID
		if (!StringUtils.isEmpty(param.get("sellerId"))) {
			map.put("IN_sellerId", param.get("sellerId"));
		}
		// 退货申请处理状态编码
		if (!StringUtils.isEmpty(param.get("cancStatusCode"))) {
			map.put("EQ_cancStatusCode", param.get("cancStatusCode"));
		}
		// 申请查询开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_applyTime", startTime);
		}
		// 申请查询结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_applyTime", endTime);
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * 
	 * @Title: searchShops @Description: 搜索店铺的动态查询 @param @param
	 *         param @param @return @param @throws Exception 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> searchShops(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("users"))) {
			map.put("IN_userId", param.get("users"));
		}
		if (!StringUtils.isEmpty(param.get("shopName"))) {
			map.put("LIKE_shopName", param.get("shopName"));
		}
		if (!StringUtils.isEmpty(param.get("shopLevel"))) {
			map.put("EQ_shopLevel", param.get("shopLevel"));
		}
		if (!StringUtils.isEmpty(param.get("shopStatusCode"))) {
			map.put("EQ_statusCode", param.get("shopStatusCode"));
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * 搜索店铺商品分销
	 * @param param
	 * @return
	 */
	public static Map<String, Object> searchGoodsChannel(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("sellUserId"))) {
			map.put("IN_sellUser", param.get("sellUserId"));
		}
		if (!StringUtils.isEmpty(param.get("buyUserId"))) {
			map.put("IN_retailUserId", param.get("buyUserId"));
		}
		// 创建开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_createTime", startTime);
		}
		// 创建结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_createTime", endTime);
		}
		if (!StringUtils.isEmpty(param.get("orderNo"))) {
			map.put("IN_retailObjId", param.get("orderNo"));
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * @Title: getGoodsPromotion @Description: 得到商品分销流水 @param @param
	 * param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> getGoodsPromotion(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("userID"))) {
			map.put("EQ_ownerId", param.get("userID"));
		}
		if (!StringUtils.isEmpty(param.get("goodsName"))) {
			map.put("EQ_goodsName", param.get("goodsName"));
		}
		map.put("EQ_goodsTypeCode", "sale_type");

		map.put("EQ_isDeleted", 0);
		return map;
	}

	public static Map<String, Object> searchChannel(String shopId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(shopId)) {
			map.put("IN_shopId", shopId);
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}

	public static Map<String, Object> searchRetailDetails(Map<String, Object> param) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("userID"))) {
			map.put("EQ_retailUserId", param.get("userID"));
		}
		
		if (!StringUtils.isEmpty(param.get("settlementStatus"))) {
			map.put("EQ_settlementStatus", param.get("settlementStatus"));
		}
		if (!StringUtils.isEmpty(param.get("retailType"))) {
			map.put("EQ_retailType", param.get("retailType"));
		}
		// 创建开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_createTime", startTime);
		}
		// 创建结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_createTime", endTime);
		}
		map.put("EQ_retailType", 0);
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * 获得竞价记录 @param @return void @throws
	 */
	public static Map<String, Object> getBidRecord(String GoodId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_goodsId", GoodId);
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * 搜索公告 @param @return Map<String,Object> @throws
	 */
	public static Map<String, Object> getShowNotice(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("noticeType"))) {
			map.put("EQ_noticeType", param.get("noticeType"));
		}
		if (!StringUtils.isEmpty(param.get("searchKeywords"))) {
			map.put("LIKE_noticeTitle", param.get("searchKeywords"));
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * @Title: NoticeList @Description: 搜索公告列表 @param @param
	 *         param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> NoticeList(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("noticeType"))) {
			map.put("EQ_noticeType", param.get("noticeType"));
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * 搜索会员积分流水 @param String @return Map<String,Object> @throws
	 */
	public static Map<String, Object> getMbrIntegralFlow(String MbrId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(MbrId)) {
			map.put("EQ_userId", MbrId);
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * @Title: search @Description: 搜索所有的未删除 @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> search() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_isDeleted", 0);
		return map;
	}

	public static Map<String, Object> searchBanner(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("versionNo"))) {
			map.put("EQ_versionNo", param.get("versionNo"));
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * 搜索订单 @param @return Map<String,Object> @throws
	 */
	public static Map<String, Object> searchOrders(Order order, List<String> userIds, List<String> shopIds,
												   Date startTime, Date endTime , BigDecimal firstSum ,BigDecimal secondSum) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(order.getBuyerId())) {
			map.put("EQ_buyerId", order.getBuyerId());
		}
		if (!StringUtils.isEmpty(userIds)) {
			map.put("IN_buyerId", userIds);
		}
		if (!StringUtils.isEmpty(order.getFlowNo())) {
			map.put("EQ_flowNo", order.getFlowNo());
		}
		if (!StringUtils.isEmpty(order.getOrderTypeCode())) {
			map.put("EQ_orderTypeCode", order.getOrderTypeCode());
		}
		if (!StringUtils.isEmpty(order.getBuyerPhone())) {
			map.put("LIKE_buyerPhone", order.getBuyerPhone());
		}
		if (!StringUtils.isEmpty(order.getShopOwnerId())) {
			map.put("EQ_shopOwnerId", order.getShopOwnerId());
		}
		if (!StringUtils.isEmpty(shopIds)) {
			map.put("IN_shopId", shopIds);
		}
		if (!StringUtils.isEmpty(order.getOrderSn())) {
			map.put("EQ_orderSn", order.getOrderSn());
		}
		if (!StringUtils.isEmpty(order.getOrderStatusCode())) {
			if("refund_accepted".equals(order.getOrderStatusCode())){
				map.put("EQ_isRefund", order.getOrderStatusCode());
			}else{
				map.put("EQ_orderStatusCode", order.getOrderStatusCode());
			}
		}
		if (!StringUtils.isEmpty(order.getPayKindsId())) {
			map.put("EQ_payKindsId", order.getPayKindsId());
		}
		if (!StringUtils.isEmpty(startTime)) {
			map.put("GTE_createTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			map.put("LTE_createTime", endTime);
		}
		if (!StringUtils.isEmpty(firstSum)){
			map.put("GTE_totalPrice",firstSum);
		}
		if (!StringUtils.isEmpty(secondSum)){
			map.put("LTE_totalPrice",secondSum);
		}
		List<String> groupStatus = new ArrayList<String>();
		groupStatus.add("普通订单");
		groupStatus.add("已成团");
		groupStatus.add("未成团");
		map.put("IN_groupStatus", groupStatus);
		map.put("EQ_isDeleted", 0);
		return map;
	}
	
	
	public static Map<String, Object> searchOrder() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * @Title: searchRefundApplys @Description: 搜索订单退款 @param @param
	 *         param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> searchRefundApplys(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 订单Ids
		if (!StringUtils.isEmpty(param.get("orderIds")) && StringUtils.isEmpty(param.get("orderId"))) {
			map.put("IN_orderId", param.get("orderIds"));
		}
		if (StringUtils.isEmpty(param.get("orderIds")) && !StringUtils.isEmpty(param.get("orderId"))) {
			map.put("IN_orderId", param.get("orderId"));
		}
		if (!StringUtils.isEmpty(param.get("orderIds")) && !StringUtils.isEmpty(param.get("orderId"))) {
			List<String> orderIds = (List<String>) param.get("orderIds");
			List<String> orderId = (List<String>) param.get("orderId");
			orderIds.retainAll(orderId);
			map.put("IN_orderId", orderIds);
		}
		if (!StringUtils.isEmpty(param.get("flowNo"))){			
			map.put("EQ_refundOrderNo", param.get("flowNo"));
		}
		// 卖家ID
		if (!StringUtils.isEmpty(param.get("userId"))) {
			map.put("IN_sellerId", param.get("userId"));
		}
		// 买家ID
		if (!StringUtils.isEmpty(param.get("buyerId"))) {
			map.put("IN_buyerId", param.get("buyerId"));
		}
		// 退款申请处理状态编码
		if (!StringUtils.isEmpty(param.get("refundStatusCode"))) {
			map.put("EQ_refundStatusCode", param.get("refundStatusCode"));
		}
		// 申请查询开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_applyTime", startTime);
		}
		// 申请查询结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_applyTime", endTime);
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * @Title: searchFulfilRemit @Description: 店铺满减满送活动 @param @param
	 *         qo @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> searchDiscountTime(ShopQo qo) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(qo.getShopID())) {
			map.put("EQ_shopId", qo.getShopID());
		}
		if (!StringUtils.isEmpty(qo.getGoodsID())) {
			map.put("EQ_goodsId", qo.getGoodsID());
		}
		map.put("GTE_endTime", DateUtil.getCurrentDate());
		map.put("LTE_startTime", DateUtil.getCurrentDate());
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * @Title: searchDiscounts @Description: 限时折扣活动的检索 @param @param
	 *         param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> searchDiscounts(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 店铺ID
		if (!StringUtils.isEmpty(param.get("shopID"))) {
			map.put("EQ_shopId", param.get("shopID"));
		}
		// 商品ID
		if (!StringUtils.isEmpty(param.get("goodsIds"))) {
			map.put("IN_goodsId", param.get("goodsIds"));
		}
		// 活动开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_startTime", startTime);
		}
		if (!StringUtils.isEmpty(param.get("discountID"))) {
			map.put("EQ_id", param.get("discountID"));
		}
		// 活动结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_endTime", endTime);
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * @Title: searchCoupons @Description: 搜索优惠券 @param @param
	 *         param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> searchCoupons(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("couponName"))) {
			map.put("IN_couponName", param.get("couponName"));
		}
		// 店铺ID
		if (!StringUtils.isEmpty(param.get("shopID"))) {
			map.put("EQ_shopId", param.get("shopID"));
		}
		if (!StringUtils.isEmpty(param.get("couponID"))) {
			map.put("EQ_id", param.get("couponID"));
		}
//		map.put("GTE_expirationTime", new Date());
		//map.put("EQ_isAvailable", 1);
		map.put("EQ_isDeleted", 0);
		return map;
	}
	
	public static Map<String, Object> searchCoupon(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("couponName"))) {
			map.put("IN_couponName", param.get("couponName"));
		}
		// 店铺ID
		if (!StringUtils.isEmpty(param.get("shopID"))) {
			map.put("EQ_shopId", param.get("shopID"));
		}
		if (!StringUtils.isEmpty(param.get("couponID"))) {
			map.put("EQ_id", param.get("couponID"));
		}
		map.put("GTE_expirationTime", new Date());
		map.put("EQ_isAvailable", 1);
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * @Title: searchCombos @Description:搜索套餐搭配的检索 @param @param
	 *         param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> searchCombos(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 店铺ID
		if (!StringUtils.isEmpty(param.get("shopID"))) {
			map.put("EQ_shopId", param.get("shopID"));
		}
		if (!StringUtils.isEmpty(param.get("comboID"))) {
			map.put("EQ_id", param.get("comboID"));
		}
		if (!StringUtils.isEmpty(param.get("comboName"))) {
			map.put("LIKE_comboName", param.get("comboName"));
		}
		// 活动开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_startTime", startTime);
		}
		// 活动结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_endTime", endTime);
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}

	public static Map<String, Object> searchGroups(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 店铺ID
		if (!StringUtils.isEmpty(param.get("shopID"))) {
			map.put("EQ_shopId", param.get("shopID"));
		}
		if (!StringUtils.isEmpty(param.get("groupID"))) {
			map.put("EQ_id", param.get("groupID"));
		}
		// 商品ID
		if (!StringUtils.isEmpty(param.get("goodsIds"))) {
			map.put("IN_goodsId", param.get("goodsIds"));
		}
		// 团购状态
		if (!StringUtils.isEmpty(param.get("groupStatus"))) {
			if (param.get("groupStatus").equals("notStarted")) { // 未开始
				map.put("GTE_startTime", DateUtil.getCurrentDate());
			} else if (param.get("groupStatus").equals("underway")) { // 进行中
				map.put("LTE_startTime", DateUtil.getCurrentDate());
				map.put("GTE_endTime", DateUtil.getCurrentDate());
			} else if (param.get("groupStatus").equals("stale")) {// 已结束
				map.put("LTE_endTime", DateUtil.getCurrentDate());
			}
		}
		// 活动开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_startTime", startTime);
		}
		// 活动结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_endTime", endTime);
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}

	/**
	 * @Title: getGoods @Description: 商品评论 @param @param param @param @return
	 *         设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> getGoods(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("orderID"))) {
			map.put("IN_orderId", param.get("orderID"));
		}
		return map;
	}

	/**
	 * @Title: searchGroupDetails @Description: 组团的搜索 @param @param
	 *         param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public static Map<String, Object> searchGroupDetails(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(param.get("groupIsSuccess"))) {
			map.put("EQ_isGroupSuccess", param.get("groupIsSuccess"));
		}
		if (!StringUtils.isEmpty(param.get("groupID"))) {
			map.put("EQ_groupId", param.get("groupID"));
		}
		// 活动开始时间
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			Date startTime = new Date((Long) param.get("startTime"));
			map.put("GTE_createTime", startTime);
		}
		// 活动结束时间
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			map.put("LTE_createTime", endTime);
		}
		map.put("EQ_isDeleted", 0);
		return map;
	}
}
