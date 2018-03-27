package com.visionet.syh_mall.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.entity.order.TradeSetting;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.TradeSettingRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.vo.TradeSettingVo;

/**
 * @ClassName: TradeSettingService
 * @Description: 交易设置
 * @author chenghongzhan
 * @date 2017年9月6日 上午11:03:13
 *
 */
@Service
public class TradeSettingService extends BaseService {

	@Autowired
	private TradeSettingRepository tradeSettingDao;

	/**
	 * @Title: getTradeSetting @Description: 搜索交易设置 @param @return 设定文件 @return
	 *         List<Map<String,Object>> 返回类型 @throws
	 */
	public List<Map<String, Object>> getTradeSetting() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Iterable<TradeSetting> tradeSetting = tradeSettingDao.findAll();
		for (TradeSetting trade : tradeSetting) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("settingID", trade.getId());
			map.put("closeOrderInterval", trade.getCloseOrderInterval());
			map.put("completeOrderInterval", trade.getCompleteOrderInterval());
			map.put("closeFeedbackInterval", trade.getCloseFeedbackInterval());
			map.put("closeServiceInterval", trade.getCloseServiceInterval());
			map.put("applyReviewInterval", trade.getApplyReviewInterval());
			map.put("closeCancInterval", trade.getCloseCancInterval());
			map.put("refundReviewInterval", trade.getRefundReviewInterval());
			result.add(map);
		}
		return result;
	}

	/**
	 * @Title: editTradeSetting @Description: 编辑交易设置 @param @param
	 *         param @param @param adminId 设定文件 @return void 返回类型 @throws
	 */
	public void editTradeSetting(TradeSettingVo settingVo, String adminId) {
		TradeSetting setting = tradeSettingDao.findOne(settingVo.getSettingID());
		if (StringUtils.isEmpty(setting)) {
			throw new RestException("没有该交易设置");
		}
		tradeSettingDao.save(settingVo.converPo(settingVo, setting, adminId));
	}

}
