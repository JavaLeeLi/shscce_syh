package com.visionet.syh_mall.repository.goods;

import java.util.List;

import com.visionet.syh_mall.entity.goods.GroupJob;
import com.visionet.syh_mall.repository.BaseRepository;

public interface GroupJobRepository extends BaseRepository<GroupJob, String> {
	
	GroupJob findByGroupDetailId(String groupDetailId);

	List<GroupJob> findAllByJobStatus(int Status);
}
