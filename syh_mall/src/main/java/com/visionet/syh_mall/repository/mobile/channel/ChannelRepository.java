package com.visionet.syh_mall.repository.mobile.channel;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.channel.Channel;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * 会员分销模板Dao层接口
 * 
 * @author mulongfei
 * @date 2017年8月23日下午4:53:52
 */
public interface ChannelRepository extends BaseRepository<Channel, String> {

	@Query("FROM Channel c WHERE c.isDelete=0")
	Channel getOne();

	@Query(value = "SELECT SUM( cf.buy_father_user_commission_fee) AS buyFatherFee, cf.buy_father_user_commission_rate, SUM(cf.sell_father_user_commission_fee) AS sellFatherFee,	cf.sell_father_user_commission_rate,	SUM(cf.buy_grand_father_user_commission_fee) AS buyGrandFatherFee,cf.buy_grand_father_user_commission_rate,	SUM(cf.sell_grand_father_user_commission_fee) AS sellGrandFatherFee,	cf.sell_grand_father_user_commission_rate,u.login_name AS userNo,u.alias_name AS userName FROM tbl_commission_flow cf LEFT JOIN tbl_user u ON (cf.buy_father_user_account = u.login_name	OR cf.sell_father_user_account = u.login_name) WHERE u.login_name = ?1 ", nativeQuery = true)
	public Object getChannelSummary(String loginName);

}
