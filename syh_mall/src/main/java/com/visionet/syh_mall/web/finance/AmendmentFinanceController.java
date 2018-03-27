package com.visionet.syh_mall.web.finance;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.service.finance.AmendmentFinanceService;
import com.visionet.syh_mall.web.BaseController;

@RestController
@RequestMapping(value="/api/export")
public class AmendmentFinanceController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(AmendmentFinanceController.class);
	
	@Autowired
	private AmendmentFinanceService amendmentFinanceService;
	
	@RequestMapping(value="amendmentFinance",method=RequestMethod.POST)
	public BaseReturnVo<Object> amendmentFinance(@RequestBody Map<String,Object> param){
		logger.info("修改对账异常文件:{}",param);
		String bizOrderNo = (String) param.get("bizOrderNo");
		amendmentFinanceService.amendmentFinance(bizOrderNo);
		return BaseReturnVo.success("成功");
	}
}
