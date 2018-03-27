package com.visionet.syh_mall.service.goods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.admin.AdminUserRepository;
import com.visionet.syh_mall.repository.admin.RoleRepository;
import com.visionet.syh_mall.repository.goods.EvaluationPicLinkRepository;
import com.visionet.syh_mall.vo.goods.EvaluationImgVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.FileManage;
import com.visionet.syh_mall.entity.admin.AdminUser;
import com.visionet.syh_mall.entity.admin.Role;
import com.visionet.syh_mall.entity.goods.EvaluationPicLink;
import com.visionet.syh_mall.entity.goods.EvaluationResult;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.goods.EvaluationResultRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.export.ExportEvaluationRestVo;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
import com.visionet.syh_mall.vo.goods.EvaluationResultVo;
import com.visionet.syh_mall.vo.goods.SearchEvaluationVo;

@Service
public class EvaluationResultService extends BaseService {
	@Autowired
	private EvaluationResultRepository evaluationResultDao;
	@Autowired
	private EvaluationPicLinkRepository evaluationPicLinkDao;
	@Autowired
	private FileManageRepostory fileManageDao;
	@Autowired
	private AdminUserRepository userDao;
	@Autowired
	private RoleRepository roleDao;

	private List<EvaluationImgVo> imgs;
	
	private static final String QRURL = "/player_app/tasting?d=";
	
	/**
	 * 添加/编辑鉴评结果 @param @return void @throws
	 * @param string 
	 */
	@Transactional
	public void addEvaluation(EvaluationResultVo evaluationResultVo, String userID) {
		// begin 鉴评编码生成
		// 第一位
		String collectTypeCode = evaluationResultVo.getCollectTypeCode().toString();
		// 二、三位
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date currentDate = DateUtil.getCurrentDate();
		String year = dateFormat.format(currentDate);
		String yearCode = year.substring(2, 4);
		// 客户号：第4-6位
		String factorCode = evaluationResultVo.getFactorCode();
		if(factorCode.length()!=3){
			throw new RestException("客户号异常");
		}
		// 最终鉴评编号
		String collectEvaluationCode = null;
		List<EvaluationResult> evaluations = evaluationResultDao.findByFactorCode(factorCode);
		if (evaluations.size() > 0) {
			String oldCode = evaluations.get(0).getCollectEvaluationCode();
			Long code = Long.valueOf(oldCode);
			Long newCode = code + 1;
			collectEvaluationCode = collectTypeCode + yearCode + factorCode + newCode.toString().substring(6, 12);
		} else {
			collectEvaluationCode = collectTypeCode + yearCode + factorCode + "000001";
		}
		evaluationResultVo.setCollectEvaluationCode(collectEvaluationCode);
		// end
		EvaluationResult evaluationResult = null;
		if (StringUtils.isEmpty(evaluationResultVo.getId())) {
			evaluationResult = new EvaluationResult();
		} else {
			evaluationResult = evaluationResultDao.findByIdAndIsDeleted(evaluationResultVo.getId(), 0);
		}
		AdminUser adminUser = userDao.findOne(userID);
		if(StringUtils.isEmpty(adminUser)){
			throw new RestException("login error");
		}
		String roleId = adminUser.getRoleId();
		Role role = roleDao.findOne(roleId);
		EvaluationResult save = evaluationResultDao
				.save(EvaluationResultVo.VoToPo(evaluationResult, evaluationResultVo,role.getRoleName()));
		save.setQrCode(FilePathUtils.QRCode(QRURL+save.getCollectEvaluationCode()+"&n=a"));
		if (evaluationResultVo.getOperationType() == 2&&!"存疑".equals(save.getIsTrue())) {
			List<EvaluationPicLink> pic = evaluationPicLinkDao.findByEvaluationIdAndIsDeleted(save.getId(), 0);
			if(pic.size()<=0){
				throw new RestException("请添加图片！");
			}
		}
		evaluationResultDao.save(save);
		imgs = evaluationResultVo.getImgs();
		imgs = (null == imgs ? new ArrayList<EvaluationImgVo>() : imgs);
		EvaluationPicLink evaluationPicLink = null;
		for (EvaluationImgVo img : imgs) {
			evaluationPicLink = new EvaluationPicLink();
			evaluationPicLinkDao.save(EvaluationImgVo.VoToPo(evaluationPicLink, img, save.getId()));
		}
	}

	/**
	 * 删除鉴评结果 @param @return void @throws
	 */
	public void delEvaluation(EvaluationResultVo evaluationResultVo) {
		String id = evaluationResultVo.getId();
		EvaluationResult evaluationResult = evaluationResultDao.findByIdAndIsDeleted(id, 0);
		evaluationResult.setIsDeleted(1);
		evaluationResult.setUpdateTime(DateUtil.getCurrentDate());
		evaluationResultDao.save(evaluationResult);
	}

