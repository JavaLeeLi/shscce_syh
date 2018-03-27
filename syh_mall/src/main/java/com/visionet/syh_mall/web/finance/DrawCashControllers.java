package com.visionet.syh_mall.web.finance;

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
import com.visionet.syh_mall.entity.account.DrawCash;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.finance.DrawCashRepository;
import com.visionet.syh_mall.service.finance.DrawCashService;
import com.visionet.syh_mall.vo.DrawVo;
import com.visionet.syh_mall.vo.goods.DrawCashVo;
import com.visionet.syh_mall.web.BaseController;
import com.visionet.syh_mall.web.account.RechargeController;

/**
 * 提现
 * 
 * @author xiaofb
 * @time 2017年10月23日
 */
@RestController
@RequestMapping("api/finances")
public class DrawCashControllers extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(RechargeController.class);
	@Autowired
	private DrawCashService drawCashService;
	@Autowired
	private DrawCashRepository drawCashDao;

	/**
	 * 提现审核
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/drawCashAuditing", method = RequestMethod.POST)
	@Log(name = "提现审核", model = "财务模块")
	public Object drawCashAuditing(@RequestBody DrawCashVo drawCashs) {
		logger.info("提现审核请求参数:{}", drawCashs);
		try {
			for (DrawVo drawVo : drawCashs.getDrawCashs()) {
				synchronized (this) {
					DrawCash drawCash = drawCashDao.findOne(drawVo.getDrawCashId());
					drawCashService.auditing(drawVo.getDrawCashId(), drawCash.getUserId(), "", drawVo.getTagName(),getCurrentUserId());
				}
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
