package com.visionet.syh_mall.repository.goods;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.goods.EvaluationResult;
import com.visionet.syh_mall.repository.BaseRepository;

public interface EvaluationResultRepository extends BaseRepository<EvaluationResult, String> {

	@Query(value = "FROM EvaluationResult e WHERE e.factorCode=?1 ORDER BY e.createTime DESC")
	List<EvaluationResult> findByFactorCode(String factorCode);

	EvaluationResult findByIdAndIsDeleted(String id, int isDeleted);

	@Query(value = "FROM EvaluationResult e WHERE e.evaluationTypeCode='evaluation_pass' and  e.collectEvaluationCode=?1 and e.isDeleted='0' ORDER BY e.createTime DESC")
	EvaluationResult findByCollectEvaluationCode(String collectEvaluationCode);

	EvaluationResult findByCollectEvaluationCodeAndIsDeleted(String evaluationResultId, int isDeleted);

}