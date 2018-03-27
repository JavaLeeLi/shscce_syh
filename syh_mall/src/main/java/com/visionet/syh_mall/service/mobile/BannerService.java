package com.visionet.syh_mall.service.mobile;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.SysConstants;
import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.Banner;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.mobile.BannerRepository;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
import com.visionet.syh_mall.vo.BannerVo;
import com.visionet.syh_mall.vo.BannerVo.BannerReturn;

/**
 * @Author DM
 * @version ：2017年8月16日下午2:40:12 实体类
 */
@Service
public class BannerService {
	@Autowired
	private BannerRepository bannerDao;
	@Autowired
	private FileManageRepostory fileDao;

	/**
	 * @Title: queryList @Description: 搜索首页banner方法 @param @return 设定文件 @return
	 *         List<Map<String,Object>> 返回类型 @throws
	 */
	public List<BannerReturn> queryList(Map<String,Object> param) {
		List<BannerReturn> result = new ArrayList<BannerReturn>();
		// 查询所有未被删除的Banner项
		Sort sort = new Sort(Direction.ASC, "bannerSort");
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchBanner(param)));
		List<Banner> banners = bannerDao.findAll(DynamicSpecifications.bySearchFilter(filter.values(), Banner.class),
				sort);
		// 遍历结果集并返回需要的参数
		for (Banner banner : banners) {
			if (banner.getBannerShopId() == null) {
				String path = fileDao.findUrlById(banner.getImageFileId());
				BannerReturn bannerList = BannerVo.convertVo(banner, FilePathUtils.fileUrl(path));
				result.add(bannerList);
			}
		}
		return result;
	}

	/**
	 * 添加/编辑banner项
	 * 
	 * @param
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public void reviseBanner(BannerVo bannerVo) throws IllegalAccessException, InvocationTargetException {
		if (StringUtils.isEmpty(bannerVo.getBannerID())) {
			Banner banner = new Banner();
			bannerDao.save(bannerVo.convertPo(bannerVo, banner));
			return;
		}
		Banner banner = bannerDao.getOneBanner(bannerVo.getBannerID());
		if (StringUtils.isEmpty(banner)) {
			throw new RestException("banner不存在");
		}
		banner.setUpdateTime((DateUtil.getCurrentDate()));
		bannerDao.save(bannerVo.convertPo(bannerVo, banner));
	}

	/**
	 * 删除banner项
	 * 
	 * @param
	 */
	@Transactional
	public void delBanner(String bannerID,String shopID) {
		// 修改查出来的banner删除状态
		Banner banner = bannerDao.getOneBanner(bannerID);
		if (StringUtils.isEmpty(banner)) {
			throw new RestException("banner不存在");
		}
		banner.setIsDeleted(SysConstants.DELETE_FLAG_YES);
		banner.setUpdateTime(DateUtil.getCurrentDate());
		bannerDao.save(banner);
		List<Banner> list = bannerDao.findBannerByShopId(shopID);
		for (Banner banner2 : list) {
			if(banner2.getBannerSort()==banner.getBannerSort()){
				break;
			}
			if(banner2.getBannerSort()>banner.getBannerSort()){
				banner2.setBannerSort(banner2.getBannerSort()-1);
			}
			bannerDao.save(banner2);
		}
	}

}
