package com.visionet.syh_mall.web;


import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;

@RestController
@RequestMapping("/api/common")
public class CommonController extends BaseController {
	
	/**
	 * 发送短信
	* @author dm
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/isLogin", method = RequestMethod.POST)
	public Object queryList() throws Exception { 
		return BaseReturnVo.success("成功");
	}
}
