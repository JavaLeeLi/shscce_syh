package com.visionet.syh_mall.repository.mobile;



import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.Tag;
import com.visionet.syh_mall.repository.BaseRepository;

import java.util.List;

public interface TagRepository extends BaseRepository<Tag, String>{
	
	    //查询用标签名字查标签ID
		@Query(value="SELECT t.id FROM Tag t WHERE t.tagName=?1 and t.isDeleted=0")
		String findTagIdIdByTagName(String tagName);
		
		Tag findById(String tagId);

	    List<Tag> findByIsDeleted(Integer isDeleted);
		
		@Query(value="FROM Tag t WHERE t.id=?1 AND t.isDeleted=0")
		Tag findByTagId(String tagId);
}
