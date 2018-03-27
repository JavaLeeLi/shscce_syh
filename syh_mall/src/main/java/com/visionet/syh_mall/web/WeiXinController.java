package com.visionet.syh_mall.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.entity.WeixinConfig;
import com.visionet.syh_mall.entity.WeixinOauth2Token;
import com.visionet.syh_mall.entity.WeixinUserInfo;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.CommonUtil;
import com.visionet.syh_mall.service.WeiXinService;
import com.visionet.syh_mall.web.mobile.goods.GoodsController;

/**
 * @Author DM
 * @version ：2017年10月17日下午4:29:33 实体类
 */
@RestController
@RequestMapping(value = "api/weixin")
public class WeiXinController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);
	@Autowired
	private WeiXinService weiXinService;

	@RequestMapping(value = "/getDetail")
	public BaseReturnVo<Object> getDetail(@RequestBody Map<String, Object> map) throws Exception {
		logger.info("获取微信关注人信息:{}", map);
		String accessToken = CommonUtil.getToken("wxc480ed072f7e31a0", "1cb4b7eae167f1190d6f3b8f94db5a79")
				.getAccessToken();
		WeixinUserInfo weixinUserInfo = null;
		try {
			weixinUserInfo = weiXinService.getUserInfo(accessToken, (String) map.get("openId"));
		} catch (RestException e) {
			e.printStackTrace();
			logger.info("获取微信关注人信息:{}", e.getMessage());
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(weixinUserInfo);
	}

	@RequestMapping(value = "/config",method=RequestMethod.POST)
	public ResponseEntity<?> weixinConfig(HttpServletRequest request, @RequestBody Map<String, Object> map)
			throws Exception {
		logger.info("微信分享:{}", map);
		String url = (String) map.get("url");
		if (url == null) {
			throw new RestException("url is null!");
		}
		return new ResponseEntity<WeixinConfig>(weiXinService.getSignature(url), HttpStatus.OK);
	}

	@RequestMapping(value = "/getOauth2AccessToken")
	public BaseReturnVo<Object> getOauth2AccessToken(@RequestBody Map<String, Object> map) throws Exception {
		logger.info("换取网页授权access_token:{}", map);
		WeixinOauth2Token weixinOauth2Token = null;
		try {
			weixinOauth2Token = weiXinService.getOauth2AccessToken((String) map.get("code"));
		} catch (RestException e) {
			e.printStackTrace();
			logger.info("通过code换取网页授权access_token:{}", e.getMessage());
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(weixinOauth2Token);
	}
	
}
