package com.visionet.syh_mall.repository.mobile.channel;


import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.MbrUser;
import com.visionet.syh_mall.repository.BaseRepository;
/**
 * 搜索分销会员Dao接口
 * @author mulongfei
 */
public interface MbrUserRepository extends BaseRepository<MbrUser, String>{
	@Query(value="SELECT t2.filePath FROM MbrUser t1,FileManage t2 WHERE t1.imgFileId=t2.id AND t1.id=?1")
	String getImgPath(String Id);
}
