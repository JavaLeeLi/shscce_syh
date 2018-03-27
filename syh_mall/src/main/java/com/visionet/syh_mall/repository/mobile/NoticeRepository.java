package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.visionet.syh_mall.entity.Notice;


/**
 *@Author DM
 *@version ：2017年8月15日下午4:03:58
 *实体类
 */
public interface NoticeRepository extends PagingAndSortingRepository<Notice, Long>, JpaSpecificationExecutor<Notice>  {
	
	@Query("from Notice n where n.noticeType=?1")
	List<Notice> findByNoticeType(int noticeType);
	
	@Query(value="from Notice n where n.id=?1")
	public Notice findById(String id);
	
	@Query("FROM Notice n WHERE n.noticeType=?1")
	List<Notice> findByType(Integer noticeType);
	
	@Query("FROM Notice n WHERE n.noticeType=?1 AND n.noticeTitle like ?2")
	List<Notice> findByTypeAndKeywords(Integer noticeType,String keywords);
	
	/*@Query("SELECT MAX(n.noticeSort) FROM Notice n ")
	Integer findMaxSort();*/
	
}
