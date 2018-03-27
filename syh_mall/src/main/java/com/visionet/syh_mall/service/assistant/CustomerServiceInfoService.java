package com.visionet.syh_mall.service.assistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.SysConstants;
import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.assistant.CustomerServiceInfo;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.assistant.CustomerServiceInfoRepository;
import com.visionet.syh_mall.repository.mobile.ShopRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.vo.CustomerServiceInfoVo;

/**
 * @ClassName: CustomerServiceInfoService
 * @Description: 客服service
 * @author chenghongzhan
 * @date 2017年8月28日 下午5:09:07
 */
@Service
public class CustomerServiceInfoService extends BaseService {
	@Autowired
	private CustomerServiceInfoRepository CustomerServiceDao;
	@Autowired
	private ShopRepository shopDao;
	@Autowired
	private CustomerServiceInfoRepository cusDao;

	/**
	 * @Title: getAssistants @Description: 检索客服列表的方法 @param @param
	 *         shopId @param @param customerServiceType @param @return
	 *         设定文件 @return List<Map<String,Object>> 返回类型 @throws
	 */
	public List<Map<String, Object>> getAssistants(Map<String, Object> param) throws Exception {
		// 返回前端的客服列表的结果集
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// 返回该店铺ID的所有客服ID
		if (!StringUtils.isEmpty(param.get("shopID"))) {
			// 搜索该店铺
			Shop shop = shopDao.findById(String.valueOf(param.get("shopID")));
			// 通过店铺雇主找到所有的客服
			List<String> ids = cusDao.findCusIdByUserId(shop.getUserId());
			// 如果没有客服直接返回空
			if (ids.size() <= 0) {
				return result;
			}
			param.put("ids", ids);
		}
		// 按条件查询客服返回的结果集
		Map<String, SearchFilter> filter = SearchFilter.parse(DynamicParamConvert.getAssistants(param));
		Iterable<CustomerServiceInfo> customers = CustomerServiceDao
				.findAll(DynamicSpecifications.bySearchFilter(filter.values(), CustomerServiceInfo.class));
		// 给前端返回的结果
		for (CustomerServiceInfo customerService : customers) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(customers)) {
				map.put("customeServiceID", customerService.getId());
				map.put("customeServiceName", customerService.getCustomerName());
				map.put("customeServiceQQ", customerService.getCustomerQq());
				map.put("customeServiceType", customerService.getCustomerType());
				map.put("customeServiceDutyType", customerService.getDutyType());
				map.put("startTime", customerService.getStartTime());
				map.put("endTime", customerService.getEndTime());
				result.add(map);
			}
		}
		return result;
	}

	/**
	 * 
	 * @Title: addAssistant @Description: 添加/编辑客服的方法 @param @param param
	 *         设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void addAssistant(CustomerServiceInfoVo serviceInfoVo, String Id) {
		if (StringUtils.isEmpty(serviceInfoVo.getCustomerServiceID())) {
			CustomerServiceInfo serviceInfo = new CustomerServiceInfo();
			CustomerServiceDao.save(serviceInfoVo.convertPo(serviceInfoVo, serviceInfo));
			return;
		}
		CustomerServiceInfo serviceInfo = CustomerServiceDao.findById(serviceInfoVo.getCustomerServiceID());
		if (StringUtils.isEmpty(serviceInfo)) {
			throw new RestException("没有该客服");
		}
		serviceInfo.setUpdateTime(DateUtil.getCurrentDate());
		CustomerServiceDao.save(serviceInfoVo.convertPo(serviceInfoVo, serviceInfo));
	}

	/**
	 * @Title: delAssistant @Description: 删除客服的方法 @param @param
	 *         customerServiceID 设定文件 @return void 返回类型 @throws
	 */
	public void delAssistant(String customerServiceID) {
		CustomerServiceInfo customerService = CustomerServiceDao.findById(customerServiceID);
		if (StringUtils.isEmpty(customerService)) {
			throw new RestException("没有该客服");
		}
		customerService.setIsDeleted(SysConstants.DELETE_FLAG_YES);
		customerService.setUpdateTime(DateUtil.getCurrentDate());
		CustomerServiceDao.save(customerService);
	}

}