	/**
	 * @Title: checkEvaluation @Description: 审核鉴评码 @param @param
	 *         evaluationResultVo 设定文件 @return void 返回类型 @throws
	 */
	public Boolean checkEvaluation(Map<String, Object> param) {
		EvaluationResult findByCollectEvaluationCode = evaluationResultDao
				.findByCollectEvaluationCode((String) param.get("collectEvaluationCode"));
		if (findByCollectEvaluationCode == null) {
			return false;
		}
		return true;
	}

	/**
	 * 获取鉴评结果 @param @return Map<String,Object> @throws
	 */
	public Map<String, Object> searchEvaluation(SearchEvaluationVo searchEvaluationVo) {
		PageInfo info = new PageInfo(searchEvaluationVo.getPageIndex(), searchEvaluationVo.getItemCount(),"updateTime",Direction.DESC);
		PageRequest request = buildPageRequest(info);
		Map<String, Object> searchEvaluation = DynamicParamConvert.searchEvaluation(searchEvaluationVo);
		Map<String, SearchFilter> parse = SearchFilter.parse(searchEvaluation);
		Page<EvaluationResult> page = evaluationResultDao
				.findAll(DynamicSpecifications.bySearchFilter(parse.values(), EvaluationResult.class), request);
		Map<String, Object> result = getEvaluationRsult(page);
		result.put("itemCount", page.getTotalElements());
		result.put("pageCount", page.getTotalPages());
		result.put("curPageIndex", info.getPageIndex());
		result.put("hasNext", page.hasNext());
		return result;
	}

