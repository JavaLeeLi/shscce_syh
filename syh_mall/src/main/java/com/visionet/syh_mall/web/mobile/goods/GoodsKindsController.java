package com.visionet.syh_mall.web.mobile.goods;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.entity.goods.GoodsKind;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.fileManage.FileManageService;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
import com.visionet.syh_mall.service.mobile.GoodsKindsService;
import com.visionet.syh_mall.web.BaseController;

/**
 * 商品种类Controller
 * @author xiaofb
 * @time 2017年9月15日
 */
@RestController
@RequestMapping(value = "api/goods")
public class GoodsKindsController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(GoodsKindsController.class);

	
	@Autowired
	private GoodsKindsService goodsKindsService;
	@Autowired
	private FileManageService fileManageService;
	/**
	 * 获取商品种类详情
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/getCategoryDetail")
	public BaseReturnVo<Object> getCategoryDetail(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info("获取商品种类详情请求参数:{}", param);
		try {
			GoodsKind goodsKind = goodsKindsService.getGoodsKind(String.valueOf(param.get("categoryID")));
			resultMap.put("categoryID", goodsKind.getId());
			resultMap.put("categoryName", goodsKind.getKindName());
			resultMap.put("categoryImgID", goodsKind.getKindIconFileId());
			//获取图片路径
			String path = fileManageService.findPathById(goodsKind.getKindIconFileId());
			resultMap.put("categoryImgUrl", FilePathUtils.fileUrl(path));
			resultMap.put("categorySort", goodsKind.getKindSort());
			resultMap.put("reviewIsAvoid", goodsKind.getReviewIsAvoid()==0?false:true);
			if(!StringUtils.isEmpty(goodsKind.getParentKindId())){
				GoodsKind parentKinds = goodsKindsService.getGoodsKind(goodsKind.getParentKindId());
				resultMap.put("parentCategoryID", parentKinds.getId());
				resultMap.put("parentCategoryName", parentKinds.getKindName());
			}
			resultMap.put("categoryGoodsNum", null);
			resultMap.put("createTime", goodsKind.getCreateTime());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常");
		}
		return BaseReturnVo.success(resultMap);
	}
}
