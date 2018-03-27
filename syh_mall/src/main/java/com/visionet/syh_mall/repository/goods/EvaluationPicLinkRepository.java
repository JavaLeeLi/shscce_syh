package com.visionet.syh_mall.repository.goods;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.goods.EvaluationPicLink;
import com.visionet.syh_mall.repository.BaseRepository;

public interface EvaluationPicLinkRepository extends BaseRepository<EvaluationPicLink, String> {

	List<EvaluationPicLink> findByEvaluationIdAndIsDeleted(String id,int isDeleted);

	@Query(value="FROM EvaluationPicLink e WHERE e.maxImgId=?1 OR e.midImgId=?1 OR e.minImgId=?1 AND e.isDeleted=0")
	List<EvaluationPicLink> findByImg(String imgId);

}
