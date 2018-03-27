package com.visionet.syh_mall.repository.admin;

import com.visionet.syh_mall.entity.admin.ModuleRole;
import com.visionet.syh_mall.repository.BaseRepository;

public interface ModuleRoleRepository extends BaseRepository<ModuleRole, String> {
	
	public int deleteByRoleId(String roleId);
}
