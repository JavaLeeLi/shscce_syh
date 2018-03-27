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
import com.visionet.syh_mall.service.mobile.HomeNaviService;
import com.visionet.syh_mall.vo.HomeNaviVo;
import com.visionet.syh_mall.vo.HomeNaviVo.HomeNaviInfo;
import com.visionet.syh_mall.web.BaseController;

/**
 * @Author DM
 * @version ：2017年8月21日下午2:16:55 快捷导航查询控制层
 */
@RestController
@RequestMapping(value = "api/home")
public class HomeNaviController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(HomeNaviController.class);

	@Autowired
	private HomeNaviService homeNaviService;

	/**
	 * @Title: queryList @Description: 首页获取快捷导航信息 @param @return @param @throws
	 *         Exception 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public BaseReturnVo<Object> queryList() throws Exception {
		logger.info("首页获取快捷导航信息:{无参数}");
		List<HomeNaviInfo> result = null;
		try {
			result = homeNaviService.homeNaviList();
		} catch (RestException e) {
			logger.error("首页获取快捷导航异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("首页获取快捷导航异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: delNavi @Description: 删除快捷导航 @param @param param @param @return
	 *         设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/delNavi", method = RequestMethod.POST)
	@Log(name="删除快捷导航",model="首页模块")
	public BaseReturnVo<Object> delNavi(@RequestBody Map<String, String> param) {
		logger.info("删除快捷导航:{}", param);
		try {
			homeNaviService.delHomeNavi(param.get("homeNaviID"),getCurrentUserId());
		} catch (RestException e) {
			logger.error("删除快捷导航异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除快捷导航异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: addHomeNavi @Description: 添加快捷导航的方法 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/addNavi", method = RequestMethod.POST)
	@Log(name="添加快捷导航",model="首页模块")
	public BaseReturnVo<Object> addHomeNavi(@RequestBody @Valid HomeNaviVo homeNaviVo, BindingResult result) {
		logger.info("添加/编辑快捷导航的参数:{}", homeNaviVo.toString());
		// 接收前端传过来的热门导航参数并封装成对象给Service进行修改和新增的操作
		try {
			homeNaviService.addHomeNavi(homeNaviVo, getCurrentUserId());
		} catch (RestException e) {
			logger.error("添加/编辑快捷导航异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加/编辑快捷导航异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
}
