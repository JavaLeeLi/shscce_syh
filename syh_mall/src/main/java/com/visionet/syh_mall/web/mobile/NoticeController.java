package com.visionet.syh_mall.web.mobile;

import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.mobile.NoticeService;
import com.visionet.syh_mall.vo.NoticeVo;
import com.visionet.syh_mall.web.BaseController;

/**
 * @Author DM
 * @version ：2017年8月15日下午3:37:59 公告管理业务控制
 */
@RestController
@RequestMapping(value = "api/notice")
public class NoticeController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	@Autowired
	private NoticeService noticeService;

	/**
	 * @Title: queryList @Description: 搜索公告列表 @param @param
	 *         param @param @return @param @throws Exception 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/list")
	public BaseReturnVo<Object> queryList(@RequestBody Map<String, Object> param) {
		logger.info("web端查询公告列表:{}", param);
		Map<String, Object> result = null;
		try {
			result = noticeService.queryList(param, getPageInfo(param, Direction.DESC, "updateTime"));
		} catch (RestException e) {
			logger.error("查询公告列表异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("查询公告列表异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: queryNoticeDetail @Description: 搜索公告详情 @param @param
	 *         param @param @return @param @throws Exception 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/queryNoticeDetail")
	public BaseReturnVo<Object> queryNoticeDetail(@RequestBody Map<String, String> param) {
		logger.info("查询公告详情:{}", param);
		Map<String, Object> result = null;
		try {
			result = noticeService.queryNoticeDetail(param.get("noticeID"), getCurrentUserId());
		} catch (RestException e) {
			logger.error("查询公告详情异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("查询公告详情异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: reviseNotice @Description: 添加/编辑公告 @param @param
	 *         noticeVo @param @param result @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/revise", method = RequestMethod.POST)
	@Log(name="添加/编辑公告",model="首页模块")
	public BaseReturnVo<Object> reviseNotice(@RequestBody @Valid NoticeVo noticeVo, BindingResult result) {
		logger.info("添加/编辑公告:{}", noticeVo.toString());
		try {
			noticeService.reviceNotice(noticeVo, getCurrentUserId());
		} catch (RestException e) {
			logger.error("添加/编辑公告异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加/编辑公告异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: showNotice @Description: 搜索公告 @param @param param @param @return
	 *         设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/selectNotice", method = RequestMethod.POST)
	public BaseReturnVo<Object> selectNotice(@RequestBody Map<String, Object> param) {
		logger.info("搜索公告:{}", param);
		Map<String, Object> result = null;
		try {
			result = noticeService.selectNotice(param, getPageInfo(param, Direction.DESC, "updateTime"));
		} catch (RestException e) {
			logger.error("搜索公告异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索公告异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}
}
