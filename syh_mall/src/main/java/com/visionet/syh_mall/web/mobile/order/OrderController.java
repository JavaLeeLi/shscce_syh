package com.visionet.syh_mall.web.mobile.order;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.order.OrderService;
import com.visionet.syh_mall.service.order.TradeSettingService;
import com.visionet.syh_mall.vo.NoticeQo;
import com.visionet.syh_mall.vo.OrderInfo;
import com.visionet.syh_mall.vo.OrderInfoVo;
import com.visionet.syh_mall.vo.OrderQo;
import com.visionet.syh_mall.vo.TradeSettingVo;
import com.visionet.syh_mall.vo.order.OrderVo;
import com.visionet.syh_mall.web.BaseController;

/**
 * @Author DM
 * @version ：2017年8月21日下午5:19:22 订单业务控制类
 */
@RestController
@RequestMapping(value = "api/order")
public class
OrderController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;
	@Autowired
	private TradeSettingService tradeService;

	/**
	 * 首页获取平台最近5次的成交记录
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Object queryList(@RequestBody NoticeQo qo) throws Exception {
		logger.info("首页获取平台最近5次的成交记录:{}", qo);
		List<OrderInfoVo> orderList = orderService.queryList();
		List<OrderInfoVo> subList = null;
		if(orderList.size()>=30){
			subList = orderList.subList(0, 30);
		}else{
			subList = orderList;
		}
		BaseReturnVo<List<OrderInfoVo>> returnVo = new BaseReturnVo<List<OrderInfoVo>>(subList);
		return returnVo;
	}
	/**
	 * 搜索订单 @param Map<String,Object> @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/searchOrders", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchOrders(@RequestBody Map<String, Object> map) {
		logger.info("搜索订单:{}", map);
		Map<String, Object> result = null;
		try {
			result = orderService.searchOrders(map);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}
	/**
	 * 结算订单 @param OrderQo @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
	@Log(name="结算订单",model="订单模块")
	public BaseReturnVo<Object> generateOrder(@RequestBody @Valid OrderVo qo ,BindingResult result) {
		logger.info("结算订单:{}", qo);
		List<Map<String, Object>> orderIds = null;
		try {
			orderIds = orderService.generateOrder(qo, getCurrentUserId());
		} catch (RestException e) {
			e.printStackTrace();
			String[] split = e.getMessage().split("-");
			throw new RestException(BusinessStatus.getCodeByDesc(split[split.length-1]), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(orderIds);
	}

	/**
	 * 添加商品供货请求
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequestMapping(value="addSupplyRecord",method=RequestMethod.POST)
	public BaseReturnVo<Object> addSupplyRecord(@RequestBody Map<String,Object> map){
		logger.info("添加商品供货请求:{}", map);
		try {			
			orderService.addSupplyRecord(map);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return new BaseReturnVo<Object>("成功");
	}
	
	/**
	 * 求购供货 @param @return BaseReturnVo<Object> @throws
	 */
	@SuppressWarnings("static-access")
	@RequiresAuthentication
	@RequestMapping(value = "/purchaseOrder", method = RequestMethod.POST)
	@Log(name="求购供货",model="商品模块")
	public BaseReturnVo<Object> BUYResponse(@RequestBody @Valid OrderQo qo,BindingResult result) {
		logger.info("求购供货:{}", qo);
		List<Map<String, Object>> orderIds = null;
		try {
			orderIds = orderService.BUYResponse(qo, this.getCurrentUserId());
		} catch (RestException e) {
			e.printStackTrace();
			String[] split = e.getMessage().split("-");
			throw new RestException(BusinessStatus.getCodeByDesc(split[split.length-1]), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(orderIds);
	}

	/**
	 * 订单发货 @param Map @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/deliveryOrder", method = RequestMethod.POST)
	@Log(name="订单发货",model="订单模块")
	public BaseReturnVo<Object> deliveryOrder(@RequestBody Map<String, Object> map) {
		logger.info("订单发货:{}", map);
		try {
			orderService.deliveryOrder((String) map.get("orderID"), (String) map.get("expressBillNo"));
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 确认收货 @param Map @return BaseReturnVo<Object> @throws
	 *//*
	@RequiresAuthentication
	@RequestMapping(value = "/receiveOrder", method = RequestMethod.POST)
	@Log(name="确认收货",model="订单模块")
	public BaseReturnVo<Object> receiveOrder(@RequestBody Map<String, Object> map) {
		logger.info("确认收货:{}", map);
		try {
			orderService.receiveOrder((String) map.get("orderID"));
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}*/

	/**
	 * 取消订单 @param Map @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
	@Log(name="取消订单",model="订单模块")
	public BaseReturnVo<Object> cancelOrder(@RequestBody Map<String, Object> map) {
		logger.info("取消订单:{}", map);
		try {
			orderService.cancelOrder((String) map.get("orderID"));
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 评价订单 @param Map @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/scoreOrder", method = RequestMethod.POST)
	@Log(name="订单评价",model="订单模块")
	public BaseReturnVo<Object> scoreOrder(@RequestBody Map<String, Object> map) {
		logger.info("评价订单:{}", map);
		try {
			orderService.scoreOrder(map,getCurrentUserId(),0);
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 申请退款 @param Map @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/applyRefund", method = RequestMethod.POST)
	@Log(name="申请退款",model="退款模块")
	public BaseReturnVo<Object> applyRefund(@RequestBody Map<String, Object> map) {
		logger.info("申请退款:{}", map);
		try {
			orderService.applyRefund((String) map.get("orderID"),(String)map.get("refundReason"));
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 订单改价 @param Map @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/modifyOrderAmt", method = RequestMethod.POST)
	@Log(name="订单改价",model="订单模块")
	public BaseReturnVo<Object> modifyOrderAmt(@RequestBody OrderInfo orderInfo) {
		logger.info("订单改价:{}", orderInfo);
		try {
			orderService.modifyOrderAmt(orderInfo.getOrderID(), orderInfo.getOrderSum());
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 订单修改快递单号 @param Map @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/modifyExpressNO", method = RequestMethod.POST)
	@Log(name="订单修改运单号",model="订单模块")
	public BaseReturnVo<Object> modifyExpressNO(@RequestBody Map<String, Object> map) {
		logger.info("订单修改快递单号:{}", map);
		try {
			orderService.modifyExpressNO((String) map.get("orderID"), (String) map.get("expressNO"));
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 获取订单详情 @param Map @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/getOrderDetail", method = RequestMethod.POST)
	public BaseReturnVo<Object> getOrderDetail(@RequestBody Map<String, Object> map) {
		logger.info("获取订单详情:{}");
		if (StringUtils.isEmpty(map.get("orderID"))) {
			throw new RestException(BusinessStatus.NULL_LIMIT.getCode(), BusinessStatus.NULL_LIMIT.getDesc());
		}
		Map<String, Object> orderMap = null;
		try {
			orderMap = orderService.getOrderDetail(String.valueOf(map.get("orderID")));
		} catch (RestException e) {
			logger.error("获取订单详情异常：",e);
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			logger.error("获取订单详情异常：",e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(orderMap);
	}

	/**
	 * 获取订单收件信息 @param Map @return BaseReturnVo<Object> @throws
	 */
	@RequestMapping(value = "/getOrderReceiverDetail", method = RequestMethod.POST)
	public BaseReturnVo<Object> getOrderReceiverDetail(@RequestBody Map<String, Object> map) {
		logger.info("获取订单收件信息:{}", map);
		Object news = null;
		try {
			news = orderService.getOrderReceiverDetail((String) map.get("orderID"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(news);
		return BaseReturnVo.success(jsonObject);
	}
	/**
	 * @Title: getPayKinds @Description: 获取支付方式列表 @param @param
	 *         qo @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/getPayKinds", method = RequestMethod.POST)
	public BaseReturnVo<Object> getPayKinds() {
		logger.info("获取支付方式列表的参数:{}");
		List<Map<String, Object>> result = null;
		try {
			result = orderService.getPayKinds();
		} catch (RestException e) {
			logger.error("获取支付方式异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取支付方式异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: switchPayKind @Description: 停用/启用平台支持的支付方式 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "switchPayKind", method = RequestMethod.POST)
	@Log(name="停用/启用平台支付方式",model="支付模块")
	public BaseReturnVo<Object> switchPayKind(@RequestBody Map<String, Object> param) {
		logger.info("停用/启用平台支持的支付方式的参数:{}", param);
		try {
			String payKindID = (String) param.get("payKindID");
			Integer isAvailable = (Integer) param.get("isAvailable");
			orderService.switchPayKind(payKindID, isAvailable);
		} catch (RestException e) {
			logger.error("停用/启动支付方式异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("停用/启动支付方式异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: getTradeSetting @Description: 搜索交易设置 @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "getTradeSetting", method = RequestMethod.POST)
	public BaseReturnVo<Object> getTradeSetting() {
		logger.info("交易设置的参数:{}");
		List<Map<String, Object>> result = null;
		try {
			result = tradeService.getTradeSetting();
		} catch (RestException e) {
			logger.error("搜索交易设置异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索交易设置异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: editTradeSetting @Description: 编辑交易设置 @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "editTradeSetting", method = RequestMethod.POST)
	@Log(name="编辑交易设置",model="交易模块")
	public BaseReturnVo<Object> editTradeSetting(@RequestBody @Valid TradeSettingVo settingVo,BindingResult result) {
		logger.info("编辑交易设置的参数:{}", settingVo.toString());
		try {
			tradeService.editTradeSetting(settingVo, getCurrentUserId());
		} catch (RestException e) {
			logger.error("编辑交易设置异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("编辑交易设置异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
	
	/**
	 * 提醒发货
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequestMapping(value="/orderRemind",method=RequestMethod.POST)
	public BaseReturnVo<Object> orderRemind(@RequestBody Map<String,Object> param){
		logger.info("提醒发货：{}",param);
		try {			
			orderService.orderRemind(param);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return new BaseReturnVo<Object>("成功");
	}
}
