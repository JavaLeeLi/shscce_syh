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
import com.visionet.syh_mall.service.account.UserAccountService;
import com.visionet.syh_mall.service.export.ExportService;
import com.visionet.syh_mall.vo.user.UserAccountVo;

/**
 * 导出余额
 * @date 2018年1月26日上午11:49:24
 */
@RestController
@RequestMapping(value="/api/export")
public class ExportAccountExcel {
	private static final Logger logger = LoggerFactory.getLogger(ExportAccountExcel.class);
	
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private ExportService exportService;
	
	@RequestMapping(value="exportAccountExcel",method=RequestMethod.GET)
	public void exportAccountExcel(HttpServletResponse response, String userLoginName) throws Exception {
		logger.info("账户余额导出");
		Map<String,String> param = new HashMap<String,String>();
		if(!StringUtils.isEmpty(userLoginName)){
			param.put("userLoginName", userLoginName);
		}
		List<UserAccountVo> searchUserAccount = userAccountService.searchUserAccount(param);
		HSSFWorkbook workbook = exportService.exportAccountExcel(searchUserAccount);
		Date currentDate = DateUtil.getCurrentDate();
		String dateTime = DateUtil.convertToString(currentDate);
		String fileName = "账户余额导出表" + dateTime + ".xlsx";
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1")
				+ DateUtil.getCurrentDate() + ".xls");
		OutputStream os = response.getOutputStream();
		workbook.write(os);
		workbook.close();
		os.close();
	}
}
