package com.visionet.syh_mall.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.service.SyhconfigService;

@RestController
@RequestMapping(value="/api/syhconfig")
public class SyhconfigController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(SyhconfigController.class);
	
	@Autowired
	private SyhconfigService syhconfigService;
	/**
	 * 获取数据字典
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequestMapping(value="getDDInfo",method=RequestMethod.POST)
	public BaseReturnVo<Object> getDDInfo(@RequestBody Map<String,Object> map){
		logger.info("获取数据字典：{}",map);
		List<Map<String,Object>> ddInfo = syhconfigService.getDDInfo(map);
		return BaseReturnVo.success(ddInfo);
	}
}
