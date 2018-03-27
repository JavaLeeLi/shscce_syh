package com.visionet.syh_mall.repository.mbr;

import java.util.List;

import com.visionet.syh_mall.entity.UserAuthentication;
import com.visionet.syh_mall.entity.account.UserBank;
import com.visionet.syh_mall.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserBankRepository extends BaseRepository<UserBank, String> {
	//通过流水号获取绑卡信息
	public UserBank findByTranceNum(String tranceNum);
	
	//获取绑卡列表
	public List<UserBank> findByUserId(String userId);

	public UserBank findByUserIdAndCardNoAndStatus(String userId,String bankCard,String status);

	public List<UserBank> findByUserIdAndStatus(String userId, String status);

}
