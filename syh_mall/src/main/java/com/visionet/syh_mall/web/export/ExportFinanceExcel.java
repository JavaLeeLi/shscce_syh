package com.visionet.syh_mall.web.export;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.service.export.ExportFinanceVo;
import com.visionet.syh_mall.service.export.ExportService;
import com.visionet.syh_mall.service.finance.FinanceService;

@RestController
@RequestMapping("/api/ExportExcel")
public class ExportFinanceExcel {
	private static final Logger logger = LoggerFactory.getLogger(ExportFinanceExcel.class);
	@Autowired
	private FinanceService financeService;
	@Autowired
	private ExportService exportService;
	/**
	 * 导出财务流水
	 * @param list
	 * @throws Exception
	 */
	@RequestMapping(value="/exportFinanceExcel",method=RequestMethod.GET)
	public void exportExternalExcel(HttpServletResponse response,HttpServletRequest request) throws Exception { 
		logger.info("导出业务流水记录");
		List<ExportFinanceVo> list = financeService.getFinanceExport();
		//获得workbook对象
		HSSFWorkbook workbook = exportService.exportFinanceExcel(list);
		Date currentDate = DateUtil.getCurrentDate();
		String dateTime = DateUtil.convertToString(currentDate);
		String fileName = "业务流水导出表"+dateTime+".xlsx";
		response.setContentType("application/octet-stream;charset=utf-8");  
		response.setHeader("Content-Disposition", "attachment;filename="  
		        + new String(fileName.getBytes(),"iso-8859-1")+ DateUtil.getCurrentDate() + ".xls");
		OutputStream os = response.getOutputStream();
		workbook.write(os);
		workbook.close();
		os.close();
	}
}
