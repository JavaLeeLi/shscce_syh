package com.visionet.syh_mall.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.admin.AdminUser;
import com.visionet.syh_mall.repository.BaseRepository;

public interface AdminUserRepository extends BaseRepository<AdminUser, String> {

	@Query("FROM AdminUser a WHERE a.isDeleted=0 AND a.loginName=?1")
	public AdminUser findAdminUserByLoginName(String loginName);

	@Query("select u from AdminUser u where u.isDeleted = ?1 and u.id != '402890595e07a2e2015e07d8d8b90054' ")
	public List<AdminUser> findByIsDeleted(int isDeleted);

	public AdminUser findByLoginName(String loginName);
}
