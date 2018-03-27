package com.visionet.syh_mall.web.mobile;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.mobile.BannerService;
import com.visionet.syh_mall.vo.BannerVo;
import com.visionet.syh_mall.vo.BannerVo.BannerReturn;
import com.visionet.syh_mall.web.BaseController;

/**
 * @Author DM
 * @version ：2017年8月16日下午2:32:29 banner展示业务控制
 */
@RestController
@RequestMapping(value = "api/banner")
public class BannerController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BannerController.class);
	@Autowired
	private BannerService bannerService;
	
	/**
	 * @Title: queryList @Description:搜索首页banner方法 @param @return @param @throws
	 *         Exception 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/list")
	public BaseReturnVo<Object> queryList(@RequestBody Map<String, Object> param) throws Exception {
		logger.info("首页获取banner信息:{}", param);
		List<BannerReturn> result = null;
		try {
			result = bannerService.queryList(param);
		} catch (RestException e) {
			logger.error("获取banner详情异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取banner详情异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: reviseBanner @Description: 添加/编辑banner项 @param @param
	 *         map @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/revise", method = RequestMethod.POST)
	@Log(name = "添加/编辑banner项", model = "首页模块")
	public BaseReturnVo<Object> reviseBanner(@RequestBody @Valid BannerVo bannerVo, BindingResult result) {
		logger.info("添加/编辑banner项:{}", bannerVo.toString());
		try {
			// 添加或编辑Banner的Service层方法
			bannerService.reviseBanner(bannerVo);
		} catch (RestException e) {
			logger.error("添加/编辑banner异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加/编辑banner异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: delBanner @Description: 删除banner项 @param @param
	 *         map @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/delBanner", method = RequestMethod.POST)
	@Log(name = "删除banner项", model = "首页模块")
	public BaseReturnVo<Object> delBanner(@RequestBody Map<String, String> map) {
		logger.info("删除banner项:{}", map);
		try {
			bannerService.delBanner(map.get("bannerID"), getCurrentUserShop());
		} catch (RestException e) {
			logger.error("删除banner异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除banner异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

}
