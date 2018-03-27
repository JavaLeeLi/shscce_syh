package com.visionet.syh_mall.web.mobile.order;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.order.OrderCancService;
import com.visionet.syh_mall.vo.ServiceReasonVo;
import com.visionet.syh_mall.web.BaseController;

/**
 * @ClassName: OrderCancController
 * @Description: 退货申请
 * @author chenghongzhan
 * @date 2017年9月13日 上午11:25:49
 *
 */
@RestController
@RequestMapping("api/order")
public class OrderCancController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(OrderCancController.class);

	@Autowired
	private OrderCancService orderCancService;

	/**
	 * 
	 * @Title: searchCancApplys @Description: 搜索退货申请 @param @param
	 *         map @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/searchCancApplys", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchCancApplys(@RequestBody Map<String, Object> param) {
		logger.info("搜索退货申请:{}", param);
		Map<String, Object> result = null;
		try {
			result = orderCancService.searchCancApplys(param, getPageInfo(param, Direction.DESC, "updateTime"));
		} catch (RestException e) {
			logger.error("搜索退货申请异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索退货申请异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: reviewCancApply @Description: 审核退货申请 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/reviewCancApply", method = RequestMethod.POST)
	@Log(name="审核退货申请",model="退货模块")
	public BaseReturnVo<Object> reviewCancApply(@RequestBody Map<String, String> param) {
		logger.info("审核退货申请:{}", param);
		try {
			orderCancService.reviewCancApply(param.get("orderID"), param.get("reviewIsApproved"));
		} catch (RestException e) {
			logger.error("审核退货申请异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("审核退货申请异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: searchRefundApplys @Description: 搜索退款申请 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/searchRefundApplys", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchRefundApplys(@RequestBody Map<String, Object> param) {
		logger.info("搜索退款申请的参数:{}", param);
		Map<String, Object> result = null;
		try {
			result = orderCancService.searchRefundApplys(param, getPageInfo(param, Direction.DESC,"updateTime"));
		} catch (RestException e) {
			logger.error("搜索退款申请:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索退款申请:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: confimrRefund @Description: 确认退款的 方法 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/confimrRefund", method = RequestMethod.POST)
	@Log(name="确认退款",model="退款模块")
	public BaseReturnVo<Object> confimrRefund(@RequestBody Map<String, String> param) {
		logger.info("确认退款的参数:{}", param);
		try {
			orderCancService.confimrRefund(param.get("orderID"),getCurrentUserId());
		} catch (RestException e) {
			logger.error("确认退款异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("确认退款异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
	
	/**
	 * 批量确认退款
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequiresAuthentication
	@RequestMapping(value = "/batchRefund", method = RequestMethod.POST)
	@Log(name="批量确认退款",model="退款模块")
	public BaseReturnVo<Object> batchRefund(@RequestBody Map<String, Object> param) {
		logger.info("批量确认退款的参数:{}", param);
		List<Map<String,String>> orderList = (List<Map<String, String>>) param.get("orderIdList");
		try {
			for (Map<String,String> orderNews : orderList) {
				orderCancService.confimrRefund(orderNews.get("orderID"),getCurrentUserId());
			}
		} catch (RestException e) {
			logger.error("确认退款异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("确认退款异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: reviewRefundApply @Description: 审核退款申请 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/reviewRefundApply", method = RequestMethod.POST)
	@Log(name="审核退款申请",model="退款模块")
	public BaseReturnVo<Object> reviewRefundApply(@RequestBody Map<String, String> param) {
		logger.info("审核退款申请的参数:{}", param);
		try {
			orderCancService.reviewRefundApply(param.get("banReason"), param.get("orderID"),
					param.get("reviewIsApproved"),getCurrentUserId(),0);
		} catch (RestException e) {
			logger.error("审核退款申请异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("审核退款申请异常:{}", e);
			e.printStackTrace();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: addAftersaleReason @Description: 添加/编辑售后原因 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/addAftersaleReason", method = RequestMethod.POST)
	@Log(name="添加/编辑售后原因",model="售后模块")
	public BaseReturnVo<Object> addAftersaleReason(@RequestBody @Valid ServiceReasonVo reasonVo, BindingResult result) {
		logger.info("添加/编辑售后原因的参数:{}", reasonVo.toString());
		try {
			orderCancService.addAftersaleReason(reasonVo, getCurrentUserId());
		} catch (RestException e) {
			logger.error("添加/编辑售后原因 异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加/编辑售后原因 异常:{}", e);
			e.printStackTrace();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: delAftersaleReason @Description: 删除售后原因 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/delAftersaleReason", method = RequestMethod.POST)
	@Log(name="删除售后原因",model="售后模块")
	public BaseReturnVo<Object> delAftersaleReason(@RequestBody Map<String, String> param) {
		logger.info("删除售后原因的参数:{}", param);
		try {
			orderCancService.delAftersaleReason(param.get("reasonID"), getCurrentUserId());
		} catch (RestException e) {
			logger.error("删除售后原因 异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除售后原因 异常:{}", e);
			e.printStackTrace();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: getAftersaleReasons @Description: 查询售后原因列表 @param @return
	 *         设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/getAftersaleReasons", method = RequestMethod.POST)
	public BaseReturnVo<Object> getAftersaleReasons() {
		logger.info("查询售后原因列表的参数:{无参数的方法}");
		List<Map<String, Object>> result = null;
		try {
			result = orderCancService.getAftersaleReasons();
		} catch (RestException e) {
			logger.error("查询售后原因列表异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("查询售后原因列表异常:{}", e);
			e.printStackTrace();
		}
		return BaseReturnVo.success(result);
	}

	@RequestMapping(value="/addArbitration",method=RequestMethod.POST)
	public BaseReturnVo<Object> addArbitration(@RequestBody Map<String,Object> param){
		logger.info("发货仲裁请求：{}",param);
		try {
			orderCancService.addArbitration(param);
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
}
