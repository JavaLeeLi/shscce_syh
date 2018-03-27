package com.visionet.syh_mall.service.adminUser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.admin.AdminUser;
import com.visionet.syh_mall.entity.admin.Role;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.admin.AdminUserRepository;
import com.visionet.syh_mall.repository.admin.RoleRepository;
import com.visionet.syh_mall.vo.AdminUserVo;

@Service
public class AdminUserService {
	
	@Autowired
	private AdminUserRepository adminRepo;
	@Autowired
	private RoleRepository roleRepo;
	
	
	public AdminUser findUserByLoginName(String loginName){
		AdminUser adminUser = adminRepo.findAdminUserByLoginName(loginName);
		return adminUser;
	}
	
	/**
	 * 获取用户角色
	 * @param userId
	 * @return
	 */
	public Role getUserRole(String roleId){
		return roleRepo.findOne(roleId);
	}
	
	
	/**
	 * 添加或编辑管理员
	 * @param user
	 */
	public void addUser(AdminUser user){
		AdminUser adminInfo = null;
		if(!StringUtils.isEmpty(user.getId())){
			//编辑分配角色
			adminInfo = adminRepo.findOne(user.getId());
			adminInfo.setRoleId(user.getRoleId());
			adminInfo.setUpdateTime(DateUtil.getCurrentDate());
			adminRepo.save(adminInfo);
			return;
		}
		//登录名是否重复
		adminInfo = adminRepo.findAdminUserByLoginName(user.getLoginName());
		if(null != adminInfo){
			throw new RestException(HttpStatus.ACCEPTED,"用户名重复");
		}
		user.setCreateTime(DateUtil.getCurrentDate());
		user.setUpdateTime(DateUtil.getCurrentDate());
		adminRepo.save(user);
	}
	
	/**
	 * 删除管理员（逻辑删除）
	 * @param id
	 */
	public void delAdminUser(String id){
		AdminUser user = adminRepo.findOne(id);
		user.setIsDeleted(1);
		adminRepo.save(user);
	}
	
	
	/**
	 * 重置管理员密码
	 * @param userId
	 */
	public void restPassword(String userId){
		AdminUser user = adminRepo.findOne(userId);
		user.setPassword("ZTEwYWRjMzk0OWJhNTlhYmJlNTZlMDU3ZjIwZjg4M2U=");	//重置密码
		user.setUpdateTime(DateUtil.getCurrentDate());
		adminRepo.save(user);
	}
	
	/**
	 * 管理员列表
	 * @return
	 */
	public List<AdminUserVo> getAdminUserList(){
		List<AdminUser> list = adminRepo.findByIsDeleted(0);
		List<AdminUserVo> voList = new ArrayList<AdminUserVo>();
		AdminUserVo vo = null;
		for(AdminUser adminUser : list) {
			vo = new AdminUserVo();
			vo.convertVo(vo,adminUser);
			Role role = roleRepo.findOne(adminUser.getRoleId());
			if(null != role){
				vo.setAdminRoleName(role.getRoleName());
			}
			voList.add(vo);
		}
		return voList;
	}
	
	/**
	 * 更新管理员信息
	 * @param adminuser
	 */
	public void updateAdminUserInfo(AdminUser adminuser){
		AdminUser user = adminRepo.findOne(adminuser.getId());
		user.setAliasName(adminuser.getAliasName());
		user.setMail(adminuser.getMail());
		user.setPhone(adminuser.getPhone());
		adminRepo.save(user);
	}
	
	/**
	 * 修改密码
	 * @param oldPwd
	 * @param newPwd	
	 * @param confirmNewPwd
	 */
	public void modifyPassword(String adminUserID,String oldPwd,String newPwd,String confirmNewPwd){
		if(oldPwd.equals(newPwd)){
			return;
		}
		AdminUser user = adminRepo.findOne(adminUserID);
		if(null==user){
			throw new RestException("管理员不存在");
		}
		if(!oldPwd.equals(user.getPassword())){
			throw new RestException("旧密码不正确");
		}
		user.setPassword(newPwd);
		adminRepo.save(user);
	}
	
	/**
	 *  登陆后操作
	 * @param userId
	 */
	public void loginOperta(String userId){
		//修改登录时间
		AdminUser user = adminRepo.findOne(userId);
		user.setLastLoginTime(DateUtil.getCurrentDate());
		adminRepo.save(user);
	}
	
}
