package com.visionet.syh_mall.web.thridPayNotice;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAuthentication;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.UserAuthenticationRepository;
import com.visionet.syh_mall.service.UserService;
import com.visionet.syh_mall.service.thridAccount.SoaPasswordService;
import com.visionet.syh_mall.web.BaseController;

@RestController
@RequestMapping(value = "api/soaPassword")
public class SoaPasswordController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(SoaPasswordController.class);
	@Autowired
	private SoaPasswordService soaPasswordService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserAuthenticationRepository userAuthenticationDao;


	@RequiresAuthentication
	@RequestMapping(value = "/setPayPwd", method = RequestMethod.POST)
	public BaseReturnVo<Object> setPayPwd(@RequestBody Map<String, Object> param) {
		logger.info("设置支付密码:{}", param);
		Map<String, Object> result = null;
		try {
			// 登陆人用户
			User user = userService.findUserById(getCurrentUserId());

			// 登录人的认证信息
			UserAuthentication authentication = userAuthenticationDao.findByUserId(getCurrentUserId());
			if (authentication == null) {
				throw new RestException("该用户未经过认证");
			}
			String jumpUrl = (String) param.get("jumpUrl");
			
			//设置支付密码
			result = soaPasswordService.setPayPwd(user.getId(), user.getPhone(), authentication.getUserRealName(),
					Long.valueOf("1"), authentication.getIdCode(), jumpUrl);
		} catch (RestException e) {
			logger.error("设置支付密码异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("设置支付密码异常:{}", e);
			e.printStackTrace();
		}
		return new BaseReturnVo<Object>(result);
	}

	/**
	 * @Title: updatePayPwd @Description: 修改支付密码 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/updatePayPwd", method = RequestMethod.POST)
	public BaseReturnVo<Object> updatePayPwd(@RequestBody Map<String, Object> param) {
		logger.info("修改支付密码:{}", param);
		Map<String, Object> result = null;
		try {
			// 登陆人用户
			User user = userService.findUserById(getCurrentUserId());
			// 登录人的认证信息
			UserAuthentication authentication = userAuthenticationDao.findByUserId(getCurrentUserId());
			
			if (authentication == null) {
				throw new RestException("该用户未经过认证");
			}
			String jumpUrl = (String) param.get("jumpUrl");
			result=soaPasswordService.updatePayPwd(user.getId(), user.getPhone(), authentication.getUserRealName(),
					Long.valueOf("1"), authentication.getIdCode(), jumpUrl);
		} catch (RestException e) {
			logger.error("修改支付密码异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("修改支付密码异常:{}", e);
			e.printStackTrace();
		}
		return new BaseReturnVo<Object>(result);
	}

	
	
	
	/**
	 * @Title: resetPayPwd @Description: 重置支付密码 @param @param
	 * param @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/resetPayPwd", method = RequestMethod.POST)
	public BaseReturnVo<Object> resetPayPwd(@RequestBody Map<String, Object> param) {
		logger.info("重置支付密码:{}", param);
		Map<String, Object> result = null;
		try {
			// 登陆人用户
			User user = userService.findUserById(getCurrentUserId());

			// 登录人的认证信息
			UserAuthentication authentication = userAuthenticationDao.findByUserId(getCurrentUserId());
			if (authentication == null) {
				throw new RestException("该用户未经过认证");
			}
			String jumpUrl = (String) param.get("jumpUrl");
			result=soaPasswordService.resetPayPwd(user.getId(), user.getPhone(), authentication.getUserRealName(),
					Long.valueOf("1"), authentication.getIdCode(), jumpUrl);

		} catch (RestException e) {
			logger.error("重置支付密码异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("重置支付密码异常:{}", e);
			e.printStackTrace();
		}
		return new BaseReturnVo<Object>(result);
	}

}
