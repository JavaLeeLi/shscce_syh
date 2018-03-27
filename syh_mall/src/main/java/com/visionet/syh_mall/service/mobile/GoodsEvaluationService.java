package com.visionet.syh_mall.service.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.SysConstants;
import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAuthentication;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.GoodsDraft;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.UserAuthenticationRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mobile.GoodsDraftRepository;
import com.visionet.syh_mall.repository.mobile.GoodsEvaluationRepository;
import com.visionet.syh_mall.service.BaseService;

/**
 * @ClassName: GoodsEvaluationService
 * @Description: 商品鉴评模块service层
 * @author chenghongzhan
 * @date 2017年9月18日 下午8:31:48
 *
 */
@Service
public class GoodsEvaluationService extends BaseService {
	@Autowired
	private GoodsEvaluationRepository goodsEvaluationDao;
	@Autowired
	private UserAuthenticationRepository userAuthenDAO;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private KeyMappingRepository keyMappingDao;
	@Autowired
	private GoodsDraftRepository goodsDraftDao;

	/**
	 * @Title: selectEvaluation @Description: 搜索鉴评结果记录 @param @param
	 *         param @param @param pageInfo @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> searchRecognizeResult(Map<String, Object> param, PageInfo pageInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		result.put("itemCount", 0);
		result.put("pageCount", 0);
		result.put("curPageIndex", pageInfo.getPageIndex());
		result.put("hasNext", false);
		result.put("goodsInfos", list);
		if (!StringUtils.isEmpty(param.get("customerName"))) {
			List<String> userIds = userAuthenDAO.findByUserName((String) param.get("customerName"));
			if (userIds.size() <= 0) {
				return result;
			}
			param.put("ids", userIds);
		}
		Map<String, SearchFilter> filter = SearchFilter.parse(DynamicParamConvert.getSearchRecognize(param));
		Page<GoodsDraft> goodList = goodsDraftDao.findAll(
				DynamicSpecifications.bySearchFilter(filter.values(), GoodsDraft.class), buildPageRequest(pageInfo));
		for (GoodsDraft goods : goodList) {
			if (!"".equals(goods.getGoodsQualityCode())) {
				String goodsId = goods.getId();
				Goods good = goodsEvaluationDao.findById(goodsId);
				Map<String, Object> goodsMap = new HashMap<String, Object>();
				goodsMap.put("goodsID", goods.getId());
				goodsMap.put("goodsName", goods.getGoodsName());
				goodsMap.put("goodsIsRecognized", goods.getIsRecognized());
				goodsMap.put("goodsQualityCode", goods.getGoodsQualityCode());
				KeyMapping keyMapping = keyMappingDao.findByKeyCode(goods.getGoodsQualityCode());
				goodsMap.put("goodsQualityDesc", keyMapping.getValueDesc());
				goodsMap.put("goodsQualityScore", goods.getGoodsQualityScore());
				goodsMap.put("goodsRecognizeCode", goods.getRecognizedCode());
				goodsMap.put("goodsOwnerID", goods.getOwnerId());
				User user = userDao.findById(goods.getOwnerId());
				UserAuthentication authentication = userAuthenDAO.findByUserId(user.getId());
				goodsMap.put("goodsOwnerName", authentication.getUserRealName());
				goodsMap.put("goodsOwnerPhone", user.getPhone());
				goodsMap.put("recognizedIsPrint", 0);
				if (good != null) {
					goodsMap.put("recognizedIsPrint", good.getRecognizedIsPrint());
				}
				list.add(goodsMap);
			}
		}
		result.put("itemCount", goodList.getTotalElements());
		result.put("pageCount", goodList.getTotalPages());
		result.put("curPageIndex", pageInfo.getPageIndex());
		result.put("hasNext", goodList.hasNext());
		result.put("goodsInfos", list);
		return result;
	}

	/**
	 * @Title: delRecognize @Description: 删除鉴评记录 @param @param Id 设定文件 @return
	 *         void 返回类型 @throws
	 */
	public void delRecognizeResult(String Id, String userId) {
		Goods good = goodsEvaluationDao.findById(Id);
		if (!StringUtils.isEmpty(good)) {
			good.setIsDeleted(SysConstants.DELETE_FLAG_YES);
			good.setUpdateBy(userId);
			good.setUpdateTime(DateUtil.getCurrentDate());
			goodsEvaluationDao.save(good);
		}
		GoodsDraft goodsDraft = goodsDraftDao.findOne(Id);
		if (!StringUtils.isEmpty(goodsDraft)) {
			goodsDraftDao.delete(goodsDraft);
		}
		addLog(null, userId, "删除鉴评结果记录！");
	}
}
