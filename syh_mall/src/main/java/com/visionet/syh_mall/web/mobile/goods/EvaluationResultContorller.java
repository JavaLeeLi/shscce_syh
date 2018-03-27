package com.visionet.syh_mall.web.mobile.goods;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.goods.EvaluationResultService;
import com.visionet.syh_mall.vo.goods.EvaluationResultVo;
import com.visionet.syh_mall.vo.goods.SearchEvaluationVo;
import com.visionet.syh_mall.web.BaseController;

@RestController
@RequestMapping(value = "api/evaluation")
public class EvaluationResultContorller extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(EvaluationResultContorller.class);
	@Autowired
	private EvaluationResultService evaluationResultService;

	/**
	 * 添加/编辑鉴评结果 @param @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "addEvaluation", method = RequestMethod.POST)
	public BaseReturnVo<Object> addEvaluation(@RequestBody EvaluationResultVo evaluationResultVo) {
		logger.info("添加/编辑鉴评结果:{}", evaluationResultVo);
		try {
			evaluationResultService.addEvaluation(evaluationResultVo,getCurrentUserId());
		} catch (RestException e) {
			e.printStackTrace();
			JSONObject jsonObject = JSONObject.parseObject(e.getMessage());
			if("login error".equals(jsonObject.get("msg").toString())){				
				throw new RestException("10022","会话失效");
			}
			throw new RestException(jsonObject.get("msg").toString());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return new BaseReturnVo<Object>("成功");
	}

	/**
	 * @Title: checkEvaluation @Description: 审核鉴评码 @param @param
	 * param @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "checkEvaluation", method = RequestMethod.POST)
	public BaseReturnVo<Object> checkEvaluation(@RequestBody Map<String, Object> param) {
		logger.info("审核鉴评码真伪:{}", param);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Boolean checkEvaluation = evaluationResultService.checkEvaluation(param);
			result.put("checkEvaluation", checkEvaluation);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return new BaseReturnVo<Object>(result);
	}

	/**
	 * 鉴评结果审核 @param @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "auditEvaluation", method = RequestMethod.POST)
	public BaseReturnVo<Object> auditEvaluation(@RequestBody Map<String, Object> map) {
		logger.info("鉴评结果审核:{}", map);
		try {
			evaluationResultService.auditEvaluation(map,getCurrentUserId());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return new BaseReturnVo<Object>("成功");
	}

	 /**
	 * 检索鉴评结果 @param @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/searchEvaluation", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchEvaluation(@RequestBody SearchEvaluationVo searchEvaluationVo) {
		logger.info("检索鉴评结果:{}", searchEvaluationVo);
		Map<String, Object> result = null;
		try {
			result = evaluationResultService.searchEvaluation(searchEvaluationVo);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return new BaseReturnVo<Object>(result);
	}

	/**
	 * 鉴评结果详情 @param @return BaseReturnVo<Object> @throws
	 */
	@RequestMapping(value = "evaluationDetail", method = RequestMethod.POST)
	public BaseReturnVo<Object> evaluationDetail(@RequestBody Map<String, Object> map) {
		logger.info("鉴评结果详情:{}", map);
		Map<String, Object> evaluationDetail = null;
		try {
			evaluationDetail = evaluationResultService.evaluationDetail(map);
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return new BaseReturnVo<Object>(evaluationDetail);
	}
	
	/**
	 * 鉴评结果查询 @param @return BaseReturnVo<Object> @throws
	 */
	@RequestMapping(value = "/evaluationResult", method = RequestMethod.POST)
	public BaseReturnVo<Object> evaluationResult(@RequestBody Map<String, Object> map) {
		logger.info("鉴评记录:{}", map);
		Map<String, Object> evaluationDetail = null;
		try {
			evaluationDetail = evaluationResultService.evaluationResult(map);
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return new BaseReturnVo<Object>(evaluationDetail);
	}

	/**
	 * 删除鉴评结果 @param @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "delEvaluation", method = RequestMethod.POST)
	public BaseReturnVo<Object> delEvaluation(@RequestBody EvaluationResultVo evaluationResultVo) {
		logger.info("删除鉴评结果:{}", evaluationResultVo);
		try {
			evaluationResultService.delEvaluation(evaluationResultVo);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return new BaseReturnVo<Object>("成功");
	}
}
