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
import com.visionet.syh_mall.service.export.ExportEvaluationRestVo;
import com.visionet.syh_mall.service.export.ExportService;
import com.visionet.syh_mall.service.goods.EvaluationResultService;

/**
 * 导出鉴评
 * @author mulongfei
 * @date 2018年1月22日上午10:53:38
 */
@RestController
@RequestMapping(value="/api/export")
public class ExportEvaluationExcel {
	private static final Logger logger = LoggerFactory.getLogger(ExportEvaluationExcel.class);
	
	@Autowired
	private EvaluationResultService evaluationResultService;
	@Autowired
	private ExportService exportService;
	
	@RequestMapping(value="exportEvaluationExcel",method=RequestMethod.GET)
	public void exportEvaluationExcel(HttpServletResponse response,String typeCode,String collectTypeCode,Long startTime,Long endTime,String factorCode,String division) throws Exception{
		logger.info("导出鉴评结果:{}");
		Map<String,Object> param = new HashMap<String,Object>();
		if (!StringUtils.isEmpty(typeCode)) {
			param.put("EQ_evaluationTypeCode", typeCode);
		}
		if (!StringUtils.isEmpty(collectTypeCode)) {
			param.put("EQ_collectTypeCode", collectTypeCode);
		}
		if (!StringUtils.isEmpty(factorCode)) {
			param.put("LIKE_factorCode", factorCode);
		}
		if (!StringUtils.isEmpty(division)) {
			param.put("LIKE_evaluationDivision", division);
		}
		if (!StringUtils.isEmpty(startTime)) {
			param.put("GTE_createTime", new Date(startTime));
		}
		if (!StringUtils.isEmpty(endTime)) {
			param.put("LTE_createTime", new Date(endTime));
		}
		param.put("EQ_isDeleted", 0);
		List<ExportEvaluationRestVo> list = evaluationResultService.exportEvaluationExcel(param);
		//获得workbook对象
		HSSFWorkbook workbook = exportService.exportEvaluationExcel(list);
		Date currentDate = DateUtil.getCurrentDate();
		String dateTime = DateUtil.convertToString(currentDate);
		String fileName = "鉴评导出表"+dateTime+".xlsx";
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="  
		        + new String(fileName.getBytes(),"iso-8859-1")+ DateUtil.getCurrentDate() + ".xls");
		OutputStream os = response.getOutputStream();
		workbook.write(os);
		workbook.close();
		os.close();
	}
	
	@RequestMapping(value="exportOneEvaluationExcel",method=RequestMethod.GET)
	public void exportEvaluationExcel(HttpServletResponse response,String id) throws Exception{
		logger.info("导出单条鉴评结果:{}");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("EQ_id", id);
		param.put("EQ_isDeleted", 0);
		List<ExportEvaluationRestVo> list = evaluationResultService.exportEvaluationExcel(param);
		//获得workbook对象
		HSSFWorkbook workbook = exportService.exportEvaluationExcel(list);
		String fileName = list.get(0).getCollectCode()+".xlsx";
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="  
				+ new String(fileName.getBytes(),"iso-8859-1")+ DateUtil.getCurrentDate() + ".xls");
		OutputStream os = response.getOutputStream();
		workbook.write(os);
		workbook.close();
		os.close();
	}
}
