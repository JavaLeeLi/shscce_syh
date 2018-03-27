package com.visionet.syh_mall.repository.assistant;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.assistant.CustomerServiceInfo;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @ClassName: CustomerServiceInfoRepository
 * @Description:客服服务的Service层
 * @author chenghongzhan
 * @date 2017年9月19日 下午2:21:02
 *
 */
public interface CustomerServiceInfoRepository extends BaseRepository<CustomerServiceInfo, String> {

	@Query(value = "SELECT c.id FROM CustomerServiceInfo c WHERE  c.employer=?1 AND c.isDeleted=0")
	List<String> findCusIdByUserId(String userID);

	@Query("FROM CustomerServiceInfo c WHERE c.id=?1 AND c.isDeleted=0")
	CustomerServiceInfo findById(String id);

}
