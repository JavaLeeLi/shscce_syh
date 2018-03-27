package com.visionet.syh_mall.service.mbr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.mbr.IntegralFlow;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mbr.MbrIntegralFlowRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.vo.goods.MbrIntegeralVo;

/**
 * @ClassName: MbrService
 * @Description:会员积分管理模块service层
 * @author chenghongzhan
 * @date 2017年9月19日 下午1:48:44
 *
 */
@Service
public class MbrService extends BaseService {
	@Autowired
	private UserRepository userDao;
	@Autowired
	private UserRepository mbrDao;
	@Autowired
	private MbrIntegralFlowRepository mbrIntegralFlowDao;

	/**
	 * @Title: searchMbrIntegral @Description: 查询会员积分 @param @param
	 *         param @param @param pageInfo @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> searchMbrIntegral(Map<String, Object> param, PageInfo pageInfo) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, SearchFilter> parse = SearchFilter.parse(DynamicParamConvert.searchMbrIntegral(param));
		Page<User> findAll = userDao.findAll(DynamicSpecifications.bySearchFilter(parse.values(), User.class),
				buildPageRequest(pageInfo));
		for (User user : findAll) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mbrID", user.getId());
			map.put("mbrName", user.getLoginName());
			map.put("mbrValidIntegral", user.getUserValidIntegral());
			map.put("mbrTotalIntegral", user.getUserTotalIntegral());
			map.put("mbrRegisterTime", DateUtil.convertToString(user.getCreateTime(), DateUtil.YMD_FULL));
			list.add(map);
		}
		returnMap.put("itemCount", findAll.getTotalElements());
		returnMap.put("pageCount", findAll.getTotalPages());
		returnMap.put("curPageIndex", pageInfo.getPageIndex());
		returnMap.put("hasNext", findAll.hasNext());
		returnMap.put("mbrInfos", list);
		return returnMap;
	}

	/**
	 * @Title: editMbr @Description: 编辑会员积分 @param @param MbrId @param @param
	 *         Num 设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void editMbrIntegral(MbrIntegeralVo integeralVo,String adminId) {
		if (integeralVo.getIntegralNum() == 0) {
			return;
		}
		User user = mbrDao.findById(integeralVo.getMbrID());
		if (StringUtils.isEmpty(user)) {
			throw new RestException("不存在的会员");
		}
		Integer userValidIntegral = integeralVo.getIntegralNum() + user.getUserValidIntegral();
		Integer userTotalIntegral = integeralVo.getIntegralNum() + user.getUserTotalIntegral();
		user.setUserTotalIntegral(userTotalIntegral);
		user.setUserValidIntegral(userValidIntegral);
		if (userValidIntegral < 0) {
			user.setUserTotalIntegral(user.getUserTotalIntegral());
			user.setUserValidIntegral(0);
		}
		user.setUpdateTime(DateUtil.getCurrentDate());
		mbrDao.save(user);

		IntegralFlow flow = new IntegralFlow();
		if (integeralVo.getIntegralNum() < 0) {
			flow.setType(1);
			flow.setIngegralNum(-integeralVo.getIntegralNum());
		}
		if (integeralVo.getIntegralNum() > 0) {
			flow.setType(0);
			flow.setIngegralNum(integeralVo.getIntegralNum());
		}
		flow.setOperateDesc(integeralVo.getOperateDesc());
		flow.setUserId(integeralVo.getMbrID());
		mbrIntegralFlowDao.save(flow);
		addLog(null, adminId, "编辑会员积分");
	}

	/**
	 * @Title: getMbrIntegral @Description: 获取积分流水 @param @param
	 *         MbrId @param @param pageInfo @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> getMbrIntegralJournal(String mbrId, PageInfo pageInfo) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		User user = mbrDao.findOne(mbrId);
		if (StringUtils.isEmpty(user)) {
			throw new RestException("不存在的用户");
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, SearchFilter> parse = SearchFilter.parse(DynamicParamConvert.getMbrIntegralFlow(mbrId));
		Page<IntegralFlow> findOneMbrIntegral = mbrIntegralFlowDao.findAll(
				DynamicSpecifications.bySearchFilter(parse.values(), IntegralFlow.class), buildPageRequest(pageInfo));
		for (IntegralFlow integralFlow : findOneMbrIntegral) {
			Map<String, Object> integralFlowMap = new HashMap<String, Object>();
			integralFlowMap.put("mbrID", mbrId);
			integralFlowMap.put("mbrName", user.getLoginName());
			integralFlowMap.put("integralNum", integralFlow.getIngegralNum());
			integralFlowMap.put("journalType", integralFlow.getType());
			integralFlowMap.put("journalDesc", integralFlow.getOperateDesc());
			integralFlowMap.put("journalTime",
					DateUtil.convertToString(integralFlow.getCreateTime(), DateUtil.YMD_FULL));
			list.add(integralFlowMap);
		}
		returnMap.put("itemCount", findOneMbrIntegral.getTotalElements());
		returnMap.put("pageCount", findOneMbrIntegral.getTotalPages());
		returnMap.put("curPageIndex", pageInfo.getPageIndex());
		returnMap.put("hasNext", findOneMbrIntegral.hasNext());
		returnMap.put("mbrInfos", list);
		return returnMap;
	}
}
