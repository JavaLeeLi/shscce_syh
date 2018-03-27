package com.visionet.syh_mall.service.mobile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.SysConstants;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.goods.GoodsKind;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.OperateLogRepository;
import com.visionet.syh_mall.repository.mobile.GoodsKindRepository;
import com.visionet.syh_mall.service.BaseService;

/**
 * 商品种类service
 * @author xiaofb
 * @time 2017年8月24日
 */
@Service
public class GoodsKindsService extends BaseService{
	
	@Autowired
	private GoodsKindRepository goodsKindRepository;
	@Autowired
	private OperateLogRepository operateLogDao;
	
	/**
	 * 添加或编辑商品种类
	 * @param goodsKind
	 */
	@Transactional
	public void addOrEditGoodsKinds(GoodsKind goodsKind,String userId){
		//无id做保存操作
		if(StringUtils.isEmpty(goodsKind.getId())){
			goodsKind.setCreateTime(DateUtil.getCurrentDate());
			goodsKind.setIsDeleted(0);
			goodsKind.setCreateBy(userId);
			goodsKind.setUpdateBy(userId);
			goodsKindRepository.save(goodsKind);
			operateLogDao.save(addLog(null, userId, "添加商品种类"));
			return;
		}
		GoodsKind kind = goodsKindRepository.findOne(goodsKind.getId());
		kind.setKindName(goodsKind.getKindName()==null?kind.getKindName():goodsKind.getKindName());
		kind.setKindIconFileId(goodsKind.getKindIconFileId());
		kind.setParentKindId(goodsKind.getParentKindId()==null?kind.getParentKindId():goodsKind.getKindName());
		//开启免审核
		if(goodsKind.getReviewIsAvoid() == SysConstants.YES){
			List<GoodsKind>  subKinds = goodsKindRepository.findByParentKindIdAndIsDeleted(kind.getId(), SysConstants.DELETE_FLAG_NO,new Sort(Direction.ASC,"kindSort"));
			GoodsKind temp = null;
			//所有子类也为免审核
			for (GoodsKind subKind : subKinds) {
				temp = goodsKindRepository.findOne(subKind.getId());
				temp.setReviewIsAvoid(SysConstants.YES);
				goodsKindRepository.save(temp);
			}	
		}
		kind.setReviewIsAvoid(goodsKind.getReviewIsAvoid());
		goodsKindRepository.save(goodsKind);
		operateLogDao.save(addLog(null, userId, "编辑商品种类"));
	}
	
	/**
	 * 删除商品种类
	 * @param id
	 */
	public void delGoodsKind(String id,String userId){
		Sort sort = new Sort(Direction.ASC, "kindSort");
		GoodsKind goodsKind = goodsKindRepository.findOne(id);
		//是否存在子类
		List<GoodsKind> list = goodsKindRepository.findByParentKindIdAndIsDeleted(goodsKind.getId(),SysConstants.DELETE_FLAG_NO,sort);
		if(list.size() > 0){
			throw new RestException(HttpStatus.ACCEPTED, "请先删除其子类");
		}
		//标记删除
		goodsKind.setIsDeleted(SysConstants.DELETE_FLAG_YES);
		goodsKind.setUpdateBy(userId);
		goodsKindRepository.save(goodsKind);
		operateLogDao.save(addLog(null, userId, "删除商品种类"));
	}
	
	/**
	 * 获取商品种类
	 * @param id
	 * @return
	 */
	public GoodsKind getGoodsKind(String id){
		GoodsKind goodsKind = goodsKindRepository.findOne(id);
		return goodsKind;
	}

}
