package com.visionet.syh_mall.web.export;

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
import com.visionet.syh_mall.service.finance.FinanceService;
import com.visionet.syh_mall.vo.user.BondVo;

/**
 * 导出保证金
 * @date 2018年1月26日上午11:49:48
 */
@RestController
@RequestMapping(value="api/export")
public class ExportCashDepositExcel {
	private static final Logger logger = LoggerFactory.getLogger(ExportCashDepositExcel.class);
	
	@Autowired
	private ExportService exportService;
	@Autowired
	private FinanceService financeService;
	
	@RequestMapping(value="/ExportCashDepositExcel",method=RequestMethod.GET)
	public void exportCashDepositExcel(HttpServletResponse response, String shopName, String status) throws Exception {
		logger.info("保证金导出");
		Map<String,Object> param = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(shopName)){			
			param.put("shopName", shopName);
		}
		if(!StringUtils.isEmpty(status)){			
			param.put("status", status);
		}
		List<BondVo> searchCashDeposit = financeService.searchCashDeposit(param);
		HSSFWorkbook workbook = exportService.exportCashDepositExcel(searchCashDeposit);
		Date currentDate = DateUtil.getCurrentDate();
		String dateTime = DateUtil.convertToString(currentDate);
		String fileName = "保证金导出表" + dateTime + ".xlsx";
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1")
				+ DateUtil.getCurrentDate() + ".xls");
		OutputStream os = response.getOutputStream();
		workbook.write(os);
		workbook.close();
		os.close();
	}
}
