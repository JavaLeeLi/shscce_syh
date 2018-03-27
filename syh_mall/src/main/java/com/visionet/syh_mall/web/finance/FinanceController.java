package com.visionet.syh_mall.web.finance;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.file.FileUtil;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.finance.StatementOrder;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.account.FreezeRecordService;
import com.visionet.syh_mall.service.finance.FinanceService;
import com.visionet.syh_mall.service.thridAccount.SoaCheckAccountFileService;
import com.visionet.syh_mall.vo.finance.FinanceVo;
import com.visionet.syh_mall.vo.finance.FundSummaryPo;
import com.visionet.syh_mall.vo.finance.FundSummaryVo;
import com.visionet.syh_mall.web.BaseController;
/**
 * 财务模块控制层
 * @author mulongfei
 * @date 2017年10月20日下午3:29:17
 */
@RestController
@RequestMapping(value="/api/account")
public class FinanceController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(FinanceController.class);
	
	@Autowired
	private FinanceService financeService;
	@Autowired
	private SoaCheckAccountFileService checkAccFileService;
	@Autowired
	private FreezeRecordService freezeRecordService;

	
	/**
	 * 获取流水明细
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="/getFinanceRecord",method=RequestMethod.POST)
	public BaseReturnVo<Object> getFinanceRecord(@RequestBody FinanceVo vo){
		logger.info("获取流水明细：{}",vo);
		System.out.println(vo);
		Map<String,Object> record = null;
		try {
			record= financeService.getFinanceRecord(vo);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(record);
	}
	
	/**
	 * 解绑银行卡
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="unbindBankCard",method=RequestMethod.POST)
	public BaseReturnVo<Object> unbindBankCard(@RequestBody Map<String,Object> map){
		logger.info("接触绑定银行卡:{}",map);
		String bankCard = (String) map.get("bankCard");
		String userId = getCurrentUserId();
		try {			
			financeService.unbindBankCard(bankCard,userId);
		} catch (RestException e) {
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return new BaseReturnVo<Object>("成功");
	}
	
	/**
	 * 账户余额
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="/getAccountBalance",method=RequestMethod.POST)
	public BaseReturnVo<Object> getAccountBalance(@RequestBody FinanceVo vo){
		logger.info("获取用户账户信息：{}",vo);
		Map<String,Object> record = null;
		try {
			record= financeService.getAccountBalance(vo);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(record);
	}
	/**
	 * 获得提现记录
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="/getWithdrawalRecord",method=RequestMethod.POST)
	public BaseReturnVo<Object> getWithdrawalRecord(@RequestBody FinanceVo vo){
		logger.info("获得提现记录:{}",vo);
		Map<String, Object> record = null;
		try {
			record = financeService.getWithdrawalRecord(vo);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(record);
	}
	/**
	 * 获取保证金
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="/getCashDeposit",method=RequestMethod.POST)
	public BaseReturnVo<Object> getCashDeposit(@RequestBody FinanceVo vo){
		logger.info("获取保证金:{}",vo);
		Map<String, Object> cashDeposit = null;
		try {
			cashDeposit = financeService.getCashDeposit(vo);
		} catch (Exception e) {
			logger.error("获取保证金异常：{}",e.getMessage());
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(cashDeposit);
	}
	
	@RequiresAuthentication
	@RequestMapping(value="/paymentDeposit",method=RequestMethod.POST)
	public BaseReturnVo<Object> paymentCashDeposit(@RequestBody FinanceVo vo){
		logger.info("缴纳保证金:{}",vo);
		Map<String,Object> result = null;
		try {
			result=financeService.paymentCashDeposit(vo,getCurrentUserId());
		} catch (Exception e) {
			logger.error("缴纳保证金异常：{}",e.getMessage());
			e.printStackTrace();
			sysException();
		}
		return new BaseReturnVo<Object>(result);
	}
	
	/**
	 * 扣除保证金
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequestMapping(value="/deductCashDeposit",method=RequestMethod.POST)
	public BaseReturnVo<Object> deductCashDeposit(@RequestBody Map<String,Object> param){
		logger.info("扣除保证金：{}",param);
		try {
			financeService.deductCashDeposit(param);
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
	
	/**
	 * 编辑保证金
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="/editCashDeposit",method=RequestMethod.POST)
	@Log(name="编辑保证金",model="保证金模块")
	public BaseReturnVo<Object> editCashDeposit(@RequestBody Map<String,Object> param){
		logger.info("编辑保证金：{}");
		financeService.editCashDeposit(param,getCurrentUserId());
		return new BaseReturnVo<Object>("成功！");
	}
	
	/**
	 * 获取对账文件
	 * @param
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping(value="/getCheckAccountFile",method=RequestMethod.GET)
	public BaseReturnVo<Object> getCheckAccountFile(HttpServletRequest request,HttpServletResponse response) {
		logger.info("获取对账文件");
		Long paramDate = Long.valueOf(request.getParameter("date"));
		if(StringUtils.isEmpty(paramDate)){
			return BaseReturnVo.success("日期不能为空");
		}
		String result = null;
		InputStream input = null;
		OutputStream ops = null;
		try {
			Date date = new Date(paramDate);
			result = checkAccFileService.getCheckAccountFile(DateUtil.convertToString(date, DateUtil.YMD3));
			URL url = new URL(result);
			URLConnection urlConn = url.openConnection();
			input = urlConn.getInputStream();
			String filename = FileUtil.getShortFileName(url.getPath());	//文件名
			//文件下载响应头
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			response.setContentType("application/octet-stream");
			byte[] fileByte = org.aspectj.util.FileUtil.readAsByteArray(input);
			ops = response.getOutputStream();
			ops.write(fileByte);
		} catch (RestException e) {
			logger.error("获取对账文件：{}",e);
			e.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED, e.getMessage());
		} catch (Exception e) {
			logger.error("获取对账文件：{}",e.getMessage());
			e.printStackTrace();
			sysException();
		}finally{
			try {
				if (input != null) {    
					input.close();    
	            }    
	            if (ops != null) {    
	            	ops.close();    
	            }   
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	} 
	
	
	/**
	 * 获取对账结果
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequestMapping(value="/comporeAccountFile",method=RequestMethod.POST)
	public BaseReturnVo<Object> comporeAccountFile(@RequestBody Map<String,Object> param){
		logger.info("获取对账文件比对结果：{}",param);
		Map<String,Object> returnMap = new HashMap<String, Object>();
		Long paramDate = Long.valueOf(String.valueOf(param.get("date")));
		if(StringUtils.isEmpty(paramDate)){
			return BaseReturnVo.success("日期不能为空");
		}
		Date date = new Date(paramDate);
		Page<StatementOrder>  pageList = null;
		try {
			PageInfo pageInfo = getPageInfo(param, Direction.DESC, "comporeResult");
			pageList = checkAccFileService.getAccountFileResult(DateUtil.convertToString(date, DateUtil.YMD1),pageInfo);
			returnMap.put("itemCount", pageList.getTotalElements());
			returnMap.put("pageCount", pageList.getTotalPages());
			returnMap.put("curPageIndex", pageInfo.getPageIndex());
			returnMap.put("hasNext", pageList.hasNext());
			returnMap.put("orderInfos", pageList.getContent());
		} catch (RestException e) {
			logger.error("获取对账文件：{}",e);
			e.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BaseReturnVo<Object>(returnMap);
	}
	
	
	/**
	 * 资金冻结
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequestMapping(value="/freezeFund",method=RequestMethod.POST)
	@Log(name="资金冻结",model="财务模块")
	public BaseReturnVo<Object> freezeFund(@RequestBody Map<String,Object> map){
		logger.info("资金冻结:{}",map);
		Integer amt = (Integer) map.get("amt");
		BigDecimal freezeAmt = new BigDecimal(amt);
		String userLoginName = (String)map.get("userLoginName");
		String userId = freezeRecordService.getUserId(userLoginName);
		try {
			freezeRecordService.saveFreezdRecord(userId, userId, freezeAmt);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
	
	/**
	 * 资金解冻
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequestMapping(value="/unfreezeFund",method=RequestMethod.POST)
	@Log(name="资金解冻",model="财务模块")
	public BaseReturnVo<Object> unfreezeFund(@RequestBody Map<String,Object> map){
		logger.info("资金解冻:{}",map);
		String userLoginName = (String)map.get("userLoginName");
		String userId = freezeRecordService.getUserId(userLoginName);
		try {
			freezeRecordService.unfreezeAmt(userId, userId);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
	
	/**
	 * 催缴保证金
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="/paymentCashDeposit",method=RequestMethod.POST)
	@Log(name="催缴保证金",model="保证金模块")
	public BaseReturnVo<Object> paymentCashDeposit(@RequestBody Map<String,Object> param) {
		logger.info("催缴保证金：{}",param);
		try {
			financeService.paymentCashDeposit(param,getCurrentUserId());			
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return new BaseReturnVo<Object>("成功");
	}
	
	/**
	 * 获取收入明细
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="/searchIncome",method=RequestMethod.POST)
	public BaseReturnVo<Object> searchIncome(@RequestBody Map<String,Object> param){
		logger.info("收入统计:{}",param);
		Map<String,Object> result = null;
		try {
			result = financeService.searchIncomePage(param);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}
	
	@RequiresAuthentication
	@RequestMapping(value="/searchFundSummary",method=RequestMethod.POST)
	public BaseReturnVo<Object> searchFundSummary(@RequestBody FundSummaryPo po){
		logger.info("资金汇总:{}",po);
		FundSummaryVo result = null;
		try {
			result = financeService.searchFundSummary(po);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}
}
