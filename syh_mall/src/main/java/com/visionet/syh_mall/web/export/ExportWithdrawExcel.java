package com.visionet.syh_mall.web.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.service.export.ExportService;
import com.visionet.syh_mall.service.export.ExportWithdrawVo;
import com.visionet.syh_mall.service.finance.DrawCashService;

@RestController
@RequestMapping(value="api/export")
public class ExportWithdrawExcel {
	private final static Logger logger = LoggerFactory.getLogger(ExportWithdrawExcel.class);
	
	@Autowired
	private ExportService exportService;
	@Autowired
	private DrawCashService drawCashService;
	
	@RequestMapping(value="/exportWithdrawExcel",method=RequestMethod.GET)
	public void exportWithdrawExcel(HttpServletResponse response,String userLoginName,String drawCashState,String cardNo,Long startTime,Long endTime) throws IOException{
		logger.info("导出提现记录：{}");
		Map<String,Object> param = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(userLoginName)){
			param.put("userLoginName", userLoginName);
		}
		if(!StringUtils.isEmpty(drawCashState)){
			param.put("drawCashState", drawCashState);
		}
		if(!StringUtils.isEmpty(cardNo)){
			param.put("cardNo", cardNo);
		}
		if(!StringUtils.isEmpty(startTime)){	
			param.put("startTime", new Date(startTime));
		}
		if(!StringUtils.isEmpty(endTime)){			
			param.put("endTime", new Date(endTime));
		}
		List<ExportWithdrawVo> list = drawCashService.exportWithdraw(param);
		//获得workbook对象
		HSSFWorkbook workbook = exportService.exportWithdrawExcel(list);
		Date currentDate = DateUtil.getCurrentDate();
		String dateTime = DateUtil.convertToString(currentDate);
		String fileName = "提现申请导出表"+dateTime+".xlsx";
		response.setContentType("application/octet-stream;charset=utf-8");  
		response.setHeader("Content-Disposition", "attachment;filename="  
		        + new String(fileName.getBytes(),"iso-8859-1")+ DateUtil.getCurrentDate() + ".xls");
		OutputStream os = response.getOutputStream();
		workbook.write(os);
		workbook.close();
		os.close();
	}
}
