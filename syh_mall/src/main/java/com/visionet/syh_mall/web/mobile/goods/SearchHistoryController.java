package com.visionet.syh_mall.web.mobile.goods;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.entity.goods.SearchHistory;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.mobile.GoodsService;
import com.visionet.syh_mall.vo.GoodsQo;
import com.visionet.syh_mall.web.BaseController;
import com.visionet.syh_mall.web.mobile.message.MessageController;

/**
 *@Author DM
 *@version ：2017年9月12日下午2:52:56
 *实体类
 */
@RestController
@RequestMapping(value = "api/mbr")
public class SearchHistoryController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	@Autowired
	private GoodsService goodsService;
	/**
	 * 获取用户在平台中的搜索历史关键字
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHistoryKeywords",method=RequestMethod.POST)
	public Object getHistoryKeywords(@RequestBody GoodsQo qo) {
		logger.info("获取用户在平台中的搜索历史关键字:{}", qo);
		List<SearchHistory> list = null;
		try {
			qo.setCurrentUserId(getCurrentUserId());
			list = goodsService.getHistoryKeywords(qo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaseReturnVo<List<SearchHistory>> returnVo = new BaseReturnVo<List<SearchHistory>>(list);
		return returnVo;
	}
	/**
	 * 删除用户在平台中的搜索历史
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delHistoryKeywords",method=RequestMethod.POST)
	@Log(name="删除用户平台的搜索记录",model="搜索记录模块")
	public BaseReturnVo<Object> delHistoryKeywords(@RequestBody GoodsQo qo) throws Exception {
		logger.info("删除用户在平台中的搜索历史:{}", qo);
		try {
			goodsService.delHistoryKeywords(qo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常,修改失败");
		}
		return BaseReturnVo.success("删除成功");
	}
}
