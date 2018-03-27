package com.visionet.syh_mall.repository.mbr;


import com.visionet.syh_mall.entity.account.UserAccount;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * 用户账户dao
 * 
 * @author xiaofb
 * @time 2017年10月13日
 */
public interface UserAccountRepository extends BaseRepository<UserAccount, String> {
	
	public UserAccount findByUserId(String userId);

	
}
