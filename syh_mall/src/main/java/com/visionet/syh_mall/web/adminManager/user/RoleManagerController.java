package com.visionet.syh_mall.web.adminManager.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.entity.admin.Role;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.adminUser.RoleService;
import com.visionet.syh_mall.vo.ModuleVo;
import com.visionet.syh_mall.vo.RoleVo;
import com.visionet.syh_mall.web.BaseController;

/**
 * 
 * @author xiaofb
 * @time 2017年8月24日
 */
@RestController
@RequestMapping("/api/role")
public class RoleManagerController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(RoleManagerController.class);
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 获取系统模块
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/querySystemModules",method = RequestMethod.POST)
	public BaseReturnVo<Object> querySystemModules(@RequestBody String userId){
		List<ModuleVo> list = null;
		try {
			list = roleService.getModulesList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常");
		}
		
		return BaseReturnVo.success(list);
	}
	
	/**
	 * 添加/编辑 系统角色
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/addSystemRole",method = RequestMethod.POST)
	@Log(name="添加/编辑系统角色",model="权限管理")
	public BaseReturnVo<Object> addSystemRole(@RequestBody @Valid RoleVo vo,BindingResult result){
		try {
			Role role = new Role();
			role.setId(vo.getRoleID());
			role.setRoleName(vo.getRoleName());
			roleService.addSystemRole(role, vo.getRoleModules());
		} catch (RestException e) {
			throw new RestException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException(e.getMessage());
		}
		return BaseReturnVo.success("添加成功");
	}
	
	/**
	 * 获取角色列表
	 * @param roleID
	 * @return
	 */
	@RequestMapping(value = "/queryRoleList",method = RequestMethod.POST)
	public BaseReturnVo<Object> queryRoleList(){
		logger.info("获取角色列表");
		List<Role> list = roleService.getRoleList();
		return BaseReturnVo.success(list);
	}
	
	/**
	 * 获取角色权限
	 * @param roleID
	 * @return
	 */
	@RequestMapping(value = "/queryRolePriMission",method = RequestMethod.POST)
	public BaseReturnVo<Object> queryRoleList(@RequestBody Map<String,String> param){
		Map<String,Object> map = new HashMap<String, Object>();
		logger.info("获取角色权限请求参数：{}",param);
		List<ModuleVo> list = roleService.getUserPermissions(param.get("adminRoleID"));
		map.put("modules", list);
		map.put("roleName", roleService.getRoleById(param.get("adminRoleID")).getRoleName());
		return BaseReturnVo.success(map);
	}
	
	/**
	 * 删除角色
	 * @param roleID
	 * @return
	 */
	@RequestMapping(value = "/delRole",method = RequestMethod.POST)
	@Log(name="删除角色",model="权限管理")
	public BaseReturnVo<Object> delRole(@RequestBody Map<String,String> param){
		logger.info("删除角色请求参数：{}",param);
		try {
			roleService.delRole(param.get("roleID"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常，删除失败");
		}
		return BaseReturnVo.success("删除成功");
	}
}
