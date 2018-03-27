package com.visionet.syh_mall.repository.evaluation;

import com.visionet.syh_mall.entity.evaluation.RecognizeTag;
import com.visionet.syh_mall.repository.BaseRepository;
/**
 * 鉴评标签管理
 * @author mulongfei
 * @date 2017年11月7日上午11:52:18
 */
public interface RecognizeTagRepository extends BaseRepository<RecognizeTag, String>{
	
	RecognizeTag findByGoodsId(String evaluationGoodsId);
}
