package com.visionet.syh_mall.web.export;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import com.visionet.syh_mall.service.finance.FinanceService;
import com.visionet.syh_mall.vo.finance.FundSummary;
import com.visionet.syh_mall.vo.finance.FundSummaryPo;
import com.visionet.syh_mall.vo.finance.FundSummaryVo;

@RestController
@RequestMapping(value="api/export")
public class ExportFundSummaryExcel {
	private static final Logger logger = LoggerFactory.getLogger(ExportFundSummaryExcel.class);
	
	@Autowired
	private FinanceService financeService;
	@Autowired
	private ExportService exportService;
	
	@RequestMapping(value="/exportFundSummaryExcel",method=RequestMethod.GET)
	public void exportFundSummaryExcel(HttpServletResponse response,BigDecimal littleAmt,BigDecimal heightAmt,String userLoginName,String userName,Long startTime,Long endTime) throws Exception {
		logger.info("导出资金汇总表");
		FundSummaryPo po = new FundSummaryPo();
		po.setUserLoginName(userLoginName);
		po.setUserName(userName);
		po.setHeightAmt(heightAmt);
		po.setLittleAmt(littleAmt);
		if(!StringUtils.isEmpty(endTime)){
			po.setEndTime(new Date(endTime));
		}
		if(!StringUtils.isEmpty(startTime)){
			po.setStartTime(new Date(startTime));
		}
		FundSummaryVo summaryVo = financeService.exportFundSummary(po);
		List<FundSummary> list = summaryVo.getFundSummarys();
		//获得workbook对象
		HSSFWorkbook workbook = exportService.exportFundSummaryExcel(list);
		Date currentDate = DateUtil.getCurrentDate();
		String dateTime = DateUtil.convertToString(currentDate);
		String fileName = "资金汇总表"+dateTime+".xlsx";
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="  
		        + new String(fileName.getBytes(),"iso-8859-1")+ DateUtil.getCurrentDate() + ".xls");
		OutputStream os = response.getOutputStream();
		workbook.write(os);
		workbook.close();
		os.close();
	}
}
