package com.visionet.syh_mall.service.syhservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.syhservice.Syhservice;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.syhservice.SyhserviceRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.vo.SyhserviceVo;

/**
 * @ClassName: SyhserviceService
 * @Description: 添加/修改服务内容
 * @author chenghongzhan
 * @date 2017年9月18日 下午7:58:12
 *
 */
@Service
public class SyhserviceService extends BaseService{
	@Autowired
	private SyhserviceRepository syhserviceDao;
	@Autowired
	private KeyMappingRepository keyMappingDao;

	/**
	 * @Title: reviceSyhservice @Description: 添加/修改服务内容 @param @param
	 *         syhserviceVo 设定文件 @return void 返回类型 @throws
	 */
	public void reviceSyhservice(SyhserviceVo syhserviceVo,String adminId) {
		if (StringUtils.isEmpty(syhserviceVo.getServiceContentID())) {
			Syhservice syhservice = new Syhservice();
			syhservice.setCreateBy(syhserviceVo.getAdminUserID());
			syhservice.setUpdateBy(syhserviceVo.getAdminUserID());
			syhserviceDao.save(syhserviceVo.convertPo(syhserviceVo, syhservice));
			return;
		}
		Syhservice syhservice = syhserviceDao.findById(syhserviceVo.getServiceContentID());
		if (StringUtils.isEmpty(syhservice)) {
			throw new RestException("没有该服务内容");
		}
		syhservice.setUpdateBy(syhserviceVo.getAdminUserID());
		syhservice.setUpdateTime(DateUtil.getCurrentDate());
		syhserviceDao.save(syhserviceVo.convertPo(syhserviceVo, syhservice));
		addLog(null, adminId, "添加/修改服务内容!");
	}

	/**
	 * @Title: getSyhserviceList @Description: 按条件查询服务内容 @param @param
	 *         syhservice @param @return 设定文件 @return List<Map<String,Object>>
	 *         返回类型 @throws
	 */
	public List<Map<String, Object>> getSyhserviceList(Map<String, Object> param) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		Map<String, SearchFilter> parse = SearchFilter.parse(DynamicParamConvert.getSyhserviceList(param));
		List<Syhservice> syhservices = syhserviceDao
				.findAll(DynamicSpecifications.bySearchFilter(parse.values(), Syhservice.class));
		for (Syhservice syhservice : syhservices) {
			Map<String, Object> syhserviceMap = new HashMap<String, Object>();
			syhserviceMap.put("serviceTypeCode", syhservice.getServiceTypeCode());
			KeyMapping keyMapping = keyMappingDao.findByKeyCode(syhservice.getServiceTypeCode());
			if (StringUtils.isEmpty(keyMapping)) {
				throw new RestException("没有该类型的服务");
			}
			syhserviceMap.put("serviceID", syhservice.getId());
			syhserviceMap.put("serviceTypeDesc", keyMapping.getValueDesc());
			syhserviceMap.put("serviceDescType", syhservice.getServiceDescType());
			syhserviceMap.put("serviceDescTitle", syhservice.getServiceDescTitle());
			syhserviceMap.put("serviceDescContent", syhservice.getServiceDescContent());
			result.add(syhserviceMap);
		}
		return result;
	}
}
