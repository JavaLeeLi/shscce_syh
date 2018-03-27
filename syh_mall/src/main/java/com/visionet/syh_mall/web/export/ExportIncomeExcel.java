package com.visionet.syh_mall.web.export;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.service.export.ExportService;
import com.visionet.syh_mall.service.finance.FinanceService;
import com.visionet.syh_mall.vo.finance.IncomeVo;

@RestController
@RequestMapping(value="/api/export")
public class ExportIncomeExcel {
	
	@Autowired
	private FinanceService financeService;
	@Autowired
	private ExportService exportService;
	
	@RequestMapping(value="/ExportIncomeExcel",method=RequestMethod.GET)
	public void ExportIncomeExcel(HttpServletResponse response,String userLoginName,String userName,String businessType,Long startTime,Long endTime) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(userLoginName)){
			param.put("userLoginName", userLoginName);
		}
		if(!StringUtils.isEmpty(userName)){
			param.put("userName", userName);
		}
		if(!StringUtils.isEmpty(businessType)){
			param.put("businessType", businessType);
		}
		if(!StringUtils.isEmpty(startTime)){
			param.put("startTime", new Date(startTime));
		}
		if(!StringUtils.isEmpty(endTime)){			
			param.put("endTime", new Date(endTime));
		}
		List<IncomeVo> list = financeService.searchIncome(param);
		//获得workbook对象
		HSSFWorkbook workbook = exportService.exportIncomeExcel(list);
		Date currentDate = DateUtil.getCurrentDate();
		String dateTime = DateUtil.convertToString(currentDate);
		String fileName = "收入导出表"+dateTime+".xlsx";
		response.setContentType("application/octet-stream;charset=utf-8");  
		response.setHeader("Content-Disposition", "attachment;filename="  
		        + new String(fileName.getBytes(),"iso-8859-1")+ DateUtil.getCurrentDate() + ".xls");
		OutputStream os = response.getOutputStream();
		workbook.write(os);
		workbook.close();
		os.close();
	}
}
