package com.visionet.syh_mall.web.assistant;

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
import com.visionet.syh_mall.service.assistant.CustomerServiceInfoService;
import com.visionet.syh_mall.vo.CustomerServiceInfoVo;
import com.visionet.syh_mall.web.BaseController;

@RestController
@RequestMapping(value = "api/assistant")
public class CustomerServiceInfoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceInfoController.class);

	@Autowired
	private CustomerServiceInfoService customerServiceInfoService;

	/**
	 * 
	 * @Title: getAssistants @Description: 获取客服列表的方法 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "getAssistants", method = RequestMethod.POST)
	public BaseReturnVo<Object> getAssistants(@RequestBody Map<String, Object> param) {
		logger.info("获取客服列表的参数{}", param);
		List<Map<String, Object>> result = null;
		try {
			result = customerServiceInfoService.getAssistants(param);
		} catch (RestException e) {
			logger.error("获取客服列表异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取客服列表异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * 
	 * @Title: addAssistant @Description: 添加/编辑客服 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "addAssistant", method = RequestMethod.POST)
	@Log(name="添加/编辑客服",model="客服模块")
	public BaseReturnVo<Object> addAssistant(@RequestBody @Valid CustomerServiceInfoVo serviceInfoVo,
			BindingResult result) {
		logger.info("编辑/添加客服的参数{}", serviceInfoVo.toString());
		try {
			customerServiceInfoService.addAssistant(serviceInfoVo, getCurrentUserId());
		} catch (RestException e) {
			logger.error("编辑/添加客服异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("编辑/添加客服异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 
	 * @Title: delAssistant @Description: 删除客服 @param @param customerServiceID
	 *         设定文件 @return void 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "delAssistant", method = RequestMethod.POST)
	@Log(name="删除客服",model="客服模块")
	public BaseReturnVo<Object> delAssistant(@RequestBody Map<String, String> param) {
		logger.info("删除客服的参数{}", param);
		try {
			customerServiceInfoService.delAssistant(param.get("customerServiceID"));
		} catch (RestException e) {
			logger.error("删除客服异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除客服异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

}
