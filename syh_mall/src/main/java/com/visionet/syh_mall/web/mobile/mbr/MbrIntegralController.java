package com.visionet.syh_mall.web.mobile.mbr;

import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.mbr.MbrService;
import com.visionet.syh_mall.vo.goods.MbrIntegeralVo;
import com.visionet.syh_mall.web.BaseController;

/**
 * @ClassName: MbrIntegralController
 * @Description: 会员积分管理模块
 * @author chenghongzhan
 * @date 2017年9月18日 下午7:26:38
 *
 */
@RestController
@RequestMapping(value = "/api/mbr")
public class MbrIntegralController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(MbrIntegralController.class);
	@Autowired
	private MbrService mbrService;

	/**
	 * @Title: searchMbr @Description: 搜索会员积分列表 @param @param
	 *         map @param @return @param @throws Exception 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/searchMbrIntegral", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchMbrIntegral(@RequestBody Map<String, Object> param) throws Exception {
		logger.info("搜索会员积分列表:{}", param);
		Map<String, Object> mbrList = null;
		try {
			mbrList = mbrService.searchMbrIntegral(param, getPageInfo(param, Direction.DESC, "updateTime"));
		} catch (RestException e) {
			e.printStackTrace();
			logger.info("搜索会员积分列表异常:{}", e.getMessage());
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(mbrList);
	}

	/**
	 * @Title: editMbrIntegral @Description: 编辑会员积分 @param @param
	 *         map @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/editMbrIntegral", method = RequestMethod.POST)
	@Log(name="编辑会员积分",model="会员积分模块")
	public BaseReturnVo<Object> editMbrIntegral(@RequestBody @Valid MbrIntegeralVo integeralVo) {
		logger.info("编辑会员积分:{}", integeralVo.toString());
		try {
			mbrService.editMbrIntegral(integeralVo,getCurrentUserId());
		} catch (RestException e) {
			e.printStackTrace();
			logger.info("编辑会员积分异常:{}", e.getMessage());
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: getMbrIntegral @Description: 获取会员积分流水 @param @param
	 *         map @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/getMbrIntegralJournal", method = RequestMethod.POST)
	public BaseReturnVo<Object> getMbrIntegralJournal(@RequestBody Map<String, Object> param) {
		logger.info("获取会员积分流水:{}", param);
		Map<String, Object> mbrIntegral = null;
		try {
			mbrIntegral = mbrService.getMbrIntegralJournal((String) param.get("mbrID"),
					getPageInfo(param, Direction.DESC,"updateTime"));
		} catch (RestException e) {
			e.printStackTrace();
			logger.info("获取会员积分流水异常:{}", e.getMessage());
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(mbrIntegral);
	}
}
