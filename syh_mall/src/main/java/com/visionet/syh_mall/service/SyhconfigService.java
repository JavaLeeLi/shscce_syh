package com.visionet.syh_mall.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.entity.Syhconfig;
import com.visionet.syh_mall.repository.SyhconfigRepository;

@Service
public class SyhconfigService extends BaseService{
	@Autowired
	private SyhconfigRepository SyhconfigDao;
	/**
	 * 获取数据字典
	 * @param 
	 * @return Map<String,Object>
	 * @throws
	 */
	public List<Map<String,Object>> getDDInfo(Map<String,Object> map){
		Map<String,Object> reMap = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(map.get("ddGroupCode"))){
			reMap.put("EQ_ddGroupCode",map.get("ddGroupCode"));
		}
		if(!StringUtils.isEmpty(map.get("ddKeyCode"))){
			reMap.put("EQ_keyCode",map.get("ddKeyCode"));
		}
		Map<String, SearchFilter> parse = SearchFilter.parse(reMap);
		List<Syhconfig> findDDInfo = SyhconfigDao.findAll(DynamicSpecifications.bySearchFilter(parse.values(), Syhconfig.class));
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (Syhconfig syhconfig : findDDInfo) {
			Map<String,Object> syhInfo = new HashMap<String,Object>();
			syhInfo.put("ddRecordID", syhconfig.getId());
			syhInfo.put("ddKeyCode", syhconfig.getKeyCode());
			syhInfo.put("ddValueDesc", syhconfig.getValueDesc());
			syhInfo.put("ddGroupCode", syhconfig.getDdGroupCode());
			syhInfo.put("ddRemark", syhconfig.getDdRemark());
			list.add(syhInfo);
		}
		return list;
	}
}
