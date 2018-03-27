package com.visionet.syh_mall.repository.syhservice;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.syhservice.Syhservice;
import com.visionet.syh_mall.repository.BaseRepository;

public interface SyhserviceRepository extends BaseRepository<Syhservice, String> {

	@Query("FROM Syhservice s WHERE s.isDeleted = 0 AND s.id=?1")
	Syhservice findById(String id);
}
