package com.visionet.syh_mall.repository.admin;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.admin.SystemModule;
import com.visionet.syh_mall.repository.BaseRepository;

public interface SystemModuleRepository extends BaseRepository<SystemModule, String> {
	
	@Query("select m from Role r,SystemModule m,ModuleRole mr where mr.roleId = r.id and mr.moduleId = m.id and r.id = ?1 and m.parentModuleId is null order by m.moduleSort ASC")
	List<SystemModule> findListByRoleId(String roleId);
	
	@Query("select m from Role r,SystemModule m,ModuleRole mr where mr.roleId = r.id and mr.moduleId = m.id and r.id = ?1 and m.parentModuleId = ?2 order by m.moduleSort ASC")
	List<SystemModule> findListByUserSubModuleId(String roleId,String parentModuleId);
	
	List<SystemModule> findByParentModuleId(String parentModuleId,Sort sort);
	
	List<SystemModule> findByParentModuleIdIsNull(Sort sort);
}
