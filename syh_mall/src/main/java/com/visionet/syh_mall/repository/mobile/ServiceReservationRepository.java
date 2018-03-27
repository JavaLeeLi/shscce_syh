package com.visionet.syh_mall.repository.mobile;


import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.service.ServiceReservation;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @ClassName: ServiceReservationRepository
 * @Description: 搜索服务预约
 * @author chenghongzhan
 * @date 2017年9月18日 下午4:31:29
 */
public interface ServiceReservationRepository extends BaseRepository<ServiceReservation, String> {
	
	@Query("FROM ServiceReservation s WHERE s.id=?1 AND s.isDeleted=0")
	ServiceReservation findById(String id);
}
