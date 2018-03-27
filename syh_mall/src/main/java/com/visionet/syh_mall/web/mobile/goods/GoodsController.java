
package com.visionet.syh_mall.web.mobile.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.constant.SysConstants;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.common.utils.Validator;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.GoodsKind;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.service.mobile.GoodsKindsService;
import com.visionet.syh_mall.service.mobile.GoodsService;
import com.visionet.syh_mall.service.mobile.ShopService;
import com.visionet.syh_mall.vo.BidRecordVo;
import com.visionet.syh_mall.vo.ExpressTempletVo;
import com.visionet.syh_mall.vo.GoodsKindVo;
import com.visionet.syh_mall.vo.GoodsQo;
import com.visionet.syh_mall.vo.GoodsVo;
import com.visionet.syh_mall.vo.GoodsVo.GoodsInfos;
import com.visionet.syh_mall.vo.RecognizeVo;
import com.visionet.syh_mall.vo.goods.GoodsDraftVo;
import com.visionet.syh_mall.vo.goods.HomeGoodsVo;
import com.visionet.syh_mall.vo.goods.PublishGoodsVo;
import com.visionet.syh_mall.vo.shop.ShopQo;
import com.visionet.syh_mall.web.BaseController;


/**
 * @Author DM
 * @version ：2017年8月17日下午5:24:33 商品业务控制
 */
