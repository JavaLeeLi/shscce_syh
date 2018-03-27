package com.visionet.syh_mall.web.syhservice;

import java.util.ArrayList;
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
import com.visionet.syh_mall.service.syhservice.SyhserviceService;
import com.visionet.syh_mall.vo.SyhserviceVo;
import com.visionet.syh_mall.web.BaseController;

/**
 * @ClassName: SyhserviceController
 * @Description: 服务内容管理模块
 * @author chenghongzhan
 * @date 2017年9月18日 下午7:59:05
 *
 */

@RestController
@RequestMapping(value = "/api/syhservice")
public class SyhserviceController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SyhserviceController.class);
	@Autowired
	private SyhserviceService syhserviceService;

	/**
	 * @Title: reviseSyhservice @Description: 添加/编辑服务内容 @param @param
	 *         syhserviceVo @param @param result @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/revice", method = RequestMethod.POST)
	@Log(name="添加/编辑服务内容",model="服务模块")
	public BaseReturnVo<Object> reviseSyhservice(@RequestBody @Valid SyhserviceVo syhserviceVo, BindingResult result) {
		logger.info("添加/编辑服务内容:{}", syhserviceVo.toString());
		try {
			syhserviceService.reviceSyhservice(syhserviceVo,getCurrentUserId());
		} catch (RestException e) {
			logger.error("添加/编辑服务内容数据异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加/编辑服务内容数据异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: listSyhservice @Description: 按条件查找符合要求的服务数据 @param @param
	 *         map @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public BaseReturnVo<Object> listSyhservice(@RequestBody Map<String, Object> param) {
		logger.info("按条件查找符合要求的服务数据:{}", param);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		try {
			result = syhserviceService.getSyhserviceList(param);
		} catch (RestException e) {
			logger.error("按条件查找符合要求的服务数据异常:{}", e);
			e.printStackTrace(	);
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("按条件查找符合要求的服务数据异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}
}
