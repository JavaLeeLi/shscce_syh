package com.visionet.syh_mall.web.finance;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.account.DrawCash;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.finance.DrawCashRepository;
import com.visionet.syh_mall.service.finance.DrawCashService;
import com.visionet.syh_mall.web.BaseController;
import com.visionet.syh_mall.web.account.RechargeController;

/**
 * 提现
 * 
 * @author xiaofb
 * @time 2017年10月23日
 */
@RestController
@RequestMapping("api/finance")
public class DrawCashController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(RechargeController.class);
	@Autowired
	private DrawCashService drawCashService;
	@Autowired
	private DrawCashRepository DrawCashDao;
	@Autowired
	private KeyMappingRepository keyMappingDao;

	@RequiresAuthentication
	@RequestMapping(value = "/withdrawalInstall", method = RequestMethod.POST)
	@Log(name = "提现设置", model = "财务模块")
	public BaseReturnVo<Object> withdrawalInstall(@RequestBody Map<String, Object> param) {
		logger.info("提现设置请求参数:{}", param);
		try {
			drawCashService.withdrawalInstall(param);
		} catch (RestException re) {
			logger.error("提现设置异常:{}", re);
			re.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED, re.getMessage());
		} catch (Exception e) {
			logger.error("提现设置异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
	
	
	@RequestMapping(value = "/getWithdrawals", method = RequestMethod.POST)
	@Log(name = "提现列表", model = "财务模块")
	public BaseReturnVo<Object> getWithdrawals(@RequestBody Map<String, String> param) {
		logger.info("提现设置请求参数:{}", param);
		List<Map<String, Object>> result=null;
		try {
			result=drawCashService.getWithdrawals();
		} catch (RestException re) {
			logger.error("提现设置异常:{}", re);
			re.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED, re.getMessage());
		} catch (Exception e) {
			logger.error("提现设置异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}
	/**
	 * 提现申请
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/applyWithdrawal", method = RequestMethod.POST)
	@Log(name = "提现申请", model = "财务模块")
	public BaseReturnVo<Object> drawCash(@RequestBody Map<String, String> param) {
		logger.info("提现申请请求参数:{}", param);
		try {
			KeyMapping keyMap1 = keyMappingDao.findByKeyCode("cash_num");
			KeyMapping keyMap2 = keyMappingDao.findByKeyCode("cash_fee");
			Map<String, Object> weekDays = getWeekDays(-0);
			Date beginDate = (Date) weekDays.get("beginDate");
			Date endDate = (Date) weekDays.get("endDate");

			// 一周提现次数
			String cashNum = keyMap1.getValueDesc();
			// 一次提现金额
			String cashFee = keyMap2.getValueDesc();
			String userId = getCurrentUserId();
			String amount = param.get("amount");
			String userBankId = param.get("userBankId");

			List<DrawCash> list = DrawCashDao.findByUserIdAndStatusCode(userId, beginDate, endDate);
			if (list.size() >= Integer.parseInt(cashNum)) {
				throw new RestException("本周可提现次数为:" + cashNum + "次");
			}
			BigDecimal cashFees = new BigDecimal(cashFee);
			BigDecimal amounts = new BigDecimal(amount);
			if (cashFees.compareTo(amounts) > 0) {
				throw new RestException("提现金额太小,最少提现金额为:" + cashFee + "元");
			}
			drawCashService.thridPayReqDrawCash(userId, amount, userBankId);
		} catch (RestException re) {
			logger.error("提现申请异常:{}", re);
			re.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED, re.getMessage());
		} catch (Exception e) {
			logger.error("提现申请异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	// 获取当前(上，下)周的日期范围如：...,-1上一周，0本周，1下一周...
	private static Map<String, Object> getWeekDays(int i) {
		Map<String, Object> result = new HashMap<String, Object>();
		// getTimeInterval(sdf);

		Calendar calendar1 = Calendar.getInstance();
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		calendar1.setFirstDayOfWeek(Calendar.MONDAY);

		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayOfWeek) {
			calendar1.add(Calendar.DAY_OF_MONTH, -1);
		}

		// 获得当前日期是一个星期的第几天
		int day = calendar1.get(Calendar.DAY_OF_WEEK);

		// 获取当前日期前（下）x周同星几的日期
		calendar1.add(Calendar.DATE, 7 * i);

		calendar1.add(Calendar.DATE, calendar1.getFirstDayOfWeek() - day);
		calendar1.set(Calendar.HOUR_OF_DAY, 0);
		calendar1.set(Calendar.SECOND, 0);
		calendar1.set(Calendar.MINUTE, 0);
		Date beginDate = calendar1.getTime();
		calendar1.add(Calendar.DATE, 6);
		calendar1.set(Calendar.HOUR_OF_DAY, 23);
		calendar1.set(Calendar.SECOND, 59);
		calendar1.set(Calendar.MINUTE, 59);

		Date endDate = calendar1.getTime();
		result.put("beginDate", beginDate);
		result.put("endDate", endDate);
		return result;
	}

    /**
     * 对公账户
     * @param param
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "/drawCashAction", method = RequestMethod.POST)
    @Log(name = "提现对公账户", model = "财务模块")
    public Object drawCashAction(@RequestBody Map<String, String> param) {
        logger.info("提现对公账户参数:{}", param);
        Map<String,String> result = new HashMap<>();
        try {
            KeyMapping keyMap1 = keyMappingDao.findByKeyCode("cash_num");
            KeyMapping keyMap2 = keyMappingDao.findByKeyCode("cash_fee");
            Map<String, Object> weekDays = getWeekDays(-0);
            Date beginDate = (Date) weekDays.get("beginDate");
            Date endDate = (Date) weekDays.get("endDate");


            // 一周提现次数
            String cashNum = keyMap1.getValueDesc();
            // 一次提现金额
            String cashFee = keyMap2.getValueDesc();
            String userId = getCurrentUserId();
            String amount = param.get("amount");
            List<DrawCash> list = DrawCashDao.findByUserIdAndStatusCode(userId, beginDate, endDate);
            if (list.size() >= Integer.parseInt(cashNum)) {
                throw new RestException("本周可提现次数为:" + cashNum + "次");
            }
			String personal = param.get("personal");
            BigDecimal cashFees = new BigDecimal(cashFee);
            BigDecimal amounts = new BigDecimal(amount);
            if (cashFees.compareTo(amounts) > 0) {
                throw new RestException("提现金额太小,最少提现金额为:" + cashFee + "元");
            }
            synchronized (this) {
				result.put("orderNo",drawCashService.auditing(userId,amount,personal));
            }
        } catch (RestException re) {
            logger.error("提现申请异常:{}", re);
            re.printStackTrace();
            throw new RestException(HttpStatus.ACCEPTED, re.getMessage());
        } catch (Exception e) {
            logger.error("提现申请异常:{}", e);
            e.printStackTrace();
            sysException();
        }
        return BaseReturnVo.success(result);
    }


	/**
	 * 提现审核
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/drawCashAuditing", method = RequestMethod.POST)
	@Log(name = "提现审核", model = "财务模块")
	public Object drawCashAuditing(@RequestBody Map<String, String> param) {
		logger.info("提现审核请求参数:{}", param);
		try {
			String drawCashId = param.get("drawCashId"); // 提现申请id
			String statusCode = param.get("statusCode"); // 状态编码
			String reason = param.get("reason");// 拒绝原因
			String userId = getCurrentUserId();
			synchronized (this) {
				drawCashService.auditing(drawCashId, userId, reason, statusCode, getCurrentUserId());
			}
		} catch (RestException re) {
			logger.error("提现申请异常:{}", re);
			re.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED, re.getMessage());
		} catch (Exception e) {
			logger.error("提现申请异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

}