@RestController
@RequestMapping(value = "api/goods")
public class GoodsController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsKindsService goodsKindService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private KeyMappingRepository keyMappingDao;

	@RequestMapping(value="/goodsSettingDetil",method=RequestMethod.POST)
	public BaseReturnVo<Object> goodsSettingDetil(){
		logger.info("商品设置详情");
		List<KeyMapping> groupCode = keyMappingDao.findByDdGroupCode(1020);
		return BaseReturnVo.success(groupCode);
	}

	
	@RequestMapping(value="/modificationGoodsSetting",method=RequestMethod.POST)
	public BaseReturnVo<Object> modificationGoodsSetting(@RequestBody Map<String,Object> param){
		logger.info("修改商品设置:{}",param);
		try {			
			goodsService.modificationGoodsSetting(param);
		} catch (Exception e) {
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
	
	/**
	 * 首页查询热推商品信息
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public Object queryList(@RequestBody GoodsQo qo) throws Exception {
		logger.info("首页查询热推商品信息:{}", qo);
		List<GoodsInfos> goodsList = goodsService.queryList(qo);
		BaseReturnVo<List<GoodsInfos>> returnVo = new BaseReturnVo<List<GoodsInfos>>(goodsList);
		return returnVo;
	}

	/**
	 * 求购供货商品列表
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequestMapping(value="/searchPurchaseGoods",method=RequestMethod.POST)
	public BaseReturnVo<Object> searchPurchaseGoods(@RequestBody Map<String,String> param){
		Map<String,Object> result = new HashMap<String,Object>();
		List<GoodsInfos> goodsInfos = null;
		try {
			goodsInfos = goodsService.searchPurchaseGoods(param);			
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		result.put("goodsInfos", goodsInfos);
		return BaseReturnVo.success(result);
	}
	
	/**
	 * 商品检索
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchGoods", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchGoods(@RequestBody GoodsQo qo) throws Exception {
		logger.info("商品检索:{}", qo);
		if (Validator.isNotNull(getCurrentUserId())) {
			qo.setCurrentUserId(getCurrentUserId());
			if (qo.getKeywords() != null) {
				if (qo.getKeywords().trim() != "") {
					goodsService.addSearchHistory(qo);
				}
			}
		}
		Page<GoodsInfos> page = goodsService.searchGoods(qo);
		GoodsVo result = new GoodsVo();
		result.setCurPageIndex(qo.getPageIndex());
		result.setHasNext(page.hasNext());
		result.setItemCount(page.getTotalElements());
		result.setPageCount(page.getTotalPages());
		result.setGoodsInfos(page.getContent());
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: searchRecommendGoods @Description: 搜索推荐商品 @param @param
	 *         qo @param @return @param @throws Exception 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/searchRecommendGoods", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchRecommendGoods(@RequestBody GoodsQo qo) throws Exception {
		logger.info("推荐商品:{}");
		GoodsVo result = null;
		try {
			qo.setUserId(getCurrentUserId());
			Page<GoodsInfos> page = goodsService.searchRecommendGoods(qo);
			result = new GoodsVo();
			result.setCurPageIndex(qo.getPageIndex());
			result.setHasNext(page.hasNext());
			result.setItemCount(page.getTotalElements());
			result.setPageCount(page.getTotalPages());
			result.setGoodsInfos(page.getContent());
			return BaseReturnVo.success(result);
		} catch (RestException e) {
			logger.error("推荐商品异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("推荐商品异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * 查询已完成的订单商品
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchOrderGoods", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchOrderGoods(@RequestBody GoodsQo qo) throws Exception {
		logger.info("查询已完成的订单商品:{}", qo);
		Page<GoodsInfos> page = goodsService.searchOrderGoods(qo);
		GoodsVo result = new GoodsVo();
		result.setCurPageIndex(qo.getPageIndex());
		result.setHasNext(page.hasNext());
		result.setItemCount(page.getTotalElements());
		result.setPageCount(page.getTotalPages());
		result.setGoodsInfos(page.getContent());
		return BaseReturnVo.success(result);
	}

	/**
	 * 首页查询商品分类
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryKindList", method = RequestMethod.POST)
	public BaseReturnVo<Object> queryKindList() throws Exception {
		logger.info("首页查询商品分类");
		List<GoodsKindVo> goodsKindList = null;
		try {
			goodsKindList = goodsService.queryKindList();
		} catch (Exception e) {
			logger.error("首页查询商品分类异常：", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(goodsKindList);
	}

	/**
	 * 查询商品详情
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryGoodsDetails")
	public Object queryGoodsDetails(@RequestBody GoodsQo qo) throws Exception {
		logger.info("查询商品详情:{}", qo);
		if (Validator.isNotNull(getCurrentUserId())) {
			qo.setCurrentUserId(getCurrentUserId());
			goodsService.addSearchGoodHistory(qo);
		}
		qo.setCurrentUserId(getCurrentUserId());
		GoodsInfos goods = goodsService.queryGoodsDetails(qo);
		BaseReturnVo<GoodsInfos> returnVo = new BaseReturnVo<GoodsInfos>(goods);
		return returnVo;
	}

	/**
	 * 删除商品
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/delGoods", method = RequestMethod.POST)
	@Log(name = "删除商品", model = "商品模块")
	public BaseReturnVo<Object> delGoods(@RequestBody GoodsQo qo) {
		logger.info(" 删除商品请求参数:{}", qo);
		try {
			goodsService.delGoods(qo);
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(e.getMessage());
		}
		return BaseReturnVo.success("删除成功!");
	}

	/**
	 * @Title: reviseGoods @Description: 添加/编辑鉴评结果 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/revise", method = RequestMethod.POST)
	@Log(name = "添加/编辑鉴评结果", model = "商品模块")
	public BaseReturnVo<Object> reviseGoods(@RequestBody @Valid RecognizeVo recognizeVo, BindingResult result) {
		logger.info("添加/编辑鉴评结果:{}", recognizeVo.toString());
		try {
			goodsService.reviseGoods(recognizeVo, getCurrentUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: delHomeGoods @Description: 删除热销商品 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/delHomeGoods", method = RequestMethod.POST)
	@Log(name = "删除热销商品", model = "商品模块")
	public BaseReturnVo<Object> delHomeGoods(@RequestBody Map<String, String> param) {
		logger.info("删除热销商品:{}", param);
		try {
			goodsService.delHomeGoods(param.get("homeGoodsID"), getCurrentUserId());
		} catch (RestException e) {
			logger.error("删除热销商品异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除热销商品异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("删除成功");
	}

	/**
	 * @Title: addHomeGoods @Description: 增加修改热销商品的方法 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/addHomeGoods", method = RequestMethod.POST)
	@Log(name = "添加/编辑热销商品", model = "商品模块")
	public BaseReturnVo<Object> addHomeGoods(@RequestBody @Valid HomeGoodsVo homeGoodsVo, BindingResult result) {
		logger.info("添加热销商品:{}", homeGoodsVo.toString());
		try {
			goodsService.addHomeGoods(homeGoodsVo, getCurrentUserId());
		} catch (RestException e) {
			logger.error("添加热销商品异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加热销商品异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 添加/编辑商品分类
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/addGoodsCategory", method = RequestMethod.POST)
	@Log(name = "添加/编辑商品分销", model = "商品模块")
	public BaseReturnVo<Object> addGoodsCategory(@RequestBody Map<String, String> map) {
		logger.info("添加/编辑商品分类请求参数:{}", map);
		GoodsKind goodsKind = new GoodsKind();
		goodsKind.setId(map.get("categoryID"));
		goodsKind.setKindName(map.get("categoryName"));
		goodsKind.setKindIconFileId(map.get("categoryImgID"));
		goodsKind.setParentKindId(map.get("parentCategoryID"));
		goodsKind.setReviewIsAvoid(Boolean.valueOf(map.get("reviewIsAvoid")) ? SysConstants.YES : SysConstants.NO);
		if (!StringUtils.isEmpty(map.get("categorySort"))) {
			goodsKind.setKindSort(Integer.valueOf(map.get("categorySort")));
		}
		try {
			goodsKindService.addOrEditGoodsKinds(goodsKind, getCurrentUserId());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("请求成功！");
	}

	/**
	 * 删除商品分类
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/delGoodsCategory", method = RequestMethod.POST)
	@Log(name = "删除商品分类", model = "商品模块")
	public BaseReturnVo<Object> delGoodsCategory(@RequestBody Map<String, String> map) {
		logger.info(" 删除商品分类请求参数:{}", map);
		String categoryID = map.get("categoryID");
		try {
			goodsKindService.delGoodsKind(categoryID, getCurrentUserId());
		} catch (RestException e) {
			logger.error(" 删除商品分类请求异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.getMessage());
		} catch (Exception e) {
			logger.error(" 删除商品分类请求异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("请求成功！");
	}

	/**
	 * 下架商品
	 * 
	 * @param map
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/offsaleGoods", method = RequestMethod.POST)
	@Log(name = "下架商品", model = "商品模块")
	public BaseReturnVo<Object> offsaleGoods(@RequestBody Map<String, String> map) {
		logger.info("下架商品请求参数:{}", map);
		String goodsID = map.get("goodsID");
		String banReason = map.get("banReason");
		try {
			goodsService.undercarriageGoods(goodsID, banReason, getCurrentUserId());
		} catch (RestException e) {
			logger.error(" 删除商品分类请求异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.getMessage());
		} catch (Exception e) {
			logger.error(" 删除商品分类请求异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("请求成功！");
	}

	/**
	 * 商品审核（上架商品）
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/reviewGoods", method = RequestMethod.POST)
	@Log(name = "审核商品", model = "商品模块")
	public BaseReturnVo<Object> reviewGoods(@RequestBody Map<String, String> map) {
		logger.info("商品审核（上架商品）请求参数:{}", map);
		String goodsID = map.get("goodsID");
		String isApproved = map.get("isApproved");
		try {
			goodsService.reviewGoods(goodsID, isApproved, getCurrentUserId());
		} catch (RestException e) {
			logger.error("商品审核（上架商品）异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.getMessage());
		} catch (Exception e) {
			logger.error("商品审核（上架商品）异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("请求成功！");
	}

	/**
	 * @Title: onsaleGoods @Description: 上架商品 @param @param param @param @return
	 *         设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/onsaleGoods", method = RequestMethod.POST)
	@Log(name = "上架商品", model = "商品模块")
	public BaseReturnVo<Object> onsaleGoods(@RequestBody Map<String, Object> param) {
		logger.info("上架商品 请求参数:{}", param);
		String goodsID = (String) param.get("goodsID");
		Integer stockNum = (Integer) param.get("stockNum");
		try {
			goodsService.onsaleGoods(goodsID, getCurrentUserId(), stockNum);
		} catch (RestException e) {
			logger.error(" 上架商品 请求异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.getMessage());
		} catch (Exception e) {
			logger.error(" 上架商品 请求异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 出售、求购、拍卖商品发布 @param Map @return BaseReturnVo<Object> @throws
	 */
	@SuppressWarnings("static-access")
	@RequiresAuthentication
	@RequestMapping(value = "/publishGoods", method = RequestMethod.POST)
	@Log(name = "出售、求购、拍卖商品发布", model = "商品模块")
	public BaseReturnVo<Object> publishGoods(@RequestBody @Valid PublishGoodsVo publishGoodsVo, BindingResult result) {
		logger.info("发布商品:{}", publishGoodsVo);
		Map<String, Object> returnMap = null;
		try {
			returnMap = goodsService.publishGoods(publishGoodsVo, this.getCurrentUserId());
		} catch (RestException e) {
			logger.error("发布商品异常", e);
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			logger.error("发布商品异常", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(returnMap);
	}

	/**
	 * 保存商品草稿 @param @return BaseReturnVo<Object> @throws
	 */
	@SuppressWarnings("static-access")
	@RequiresAuthentication
	@RequestMapping(value = "/saveGoodsDraft", method = RequestMethod.POST)
	@Log(name = "保存商品草稿", model = "商品模块")
	public BaseReturnVo<Object> saveGoodsDraft(@RequestBody @Valid GoodsDraftVo goodsDraftVo, BindingResult result) {
		logger.info("保存商品草稿:{}", goodsDraftVo);
		Map<String, Object> returnMap = null;
		try {
			returnMap = goodsService.saveGoodsDraft(goodsDraftVo, this.getCurrentUserId());
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(returnMap);
	}

	/**
	 * 获取草稿列表 @param Map @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/getGoodsDrafts", method = RequestMethod.POST)
	public BaseReturnVo<Object> getGoodsDrafts(@RequestBody Map<String, Object> map) {
		logger.info("获取草稿列表:{}", map);
		Map<String, Object> goodsDrafts = null;
		try {
			goodsDrafts = goodsService.getGoodsDrafts(getCurrentUserId(), (String) map.get("goodsTypeCode"),
					(Integer) map.get("pageIndex"), (Integer) map.get("itemCount"));
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(goodsDrafts);
	}

	/**
	 * 删除用户已保存的商品草稿 @param Map @return BaseReturnVo<Object> @throws
	 */
	@SuppressWarnings("static-access")
	@RequiresAuthentication
	@RequestMapping(value = "/delGoodsDraft", method = RequestMethod.POST)
	@Log(name = "删除用户保存的商品草稿", model = "商品模块")
	public BaseReturnVo<Object> delGoodsDraft(@RequestBody Map<String, Object> map) {
		logger.info("删除用户已保存的商品草稿:{}", map);
		try {
			goodsService.delGoodsDraft((String) map.get("goodsDraftID"), this.getCurrentUserId());
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 获取商品草稿详情 @param Map @return BaseReturnVo<Object> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/getGoodsDraftDetail", method = RequestMethod.POST)
	public BaseReturnVo<Object> getGoodsDraftDetail(@RequestBody Map<String, Object> map) {
		logger.info("获取商品草稿详情:{}", map);
		Map<String, Object> goodsDraft = new HashMap<String, Object>();
		try {
			goodsDraft = goodsService.getGoodsDraftDetail((String) map.get("goodsDraftID"));
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(goodsDraft);
	}

	/**
	 * @Title: getExpressTemplets @Description: 商家用户获取运费模板 @param @param
	 *         map @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */

	@RequestMapping(value = "/getExpressTemplets", method = RequestMethod.POST)
	public BaseReturnVo<Object> getExpressTemplets(@RequestBody Map<String, String> param) {
		logger.info("商家用户获取运费模板的参数:{}", param);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = goodsService.getExpressTemplets(param);
		} catch (RestException e) {
			logger.error("商家用户获取运费模板请求异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("商家用户获取运费模板请求异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: getPostage @Description: 获取订单邮费 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/getPostage", method = RequestMethod.POST)
	public BaseReturnVo<Object> getPostage(@RequestBody List<Map<String, Object>> param) {
		logger.info(" 获取订单邮费:{}", param);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = goodsService.getPostage(param);
		} catch (RestException e) {
			logger.error(" 获取订单邮费:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: getExpressTempletDetis @Description: 获取商家用户运费模板详情 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/getExpressTempletDetis", method = RequestMethod.POST)
	public BaseReturnVo<Object> getExpressTempletDetis(@RequestBody Map<String, String> param) {
		logger.info("获取商家用户运费模板详情的参数:{}", param);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = goodsService.getExpressTemplets(param);
		} catch (RestException e) {
			logger.error("获取商家用户运费模板详情异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: editExpressTemplet @Description: 添加/编辑运费模板 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/editExpressTemplet", method = RequestMethod.POST)
	@Log(name = "添加/编辑运费模板", model = "运费模块")
	public BaseReturnVo<Object> editExpressTemplet(@RequestBody @Valid ExpressTempletVo expressTempletVo,
			BindingResult result) {
		logger.info("添加/编辑运费模板的参数:{}", expressTempletVo.toString());
		try {
			// 运送模块的编辑/添加
			goodsService.editExpressTemplet(expressTempletVo);
		} catch (RestException e) {
			logger.error("添加/编辑运费模板求异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加/编辑运费模板求异常:{}", e.getMessage());
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	@RequiresAuthentication
	@RequestMapping(value = "/delExpressWay", method = RequestMethod.POST)
	@Log(name = "删除运费方式", model = "运费模块")
	public BaseReturnVo<Object> delExpressWay(@RequestBody Map<String, String> param) {
		logger.info("删除运费模板的参数:{}", param);
		try {
			goodsService.delExpressWay(param.get("templetWayId"));
		} catch (RestException e) {
			logger.error("删除运费方式求异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除运费方式求异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	
	/**
	 * @Title: delExpressTemplet @Description: 删除运费模板 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/delExpressTemplet", method = RequestMethod.POST)
	@Log(name = "删除运费模板", model = "运费模块")
	public BaseReturnVo<Object> delExpressTemplet(@RequestBody Map<String, String> param) {
		logger.info("删除运费模板的参数:{}", param);
		try {
			goodsService.delExpressTemplet(param.get("templetID"));
		} catch (RestException e) {
			logger.error("删除运费模板求异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除运费模板求异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 获取平台拍卖商品出价记录
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBidRecords")
	public Object getBidRecord(@RequestBody GoodsQo qo) throws Exception {
		logger.info("获取平台拍卖商品出价记录:{}", qo);
		List<BidRecordVo> list = goodsService.getBidRecord(qo);
		BaseReturnVo<List<BidRecordVo>> returnVo = new BaseReturnVo<List<BidRecordVo>>(list);
		return returnVo;
	}

	/**
	 * 获取平台求购商品商家响应供货记录
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSupplyRecord")
	public Object getSupplyRecord(@RequestBody GoodsQo qo) throws Exception {
		logger.info("获取平台求购商品商家响应供货记录:{}", qo);
		List<BidRecordVo> list = goodsService.getSupplyRecord(qo);
		BaseReturnVo<List<BidRecordVo>> returnVo = new BaseReturnVo<List<BidRecordVo>>(list);
		return returnVo;
	}

	/**
	 * @Title: checkShopMarketing @Description: 获取店铺活动 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/checkShopMarketing", method = RequestMethod.POST)
	public BaseReturnVo<Object> checkShopMarketing(@RequestBody @Valid ShopQo shopQo, BindingResult bindingResult) {
		logger.info("获取店铺活动的参数:{}", shopQo.toString());
		Map<String, Object> result = null;
		try {
			result = shopService.checkShopMarketing(shopQo);
		} catch (RestException e) {
			logger.error("获取店铺活动的参数的异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取店铺活动的参数的异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: getGoods @Description: 获取商品评价记录 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/getGoods", method = RequestMethod.POST)
	public BaseReturnVo<Object> getGoods(@RequestBody Map<String, Object> param) {
		logger.info("获取商品评价记录的参数:{}", param);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = goodsService.getGoods(param, getPageInfo(param, Direction.DESC, "createTime"));
		} catch (RestException e) {
			logger.error("获取商品评价记录请求异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取商品评价记录请求异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * 获取商品鉴评标签 @param @return BaseReturnVo<Object> @throws
	 */
	@RequestMapping(value = "/getGoodsEvaluation", method = RequestMethod.POST)
	public BaseReturnVo<Object> getGoodsEvaluation(@RequestBody Map<String, Object> param) {
		logger.info("获取商品鉴评标签:{}", param);
		Map<String, Object> result = null;
		try {
			result = goodsService.getGoodsEvaluation(param, getPageInfo(param, Direction.DESC, "createTime"));
		} catch (RestException e) {
			logger.error("获取商品鉴评标签异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取商品鉴评标签异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * 获取商品鉴评标签详情 @param @return BaseReturnVo<Object> @throws
	 */
	@RequestMapping(value = "/getGoodsEvaluationDetail", method = RequestMethod.POST)
	public BaseReturnVo<Object> getGoodsEvaluationDetail(@RequestBody Map<String, Object> param) {
		logger.info("获取商品鉴评标签详情:{}", param);
		Map<String, Object> result = null;
		try {
			result = goodsService.getGoodsEvaluationDetail((String) param.get("evaluationGoodsId"));
		} catch (RestException e) {
			logger.error("获取商品鉴评标签详情异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取商品鉴评标签异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * 获取当前店铺正在销售的商品 @param @return BaseReturnVo<Object> @throws
	 */
	@RequestMapping(value = "/searchShopGoods", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchShopGoods(@RequestBody Map<String, Object> param) {
		logger.info("获取当前店铺正在销售的商品:{}", param);
		List<Goods> goods = goodsService.searchShopGoods(param);
		return new BaseReturnVo<Object>(goods);
	}
}
