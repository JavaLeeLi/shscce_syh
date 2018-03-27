package com.visionet.syh_mall.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.VerifyCode;

/**
 *@Author DM
 *@version ：2017年8月24日下午3:02:12
 *实体类
 */
public interface VerifyCodeRepository extends BaseRepository<VerifyCode, String>{
	@Query(value="select v.verify_code from tbl_verify_code v where v.expire_time>now() and v.has_validated='unValidated' and v.phone=?1 ORDER BY v.create_time DESC LIMIT 1",nativeQuery=true)
	public String findCodeByTime(String phone);
	@Query(value="from VerifyCode v where v.expireTime>now() and v.hasValidated='unValidated' and v.phone=?1 ORDER BY v.createTime DESC")
	public List<VerifyCode> findVerifyCodeByTime(String phone);
	@Query(value="from VerifyCode v where v.expireTime>now() and v.hasValidated='unValidated' and v.phone=?1")
	public List<VerifyCode> findVerifyByTime(String phone);
}
