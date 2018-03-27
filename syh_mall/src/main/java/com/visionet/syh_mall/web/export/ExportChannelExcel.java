package com.visionet.syh_mall.web.export;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import com.visionet.syh_mall.entity.channel.CommissionFlow;
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
import com.visionet.syh_mall.service.mobile.channel.ChannelService;

@RestController
@RequestMapping(value = "api/export")
public class ExportChannelExcel {
	private static final Logger logger = LoggerFactory.getLogger(ExportChannelExcel.class);
	
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ExportService exportService;
	
	@RequestMapping(value = "/exportChannelExcel", method = RequestMethod.GET)
	public void exportExternalExcel(HttpServletResponse response, String LoginName, String orderNo, Long startTime, Long endTime) throws Exception {
		logger.info("分销会员详情导出");
		Map<String,Object> param = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(LoginName)){
			param.put("LIKE_buyUserAccount", LoginName);
		}
		if(!StringUtils.isEmpty(orderNo)){
			param.put("LIKE_buyOrderNo", orderNo);
		}
		if(!StringUtils.isEmpty(startTime)){
			param.put("GTE_createTime", new Date(startTime));
		}
		if(!StringUtils.isEmpty(endTime)){
			param.put("LTE_createTime", new Date(endTime));
		}
		List<CommissionFlow> list = channelService.getChannelExport(param);
		// 获得workbook对象
		HSSFWorkbook workbook = exportService.exportChannelExcel(list);
		Date currentDate = DateUtil.getCurrentDate();
		String dateTime = DateUtil.convertToString(currentDate);
		String fileName = "分销会员详情导出表" + dateTime + ".xlsx";
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1")
				+ DateUtil.getCurrentDate() + ".xls");
		OutputStream os = response.getOutputStream();
		workbook.write(os);
		workbook.close();
		os.close();
	}
}
