package com.visionet.syh_mall.web.mobile.goods;

import java.util.Map;

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
import com.visionet.syh_mall.service.mobile.GoodsEvaluationService;
import com.visionet.syh_mall.web.BaseController;

/**
 * @ClassName: GoodsEvaluationController
 * @Description: 商品鉴评模块
 * @author chenghongzhan
 * @date 2017年9月18日 下午8:53:14
 *
 */
@RestController
@RequestMapping(value = "/api/service")
public class GoodsEvaluationController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(GoodsEvaluationController.class);
	@Autowired
	private GoodsEvaluationService goodsEvaluationService;

	/**
	 * @Title: selectEvaluation @Description: 搜索鉴评结果 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/searchRecognizeResult", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchRecognizeResult(@RequestBody Map<String, Object> param) {
		logger.info("搜索鉴评结果记录:{}", param);
		Map<String, Object> result = null;
		try {
			result = goodsEvaluationService.searchRecognizeResult(param,
					getPageInfo(param, Direction.DESC, "updateTime"));
		} catch (RestException e) {
			logger.error("搜索鉴评结果异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索鉴评结果异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: delRecognizeResult @Description: 删除鉴评记录 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/delRecognizeResult", method = RequestMethod.POST)
	@Log(name = " 删除鉴评记录 ", model = "商品鉴评模块")
	public BaseReturnVo<Object> delRecognizeResult(@RequestBody Map<String, String> param) {
		logger.info("删除鉴评记录:{}", param);
		try {
			goodsEvaluationService.delRecognizeResult(param.get("goodsID"), getCurrentUserId());
		} catch (RestException e) {
			logger.error("删除鉴评记录异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除鉴评记录异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
}
