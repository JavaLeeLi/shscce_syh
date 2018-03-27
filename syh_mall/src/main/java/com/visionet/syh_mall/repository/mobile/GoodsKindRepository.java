package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.goods.GoodsKind;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 *@Author DM
 *@version ：2017年8月17日下午6:10:31
 *实体类
 */
public interface GoodsKindRepository extends BaseRepository<GoodsKind, String>{
	
	public List<GoodsKind> findByIsDeleted(int isDeleted);
	public GoodsKind findAllById(String id);
	@Query("from GoodsKind")
	Page<GoodsKind> findGoodsKindList(Pageable page);
	
	List<GoodsKind> findByParentKindIdAndIsDeleted(String id,int isDeleted,Sort sort);
	
	//查询一级类型
	public List<GoodsKind> findByParentKindIdIsNullAndIsDeleted(int isDeleted,Sort sort);
}
