package com.visionet.syh_mall.web.export;

import java.io.OutputStream;
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
import com.visionet.syh_mall.service.mobile.channel.ChannelService;
import com.visionet.syh_mall.vo.ChannelCollctVo;
import com.visionet.syh_mall.vo.GoodsQo;

@RestController
@RequestMapping(value = "/api/export")
public class ExportChannelCollectExcel {
	private static final Logger logger = LoggerFactory.getLogger(ExportAccountExcel.class);

	@Autowired
	private ChannelService channelService;
	@Autowired
	private ExportService exportService;

	@RequestMapping(value = "exportChannelCollectExcel", method = RequestMethod.GET)
	public void exportChannelCollectExcel(HttpServletResponse response, String loginName, String userName,
			String commissionRate, long startTime, long endTime) throws Exception {
		logger.info("分销汇总");
		GoodsQo qo = new GoodsQo();
		if (!StringUtils.isEmpty(loginName)) {
			qo.setLoginName(loginName);
		}
		if (!StringUtils.isEmpty(userName)) {
			qo.setUserName(userName);
		}
		if (!StringUtils.isEmpty(commissionRate)) {
			qo.setCommissionRate(commissionRate);
		}
		if (!StringUtils.isEmpty(startTime)) {
			qo.setStartTime(new Date(startTime));
		}
		if (!StringUtils.isEmpty(endTime)) {
			qo.setEndTime(new Date(endTime));
		}
		List<ChannelCollctVo> searchUserAccount = channelService.commissionSummary(qo);
		
		HSSFWorkbook workbook = exportService.exportChannelCollectExcel(searchUserAccount);
		Date currentDate = DateUtil.getCurrentDate();
		String dateTime = DateUtil.convertToString(currentDate);
		String fileName = "分销汇总导出表" + dateTime + ".xlsx";
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1")
				+ DateUtil.getCurrentDate() + ".xls");
		OutputStream os = response.getOutputStream();
		workbook.write(os);
		workbook.close();
		os.close();
	}
}
