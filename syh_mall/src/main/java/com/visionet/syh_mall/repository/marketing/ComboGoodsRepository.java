package com.visionet.syh_mall.repository.marketing;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.repository.BaseRepository;
import com.visionet.syh_mall.vo.shop.ComboGoods;

/**
 * @ClassName: ComboGoodsRepository
 * @Description: 套餐的商品搭配
 * @author chenghongzhan
 * @date 2017年10月13日 上午11:31:30
 *
 */
public interface ComboGoodsRepository extends BaseRepository<ComboGoods, String> {

	@Query("SELECT c FROM ComboGoods c WHERE c.comboId=?1 AND c.isDeleted=0")
	List<ComboGoods> findByComboIds(String comboId);

	@Query("SELECT c FROM ComboGoods c WHERE c.id=?1 AND c.isDeleted=0")
	ComboGoods findById(String id);

	@Query("SELECT c FROM ComboGoods c WHERE c.goodsId=?1 and c.isDeleted=0")
	List<ComboGoods> findByGoodsId(String id);



}
