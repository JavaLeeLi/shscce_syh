package com.visionet.syh_mall.web.mobile.mbr;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.entity.mbr.UserAddress;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.mbr.UserAddressService;
import com.visionet.syh_mall.vo.UserAddressQo;
import com.visionet.syh_mall.web.BaseController;
import com.visionet.syh_mall.web.mobile.BannerController;

/**
 *@Author DM
 *@version ：2017年8月31日下午3:25:23
 *实体类
 */
@RestController
@RequestMapping(value="api/mbr")
public class UserAddressController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(BannerController.class);
	@Autowired
	private UserAddressService userAddressService;
	/**
	 * 根据用户ID获取收货地址列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getReceiverAddrs",method=RequestMethod.POST)
	public Object queryList(@RequestBody UserAddressQo qo) {
		logger.info("获取收货地址:{}", qo);
		List<UserAddress> list = null;
		try {
			list = userAddressService.queryList(qo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaseReturnVo<List<UserAddress>> returnVo = new BaseReturnVo<List<UserAddress>>(list);
		return returnVo;
	}
	/**
	 * 添加/编辑收货地址
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addReceiverAddr",method=RequestMethod.POST)
	@Log(name="添加/编辑收货地址",model="会员地址模块")
	public BaseReturnVo<Object> updateUserAddress(@RequestBody UserAddressQo qo) throws Exception {
		logger.info(" 添加/编辑收货地址:{}", qo);
		try {
		userAddressService.updateUserAddress(qo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常,修改失败");
		}
		return BaseReturnVo.success("操作成功");
	}
	/**
	 * 删除收货地址
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delReceiverAddr",method=RequestMethod.POST)
	@Log(name="删除收货地址",model="会员地址模块")
	public BaseReturnVo<Object> deleteMessageApp(@RequestBody UserAddressQo qo) throws Exception {
		logger.info(" 删除收货地址:{}", qo);
		try {
		userAddressService.deleteMessageApp(qo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常,修改失败");
		}
		return BaseReturnVo.success("删除成功");
	}
	/**
	 * 获取消息详情
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/getAddrDetail",method = RequestMethod.POST)
	public BaseReturnVo<Object> getAddrDetail(@RequestBody UserAddressQo qo) throws Exception{
		logger.info("获取地址详情");
		UserAddress list = userAddressService.getAddrDetail(qo);
		return BaseReturnVo.success(list);
	}
}
