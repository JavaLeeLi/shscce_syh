package com.visionet.syh_mall.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.User;

public interface UserRepository extends BaseRepository<User, String> {
	// 查询登录的方法
	User findUserByloginName(String loginName);

	User findUserByphone(String phone);
	
	public User findUserByLoginName(String loginName);
	
	User findByLoginNameAndIsDeleted(String loginName,int isDeleted);
	
	@Query(value = "SELECT u.id  FROM User u WHERE u.aliasName LIKE %?1% AND u.isDeleted=0")
	List<String> getUserId(String userName);

	// 通过用户ID查询用户金额
	@Query(value = "SELECT af.amt FROM  UserAccountFlow af WHERE  af.userId=?1 and af.status='success'")
	List<BigDecimal> findMbrAmtById(String UserID);

	/*
	 * @Query("UPDATE User u SET u.unreadMsg=u.unreadMsg+?1") void
	 * addUnreadNotice(String sort);
	 */

	// 通过图片id查询图片路径
	@Query(value = "SELECT fm.filePath FROM FileManage fm , User u WHERE u.imgFileId=fm.id AND fm.id=?1")
	String findMbrImgUrlById(String imgFileId);

	// 统计每个标签的用户人数
	@Query(value = "SELECT COUNT(1) AS tagMbrNum FROM UserTagLink tl  WHERE  tl.tagId=?1 GROUP BY tl.tagId")
	Integer counttagId(String tagId);

	User findById(String Id);

	// 根据邀请码查询
	User findByInvitationCode(String invitationCode);

	@Query("select u.id from User u where u.phone = ?1")
	String findByPhone(String phone);

	// 通过手机号或用户名查询用户id
	@Query("select u.id from User u where u.loginName = ?1 or u.phone = ?2")
	List<String> findByLoginNameOrPhone(String loginName, String phone);

	// 同过用户名称关键字查询用户ID
	@Query("SELECT u.id FROM User u WHERE u.loginName like %?1% AND u.isDeleted=0")
	List<String> findByloginName(String loginName);

	@Query("SELECT u.id FROM User u WHERE u.userTypeCode=?1 AND u.isDeleted=0")
	List<String> findByUserTypeCode(String userTypeCode);
	
	@Query("FROM User u,Goods g WHERE u.id=g.ownerId")
	List<Object> findByAll();
	@Query("FROM User u WHERE u.id=?1 AND u.isDeleted=0 AND u.updateTime>?2 AND u.updateTime<?3")
	User findByUserIdAndTime(String userId, Date startTime, Date endTime);

	@Query("FROM User u where u.aliasName=?1")
	List<User> findByAliasName(String aliasName);

	User findByWechatOpenIdAndIsDeleted(String openId, int isDeleted);
}
