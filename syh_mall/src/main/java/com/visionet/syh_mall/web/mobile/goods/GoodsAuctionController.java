package com.visionet.syh_mall.web.mobile.goods;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.mobile.GoodsAuctionService;
import com.visionet.syh_mall.web.BaseController;
/**
 * 商品竞拍模块
 * @author mulongfei
 * @date 2017年8月31日下午2:32:22
 */
@RestController
@RequestMapping("/api/goods")
public class GoodsAuctionController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(GoodsAuctionController.class);
	@Autowired
	private GoodsAuctionService goodsAuctionService;
	
	/**
	 * 拍卖出价
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="/bid",method=RequestMethod.POST)
	@Log(name="拍卖出价",model="竞拍模块")
	public BaseReturnVo<Object> bidAmt(@RequestBody Map<String,Object> map){
		try {
			synchronized(this){
				logger.info("拍卖出价:{}",map);
				goodsAuctionService.BidAmt((String)map.get("addressId"),(String)map.get("goodsID"), new BigDecimal(String.valueOf(map.get("bidAmt"))), getCurrentUserId(), new BigDecimal(String.valueOf(map.get("expressFee"))));	
			}
		} catch (RestException e) {
			logger.error("拍卖出价异常",e);
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			logger.error("拍卖出价异常",e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
	
	/**
	 * 获取拍卖出价记录
	 * @param Map
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="/getBidRecord",method=RequestMethod.POST)
	public BaseReturnVo<Object> getBidRecord(@RequestBody Map<String,Object> map){
		logger.info("获取拍卖出价记录:{}",map);
		PageInfo page=new PageInfo((Integer) map.get("pageIndex"), (Integer) map.get("itemCount"));
		List<Map<String,Object>> bidRecord=null;
		try {
			bidRecord = goodsAuctionService.getBidRecord((String) map.get("goodsID"),page);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(bidRecord);
	}
	/**
	 * 拍卖出价申请退款
	 * @param Map
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="/refundBidAmt",method=RequestMethod.POST)
	@Log(name="拍卖出价申请退款",model="拍卖模块")
	public BaseReturnVo<Object> refundBidAmt(@RequestBody Map<String,Object> map){
		logger.info("拍卖出价申请退款:{}",map);
		try {
			goodsAuctionService.refundBidAmt((String) map.get("bidRecordID"));
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
}
