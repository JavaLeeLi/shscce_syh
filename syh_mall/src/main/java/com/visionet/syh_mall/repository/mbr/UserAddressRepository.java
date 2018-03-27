package com.visionet.syh_mall.repository.mbr;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.mbr.UserAddress;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 *@Author DM
 *@version ：2017年8月31日下午2:39:21
 *实体类
 */
public interface UserAddressRepository extends BaseRepository<UserAddress, String>{
	@Query("from UserAddress u where u.isDeleted=0 and u.userId=?1")
	List<UserAddress> findAddressById(String userId);
	@Query("from UserAddress u where u.isDefault=1 and u.userId=?1")
	UserAddress findAddressByIsDefault(String userId);
}
