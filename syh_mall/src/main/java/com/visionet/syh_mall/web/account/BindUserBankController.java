package com.visionet.syh_mall.web.account;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.entity.account.UserBank;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.account.BindUserBankService;
import com.visionet.syh_mall.web.BaseController;

/**
 * 用户银行卡绑定
 * @author xiaofb
 * @time 2017年10月20日
 */
@RestController
@RequestMapping("api/bindBank")
public class BindUserBankController extends BaseController {
	
	private final static Logger logger = LoggerFactory.getLogger(RechargeController.class);
	
	@Autowired
	private BindUserBankService userBankService;
	
	/**
	 * 请求绑定用户银行卡
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/requestBindBankCard", method = RequestMethod.POST)
	@Log(name="绑定银行卡",model="财务模块")
	public Object requestBindBankCard(@RequestBody Map<String,String> param){
		logger.info("请求绑定用户银行卡请求参数:{}", param);
		try {
			String userId = getCurrentUserId();
			String cardNo = param.get("cardNo");	//银行卡号
//			String phone = param.get("phone");	//预留手机号
			userBankService.insertUserBank(cardNo, userId);
		} catch (RestException re) {
			logger.error("请求绑定用户银行卡异常:{}",re);
			re.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED,re.getMessage());
		} catch (Exception e) {
			logger.error("请求绑定用户银行卡异常:{}",e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
	
	
	/**
	 * 确认绑定用户银行卡
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/comfirmBindUserBank", method = RequestMethod.POST)
	@Log(name="确定绑定银行卡",model="财务模块")
	public Object comfirmBindUserBank(@RequestBody Map<String,String> param){
		logger.info("确认绑定用户银行卡请求参数:{}", param);
		Map<String,String> result = null;
		try {
			String userId = getCurrentUserId();
			String tranceNum = param.get("tranceNum");
			String transDate = param.get("transDate");
			String phone = param.get("phone");
			String verificationCode = param.get("verificationCode");
			userBankService.commitBindCard(userId, tranceNum, transDate, phone, verificationCode);
			
		} catch (RestException re) {
			logger.error("确认绑定用户银行卡异常:{}",re);
			re.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED,re.getMessage());
		} catch (Exception e) {
			logger.error("确认绑定用户银行卡异常:{}",e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}
	
	/**
	 * 获取用户绑卡列表
	 * @param param
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/getUserBankList", method = RequestMethod.POST)
	public Object getUserBankList(){
		logger.info("获取用户绑卡列表:{}");
		List<UserBank> result = null;
		try {
			String userId = getCurrentUserId();
			result = userBankService.findUserBankList(userId);
		} catch (RestException re) {
			logger.error("确认绑定用户银行卡异常:{}",re);
			re.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED,re.getMessage());
		} catch (Exception e) {
			logger.error("确认绑定用户银行卡异常:{}",e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	} 
}
