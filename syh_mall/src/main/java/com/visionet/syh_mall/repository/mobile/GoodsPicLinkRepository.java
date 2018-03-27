package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.goods.GoodsPicLink;
import com.visionet.syh_mall.repository.BaseRepository;

public interface GoodsPicLinkRepository extends  BaseRepository<GoodsPicLink, String>{
	@Query(value="SELECT g FROM GoodsPicLink g WHERE g.goodsId=?1 AND g.isDeleted=0")
	List<GoodsPicLink> getGoodsPicList(String GoodId);
	
	@Query(value="FROM GoodsPicLink g WHERE g.maxImgId=?1 OR g.midImgId=?1 OR g.minImgId=?1 AND g.isDeleted=0")
	List<GoodsPicLink> findByImg(String imgId);

}
