package com.visionet.syh_mall.repository;

import java.util.List;

import com.visionet.syh_mall.entity.KeyMapping;

public interface KeyMappingRepository extends BaseRepository<KeyMapping, String> {
	/**
	 * 通过keyCode查找对象
	 * 
	 * @param keyCode
	 * @return
	 */

	public KeyMapping findByKeyCode(String keyCode);
	
	public List<KeyMapping>findByDdGroupCode(int ddGroupCode);
	
}
