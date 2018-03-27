package com.visionet.syh_mall.service.adminUser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.admin.ModuleRole;
import com.visionet.syh_mall.entity.admin.Role;
import com.visionet.syh_mall.entity.admin.SystemModule;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.admin.ModuleRoleRepository;
import com.visionet.syh_mall.repository.admin.RoleRepository;
import com.visionet.syh_mall.repository.admin.SystemModuleRepository;
import com.visionet.syh_mall.vo.ModuleVo;

/**
 * 角色service
 * @author xiaofb
 * @time 2017年8月18日
 */
@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private ModuleRoleRepository moduleRoleRepo;
	@Autowired
	private SystemModuleRepository sModuleRepository;
	
	/**
	 * 查找角色
	 * @param roleId
	 * @return
	 */
	public Role getRole(String roleId){
		return roleRepo.findOne(roleId);
	}
	
	/**
	 * 添加角色
	 * @param role
	 */
	@Transactional
	public void addSystemRole(Role role,List<String> moduleId){
		if(StringUtils.isEmpty(role.getRoleName())){
			throw new RestException(HttpStatus.ACCEPTED,"角色名不能为空");
		}
		//编辑时先删除原始角色关联
		if(!StringUtils.isEmpty(role.getId())){
			moduleRoleRepo.deleteByRoleId(role.getId());
		}
		role.setCreateTime(DateUtil.getCurrentDate());
		role.setUpdateTime(DateUtil.getCurrentDate());
		//保存角色
		role = roleRepo.save(role);
		//保存角色模块关联
		ModuleRole mr = null;
		for (int i = 0; i < moduleId.size(); i++) {
			mr = new ModuleRole();
			mr.setRoleId(role.getId());
			mr.setModuleId(moduleId.get(i));
			mr.setCreateTime(DateUtil.getCurrentDate());
			mr.setUpdateTime(DateUtil.getCurrentDate());
			moduleRoleRepo.save(mr);
		}
	}
	
	/**
	 * 系统模块列表
	 * @return
	 */
	public List<ModuleVo> getModulesList(){
		//排序字段
		Sort sort = new Sort(Direction.ASC, "moduleSort");
		//获取主菜单
		List<SystemModule> list = (List<SystemModule>) sModuleRepository.findByParentModuleIdIsNull(sort);
		List<ModuleVo> result =  ModuleVo.changeModel(list);
		List<ModuleVo> subMenu = null;
		//子菜单
		for(ModuleVo moduleVo : result){
			list = sModuleRepository.findByParentModuleId(moduleVo.getId(),sort);
			subMenu = ModuleVo.changeModel(list);
			moduleVo.setSubModules(subMenu);
		}
		return result;
	}
	
	/**
	 * 删除角色
	 * @param roleId
	 */
	@Transactional
	public void delRole(String roleId){
		//删除角色
		roleRepo.delete(roleId);
		//删除角色关联
		moduleRoleRepo.deleteByRoleId(roleId);
	}
	
	/**
	 * 角色列表
	 * @return
	 */
	public List<Role> getRoleList(){
		return (List<Role>) roleRepo.findAll();
	}
	
	/**
	 * 获取用户角色权限
	 * @param roleId
	 * @return
	 */
	public List<ModuleVo> getUserPermissions(String roleId){
		//一级菜单
		List<SystemModule> menu = sModuleRepository.findListByRoleId(roleId);
		//vo转换
		List<ModuleVo> mainModuleVo = ModuleVo.changeModel(menu);
		for (ModuleVo moduleVo : mainModuleVo) {
			menu = sModuleRepository.findListByUserSubModuleId(roleId, moduleVo.getId());
			moduleVo.setSubModules(ModuleVo.changeModel(menu));
		}
		return mainModuleVo;
	}
	
	public Role getRoleById(String id){
		return roleRepo.findOne(id);
	}
}
