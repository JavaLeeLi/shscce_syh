package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.Banner;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @Author DM
 * @version ：2017年8月16日下午2:43:58 实体类
 */
public interface BannerRepository extends BaseRepository<Banner, String> {
	@Query("from Banner")
	Page<Banner> findBannerList(Pageable page);

	@Query("FROM Banner b WHERE b.id=?1 AND b.isDeleted = 0 ")
	Banner getOneBanner(String Id);

	@Query("FROM Banner b WHERE b.bannerShopId=?1 AND b.isDeleted=0 ORDER BY bannerSort ASC")
	List<Banner> findBannerByShopId(String shopId);

}
