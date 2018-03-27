package com.visionet.syh_mall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.UserAuthentication;

/**
 * @ClassName: UserAuthenticationRepository
 * @Description: 用户详细信息
 * @author chenghongzhan
 * @date 2017年8月31日 上午10:54:54
 *
 */
public interface UserAuthenticationRepository extends BaseRepository<UserAuthentication, String> {
	// 通过用户ID来查用户的详细信息
	@Query(value = "SELECT ua.userId FROM UserAuthentication ua WHERE ua.userRealName like %?1% AND ua.status=1 AND ua.isDeleted=1 ORDER BY ua.id")
	List<String> findByUserName(String userName);

	@Query(value = "SELECT ua FROM UserAuthentication ua WHERE ua.userId=?1 and ua.isDeleted=0 and ua.status=2")
	UserAuthentication findOneByUserId(String userID);

	@Query(value = "SELECT ua FROM UserAuthentication ua WHERE ua.userId=?1 and ua.isDeleted=0 and ua.status=0")
	UserAuthentication findByUserID(String userID);
	
	@Query(value = "SELECT ua FROM UserAuthentication ua WHERE ua.userId=?1 and ua.isDeleted=1 and ua.status=1")
	UserAuthentication findByUserId(String userID);

	@Query(value = "SELECT ua FROM UserAuthentication ua where ua.idCode=?1")
	List<UserAuthentication> findByIdCode(String id);

	@Query(value = "SELECT ua FROM UserAuthentication ua WHERE ua.userId=?1 and ua.isDeleted=1 and ua.status=1 ")
	UserAuthentication findUserAuthentication(String id);
}
