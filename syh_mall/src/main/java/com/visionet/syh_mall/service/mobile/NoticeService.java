package com.visionet.syh_mall.service.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.Notice;
import com.visionet.syh_mall.exception.RestException;
//import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mobile.NoticeRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.vo.NoticeVo;

/**
 * @Author DM
 * @version ：2017年8月15日下午4:00:16 实体类
 */
@Service
public class NoticeService extends BaseService {
	@Autowired
	private NoticeRepository noticeDao;
/*	@Autowired
	private UserRepository userDao;*/

	/**
	 * @Title: queryList @Description: 搜索公告列表 @param @param param @param @param
	 *         pageInfo @param @return @param @throws Exception 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> queryList(Map<String, Object> param, PageInfo pageInfo) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.NoticeList(param)));
		Page<Notice> list = noticeDao.findAll(DynamicSpecifications.bySearchFilter(filter.values(), Notice.class),
				buildPageRequest(pageInfo));
		List<Map<String, Object>> noticeInfos = new ArrayList<Map<String, Object>>();
		for (Notice notice : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("unreadMsgCount", null);
			map.put("unreadNoticeCount", null);
			map.put("noticeID", notice.getId());
			map.put("noticeTitle", notice.getNoticeTitle());
			map.put("noticeType", notice.getNoticeType());
			map.put("noticePublishTime", notice.getCreateTime());
			noticeInfos.add(map);
		}
		result.put("itemCount", list.getTotalElements());
		result.put("pageCount", list.getTotalPages());
		result.put("curPageIndex", pageInfo.getPageIndex());
		result.put("hasNext", list.hasNext());
		result.put("noticeInfos", noticeInfos);
		return result;
	}

	/**
	 * @Title: queryNoticeDetail @Description: 搜索公告详情 @param @param
	 *         id @param @return @param @throws Exception 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> queryNoticeDetail(String id, String userId) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Notice notice = noticeDao.findById(id);
		if (StringUtils.isEmpty(notice)) {
			throw new RestException("没有此公告");
		}

		result.put("noticeID", notice.getId());
		result.put("noticeTitle", notice.getNoticeTitle());
		result.put("noticeContent", notice.getNoticeContent());
		result.put("noticeType", notice.getNoticeType());
		result.put("noticePublishTime", notice.getCreateTime());
		return result;
	}

	/**
	 * @Title: reviceNotice @Description: 添加编辑公告 @param @param
	 *         notice @param @param currentUserId 设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void reviceNotice(NoticeVo noticeVo, String currentUserId) {
		if (StringUtils.isEmpty(noticeVo.getNoticeID())) {
			Notice notice = new Notice();
			notice.setCreateBy(currentUserId);
			notice.setUpdateBy(currentUserId);
			noticeDao.save(noticeVo.convertPo(noticeVo, notice));
			return;
		}
		Notice notice = noticeDao.findById(noticeVo.getNoticeID());
		if (StringUtils.isEmpty(notice)) {
			throw new RestException("没有此公告");
		}
		notice.setUpdateBy(currentUserId);
		notice.setUpdateTime(DateUtil.getCurrentDate());
		noticeDao.save(noticeVo.convertPo(noticeVo, notice));
		addLog(null, currentUserId, "编辑公告！");
	}

	/**
	 * @Title: showNotice @Description: 搜索公告 @param @param param @param @param
	 *         pageInfo @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> selectNotice(Map<String, Object> param, PageInfo pageInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, SearchFilter> parse = SearchFilter.parse(DynamicParamConvert.getShowNotice(param));
		Page<Notice> pageNotice = noticeDao.findAll(DynamicSpecifications.bySearchFilter(parse.values(), Notice.class),
				buildPageRequest(pageInfo));
		List<Map<String, Object>> noticeInfos = new ArrayList<Map<String, Object>>();
		for (Notice notice2 : pageNotice) {
			Map<String, Object> Noticemap = new HashMap<String, Object>();
			Noticemap.put("noticeID", notice2.getId());
			Noticemap.put("noticeTitle", notice2.getNoticeTitle());
			Noticemap.put("noticeType", notice2.getNoticeType());
			Noticemap.put("noticePublishTime", notice2.getCreateTime());
			noticeInfos.add(Noticemap);
		}
		result.put("itemCount", pageNotice.getTotalElements());
		result.put("pageCount", pageNotice.getTotalPages());
		result.put("curPageIndex", pageInfo.getPageIndex());
		result.put("hasNext", pageNotice.hasNext());
		result.put("noticeInfos", noticeInfos);
		return result;
	}
}