	public static Map<String, Object> getEvaluationRsult(Page<EvaluationResult> page) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		for (EvaluationResult evaluationResult : page) {
			Map<String, Object> evaluation = new HashMap<String, Object>();
			evaluation.put("evaluationID", evaluationResult.getId());
			evaluation.put("collectNo", evaluationResult.getCollectEvaluationCode());
			evaluation.put("collectName", evaluationResult.getCollectName());
			evaluation.put("collectTypeCode", evaluationResult.getCollectTypeCode());
			evaluation.put("acceptance", evaluationResult.getAcceptance());
			evaluation.put("ratingScore", evaluationResult.getRatingScore());
			if (!StringUtils.isEmpty(evaluationResult.getRatingScore())) {
				evaluation.put("isTrue", 3);
			} else if (!StringUtils.isEmpty(evaluationResult.getIsTrue())) {
				evaluation.put("isTrue", "真品".equals(evaluationResult.getIsTrue()) ? 1 : 2);
			} else {
				evaluation.put("isTrue", null);
			}
			evaluation.put("evaluationNumber", evaluationResult.getEvaluationNumber());
			evaluation.put("evaluationTime", evaluationResult.getCreateTime());
			evaluation.put("status", evaluationResult.getFirstEvaluation());
			evaluation.put("division", evaluationResult.getEvaluationDivision());
			list.add(evaluation);
		}
		result.put("evaluationInfos", list);
		return result;
	}

	public void auditEvaluation(Map<String, Object> map, String userID) {
		String evaluationResultID = (String) map.get("evaluationResultID");
		String evaluationTypeCode = (String) map.get("evaluationTypeCode");
		String objection = (String) map.get("evaluationOffReason");
		AdminUser adminUser = userDao.findOne(userID);
		EvaluationResult evaluationResult = evaluationResultDao.findByIdAndIsDeleted(evaluationResultID, 0);
		evaluationResult.setEvaluationTypeCode(evaluationTypeCode);
		if ("evaluation_hold".equals(evaluationTypeCode)) {
			evaluationResult.setFirstEvaluation(1);
			evaluationResult.setObjection(objection);
		}
		if("evaluation_in".equals(evaluationTypeCode)){
			evaluationResult.setFirstEvaluation(1);
			evaluationResult.setObjection(objection);
		}
		evaluationResult.setUpdateTime(DateUtil.getCurrentDate());
		Role role = roleDao.findOne(adminUser.getRoleId());
		evaluationResult.setAuditBy(role.getRoleName());
		evaluationResultDao.save(evaluationResult);
	}

	public Map<String, Object> evaluationDetail(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		String evaluationResultId = (String) map.get("evaluationResultId");
		EvaluationResult evaluationResult = evaluationResultDao.findByIdAndIsDeleted(evaluationResultId, 0);
		List<EvaluationPicLink> picList = evaluationPicLinkDao.findByEvaluationIdAndIsDeleted(evaluationResult.getId(),
				0);
		List<Object> pic = new ArrayList<Object>();
		FileManage manage = null;
		String fileUrl = null;
		for (EvaluationPicLink evaluationPicLink : picList) {
			Map<String, Object> picResult = new HashMap<String, Object>();
			manage = fileManageDao.findOne(evaluationPicLink.getMaxImgId());
			fileUrl = FilePathUtils.fileUrl(manage.getFilePath());
			picResult.put("MaxImgId", manage.getId());
			picResult.put("MaxImgUrl", fileUrl);
			manage = fileManageDao.findOne(evaluationPicLink.getMidImgId());
			fileUrl = FilePathUtils.fileUrl(manage.getFilePath());
			picResult.put("MidImgId", manage.getId());
			picResult.put("MidImgUrl", fileUrl);
			manage = fileManageDao.findOne(evaluationPicLink.getMinImgId());
			fileUrl = FilePathUtils.fileUrl(manage.getFilePath());
			picResult.put("MinImgId", manage.getId());
			picResult.put("MinImgUrl", fileUrl);
			pic.add(picResult);
		}
		if (!StringUtils.isEmpty(evaluationResult.getRatingScore())) {
			evaluationResult.setIsTrue("3");
		} else if (!StringUtils.isEmpty(evaluationResult.getIsTrue())) {
			evaluationResult.setIsTrue("真品".equals(evaluationResult.getIsTrue()) ? "1" : "2");
		}
		result.put("evaluationResult", evaluationResult);
		result.put("evaluationImgs", pic);
		return result;
	}
	
	public Map<String, Object> evaluationResult(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		String evaluationResultId = (String) map.get("evaluationResultId");
		String jurisdiction = (String) map.get("jurisdiction");
		EvaluationResult evaluationResult = evaluationResultDao.findByCollectEvaluationCodeAndIsDeleted(evaluationResultId, 0);
		if (StringUtils.isEmpty(jurisdiction)&&!"evaluation_pass".equals(evaluationResult.getEvaluationTypeCode())){
			throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.NO_EVALUATION_ERROR.getDesc());
		}
		List<EvaluationPicLink> picList = evaluationPicLinkDao.findByEvaluationIdAndIsDeleted(evaluationResult.getId(),
				0);
		List<Object> pic = new ArrayList<Object>();
		FileManage manage = null;
		String fileUrl = null;
		for (EvaluationPicLink evaluationPicLink : picList) {
			Map<String, Object> picResult = new HashMap<String, Object>();
			manage = fileManageDao.findOne(evaluationPicLink.getMaxImgId());
			fileUrl = FilePathUtils.fileUrl(manage.getFilePath());
			picResult.put("MaxImgId", manage.getId());
			picResult.put("MaxImgUrl", fileUrl);
			manage = fileManageDao.findOne(evaluationPicLink.getMidImgId());
			fileUrl = FilePathUtils.fileUrl(manage.getFilePath());
			picResult.put("MidImgId", manage.getId());
			picResult.put("MidImgUrl", fileUrl);
			manage = fileManageDao.findOne(evaluationPicLink.getMinImgId());
			fileUrl = FilePathUtils.fileUrl(manage.getFilePath());
			picResult.put("MinImgId", manage.getId());
			picResult.put("MinImgUrl", fileUrl);
			pic.add(picResult);
		}
		result.put("evaluationResult", evaluationResult);
		result.put("evaluationImgs", pic);
		return result;
	}

	public List<ExportEvaluationRestVo> exportEvaluationExcel(Map<String, Object> param) {
		Map<String, SearchFilter> parse = SearchFilter.parse(param);
		List<EvaluationResult> list = evaluationResultDao.findAll(DynamicSpecifications.bySearchFilter(parse.values(), EvaluationResult.class));
		List<ExportEvaluationRestVo> excelParam = exportEvaluationExcelParam(list);
		return excelParam;
	}
	
	public static List<ExportEvaluationRestVo> exportEvaluationExcelParam(List<EvaluationResult> list){
		List<ExportEvaluationRestVo> result = new ArrayList<ExportEvaluationRestVo>();
		for (EvaluationResult evaluationResult : list) {
			ExportEvaluationRestVo evaluationRestVo = new ExportEvaluationRestVo();
			evaluationRestVo.setCollectCode(evaluationResult.getCollectEvaluationCode());
			evaluationRestVo.setCollectName(evaluationResult.getCollectName());
			evaluationRestVo.setCollectType(evaluationResult.getCollectTypeCode());
			evaluationRestVo.setFaceValue(evaluationResult.getFaceValue());
			evaluationRestVo.setPrinting(evaluationResult.getPrinting());
			evaluationRestVo.setQRCode(evaluationResult.getQrCode());
			evaluationRestVo.setRatingScore(evaluationResult.getRatingScore());
			evaluationRestVo.setReleaseUnit(evaluationResult.getReleaseUnit());
			evaluationRestVo.setReleaseYear(evaluationResult.getReleaseYear());
			evaluationRestVo.setSerialNumber(evaluationResult.getSerialNumber());
			evaluationRestVo.setTexture(evaluationResult.getTexture());
			evaluationRestVo.setWatermark(evaluationResult.getWatermark());
			result.add(evaluationRestVo);
		}
		return result;
	}
}
