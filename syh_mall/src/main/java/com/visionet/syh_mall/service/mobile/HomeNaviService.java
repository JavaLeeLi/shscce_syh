package com.visionet.syh_mall.service.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.SysConstants;
import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.HomeNavi;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.mobile.HomeNaviRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
import com.visionet.syh_mall.vo.HomeNaviVo;
import com.visionet.syh_mall.vo.HomeNaviVo.HomeNaviInfo;

/**
 * @Author DM
 * @version ：2017年8月21日下午2:12:51 实体类
 */
@Service
public class HomeNaviService extends BaseService{
	@Autowired
	private HomeNaviRepository homeDao;
	@Autowired
	private FileManageRepostory fileDao;

	/**
	 * @Title: homeNaviList @Description: 搜索快捷导航 @param @return 设定文件 @return
	 *         List<Map<String,Object>> 返回类型 @throws
	 */
	public List<HomeNaviInfo> homeNaviList() {
		// 快捷导航的结果集
		List<HomeNaviInfo> result = new ArrayList<HomeNaviInfo>();
		// 搜索没有删除的快捷导航
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.search()));
		Sort sort = new Sort(Direction.ASC, "naviItemSort");
		Iterable<HomeNavi> list = homeDao.findAll(DynamicSpecifications.bySearchFilter(filter.values(), HomeNavi.class),
				sort);
		// 遍历快捷导航将结果集添加到Map中再加入List
		for (HomeNavi homeNavi : list) {
			String path = fileDao.findUrlById(homeNavi.getIconFileId());
			HomeNaviInfo naviInfo = HomeNaviVo.convertVo(homeNavi, FilePathUtils.fileUrl(path));
			result.add(naviInfo);
		}
		return result;
	}

	/**
	 * 
	 * @Title: delHomeNavi @Description: 删除热门导航的方法 @param @param
	 *         homeNaviID @param @return 设定文件 @return int 返回类型 @throws
	 */

	public void delHomeNavi(String homeNaviID,String adminId) {
		// 查找该热门导航
		HomeNavi one = homeDao.findOne(homeNaviID);
		if (StringUtils.isEmpty(one)) {
			throw new RestException("没有该热门导航");
		}
		// 修改该热门导航的删除状态
		one.setIsDeleted(SysConstants.DELETE_FLAG_YES);
		one.setUpdateTime(DateUtil.getCurrentDate());
		homeDao.save(one);
		addLog(null, adminId, "删除热门导航！");
	}

	/**
	 * @Title: addHomeNavi @Description: 添加编辑热门导航的方法 @param @param homeNavi
	 *         设定文件 @return void 返回类型 @throws
	 */

	public void addHomeNavi(HomeNaviVo homeNaviVo, String currentUserId) {
		// 新增热门导航
		if (StringUtils.isEmpty(homeNaviVo.getHomeNaviID())) {
			HomeNavi homeNavi = new HomeNavi();
			homeNavi.setCreateBy(currentUserId);
			homeNavi.setUpdateBy(currentUserId);
			homeDao.save(homeNaviVo.convertPo(homeNaviVo, homeNavi));
			return;
		}
		// 查找该热门导航并对该热门导航进行修改
		HomeNavi homeNavi = homeDao.findOne(homeNaviVo.getHomeNaviID());
		if (StringUtils.isEmpty(homeNavi)) {
			throw new RestException("没有该热门导航");
		}
		homeNavi.setUpdateBy(currentUserId);
		homeNavi.setUpdateTime(DateUtil.getCurrentDate());
		homeDao.save(homeNaviVo.convertPo(homeNaviVo, homeNavi));
		addLog(null, currentUserId, "删除热门导航！");
	}
}
