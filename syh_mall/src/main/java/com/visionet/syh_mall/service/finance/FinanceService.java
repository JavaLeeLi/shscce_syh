package com.visionet.syh_mall.service.finance;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.visionet.syh_mall.common.string.StringUtil;
import com.visionet.syh_mall.entity.order.PaymentFlow;
import com.visionet.syh_mall.repository.order.PaymentFlowRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MessageUtils;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.common.utils.SequenceUUID;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.entity.UserAuthentication;
import com.visionet.syh_mall.entity.account.DrawCash;
import com.visionet.syh_mall.entity.account.FreezeRecord;
import com.visionet.syh_mall.entity.account.UserAccount;
import com.visionet.syh_mall.entity.account.UserBank;
import com.visionet.syh_mall.entity.finance.Bond;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.entity.order.OrderGoods;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.OperateLogRepository;
import com.visionet.syh_mall.repository.UserAuthenticationRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.finance.BondRepository;
import com.visionet.syh_mall.repository.finance.DrawCashRepository;
import com.visionet.syh_mall.repository.finance.FundSummaryImpl;
import com.visionet.syh_mall.repository.finance.IncomeImpl;
import com.visionet.syh_mall.repository.mbr.BondImpl;
import com.visionet.syh_mall.repository.mbr.FreezeRecordRepository;
import com.visionet.syh_mall.repository.mbr.UserAccountFlowRepository;
import com.visionet.syh_mall.repository.mbr.UserAccountRepository;
import com.visionet.syh_mall.repository.mbr.UserBankRepository;
import com.visionet.syh_mall.repository.mobile.OrderGoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.repository.mobile.ShopRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.account.UserAccountService;
import com.visionet.syh_mall.service.export.ExportFinanceVo;
import com.visionet.syh_mall.service.order.OrderPayService;
import com.visionet.syh_mall.service.order.OrderService;
import com.visionet.syh_mall.service.thridAccount.PayClientUtil;
import com.visionet.syh_mall.service.thridAccount.SoaAccountService;
import com.visionet.syh_mall.service.thridAccount.SoaDepositService;
import com.visionet.syh_mall.service.thridAccount.SoaMemberService;
import com.visionet.syh_mall.service.thridAccount.SoaTransferAccounts;
import com.visionet.syh_mall.vo.finance.FinanceVo;
import com.visionet.syh_mall.vo.finance.FundSummaryPo;
import com.visionet.syh_mall.vo.finance.FundSummaryVo;
import com.visionet.syh_mall.vo.finance.FundSummary;
import com.visionet.syh_mall.vo.finance.IncomeVo;
import com.visionet.syh_mall.vo.user.BondVo;

/**
 * 财务管理模块service层
 * 
 * @author mulongfei
 * @date 2017年10月20日下午3:53:14
 */
@Service
public class FinanceService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(FinanceService.class);
	@Autowired
	private UserAccountFlowRepository userAccountFlowDao;
	@Autowired
	private UserAccountRepository userAccountDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private DrawCashRepository drawCashDao;
	@Autowired
	private BondRepository bondDao;
	@Autowired
	private ShopRepository shopDao;
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private FreezeRecordRepository freezeRecordDao;
	@Autowired
	private OperateLogRepository operateLogDao;
	@Autowired
	private UserAuthenticationRepository userAuthenticationDao;
	@Autowired
	private OrderGoodsRepository orderGoodsDao;
	@Autowired
	private SoaMemberService soaMemberService;
	@Autowired
	private UserBankRepository userBankDao;
	@Autowired
	private BondImpl bondImpl;
	@Autowired
	private IncomeImpl incomeImpl;
	@Autowired
	private FundSummaryImpl fundSummaryImpl;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private SoaDepositService soaDepositService;
	@Autowired
	private OrderPayService orderPayService;
	@Autowired
	private SoaAccountService soaAccountService;
	@Autowired
	private KeyMappingRepository keyMappingDao;
	@Autowired
	private SoaTransferAccounts soaTransferAccounts;
	@Autowired
    private PaymentFlowRepository paymentFlowDao;
	
	/**
	 * 获取流水明细 @param @return Map<String,Object> @throws
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getFinanceRecord(FinanceVo vo) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		//当所有检索条件为空时，默认检索当天的数据
		if (StringUtils.isEmpty(vo.getContent())&&StringUtils.isEmpty(vo.getUserRealName())&&StringUtils.isEmpty(vo.getUserLoginName())&&StringUtils.isEmpty(vo.getBusinessType())&&StringUtils.isEmpty(vo.getStartTime())&&StringUtils.isEmpty(vo.getEndTime())&&StringUtils.isEmpty(vo.getFlowType())) {
			DateFormat cnDateFormat = DateUtil.getCnDateFormat(DateUtil.YMD1);
			String formatDate = cnDateFormat.format(DateUtil.getCurrentDate());
			Date newDate = null;
			try {
				newDate = DateUtil.convertDate(formatDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			vo.setStartTime(newDate);
			vo.setEndTime(DateUtil.seekDate(newDate, 1));
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//查询数据
		Map<String, Object> searchResult = searchResult(vo);
		//当前分页数据
		Page<UserAccountFlow> allUserFlow = (Page<UserAccountFlow>) searchResult.get("allUserFlow");
		//总数据
		List<UserAccountFlow> allUser = (List<UserAccountFlow>) searchResult.get("allUser");
		//分页信息
		PageInfo pageInfo = (PageInfo) searchResult.get("pageInfo");
		//返回参数拼装
		list = returnResult(list, allUserFlow);
		// begin 分页信息
		BigDecimal allInAmt = new BigDecimal(0);
		BigDecimal allOutAmt = new BigDecimal(0);
		//汇总当前页金额与总金额
		for (UserAccountFlow userAccountFlow : allUser) {
			if(userAccountFlow.getType()==0){
				allInAmt = allInAmt.add(userAccountFlow.getAmt());
			}
			if(userAccountFlow.getType()==1){
				allOutAmt = allOutAmt.add(userAccountFlow.getAmt());
			}
		}
		BigDecimal pageInAmt = new BigDecimal(0);
		BigDecimal pageOutAmt = new BigDecimal(0);
		for (UserAccountFlow userAccountFlow : allUserFlow) {
			if(userAccountFlow.getType()==0){
				pageInAmt = pageInAmt.add(userAccountFlow.getAmt());
			}
			if(userAccountFlow.getType()==1){
				pageOutAmt = pageOutAmt.add(userAccountFlow.getAmt());
			}
		}
		//当前页收入
		returnMap.put("pageInAmt", pageInAmt);
		//当前页支出
		returnMap.put("pageOutAmt", pageOutAmt);
		//总支出
		returnMap.put("allInAmt", allInAmt);
		//总收入
		returnMap.put("allOutAmt", allOutAmt);
		
		returnMap.put("itemCount", allUserFlow.getTotalElements());
		returnMap.put("pageCount", allUserFlow.getTotalPages());
		returnMap.put("curPageIndex", pageInfo.getPageIndex());
		returnMap.put("hasNext", allUserFlow.hasNext());
		returnMap.put("userAccountFlow", list);
		// end
		return returnMap;
	}
	
	/**
	 * 数据查询
	 * @param 
	 * @return Map<String,Object>
	 * @throws
	 */
	private Map<String,Object> searchResult(FinanceVo vo){
		// 查询条件拼接
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(vo.getFlowType())) {
			sqlMap.put("EQ_flowType", vo.getFlowType());
		}
		if (!StringUtils.isEmpty(vo.getBusinessType())) {
			sqlMap.put("EQ_businessType", vo.getBusinessType());
		}
		if (!StringUtils.isEmpty(vo.getPayMethod())) {
			sqlMap.put("EQ_payMethod", vo.getPayMethod());
		}
		if (!StringUtils.isEmpty(vo.getContent())) {
			sqlMap.put("EQ_content", vo.getContent());
		}
		if (!StringUtils.isEmpty(vo.getUserLoginName())) {
			List<String> userIds = userDao.findByloginName(vo.getUserLoginName());
			if (userIds.size() > 0) {
				sqlMap.put("IN_userId", userIds);
			} else {
				userIds = new ArrayList<String>();
				userIds.add("1");
				sqlMap.put("IN_userId", userIds);
			}
		}
		if (!StringUtils.isEmpty(vo.getUserRealName())) {
			List<String> userIds = userAuthenticationDao.findByUserName(vo.getUserRealName());
			if (userIds.size() > 0) {
				sqlMap.put("IN_userId", userIds);
			} else {
				userIds = new ArrayList<String>();
				userIds.add("1");
				sqlMap.put("IN_userId", userIds);
			}
		}
		if (!StringUtils.isEmpty(vo.getStartTime())) {
			sqlMap.put("GTE_createTime", vo.getStartTime());
		}
		if (!StringUtils.isEmpty(vo.getEndTime())) {
			sqlMap.put("LT_createTime", DateUtil.seekDate(vo.getEndTime(),1));
		}
		sqlMap.put("EQ_status", "success");
		Map<String, SearchFilter> parse = SearchFilter.parse(sqlMap);
		//分页条件
		PageInfo pageInfo = new PageInfo(vo.getPageIndex(), vo.getItemCount());
		//执行查询
		Page<UserAccountFlow> allUserFlow = userAccountFlowDao.findAll(
				DynamicSpecifications.bySearchFilter(parse.values(), UserAccountFlow.class),
				pageInfo.getPageRequestInfo());
		List<UserAccountFlow> allUser = userAccountFlowDao.findAll(
				DynamicSpecifications.bySearchFilter(parse.values(), UserAccountFlow.class));
		// end
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("allUserFlow", allUserFlow);
		result.put("allUser", allUser);
		result.put("pageInfo", pageInfo);
		return result;
	}

	private List<Map<String, Object>> returnResult(List<Map<String, Object>> list, Page<UserAccountFlow> allUserFlow) {
		// begin 返回数据拼装
		for (UserAccountFlow userAccountFlow : allUserFlow) {
			Map<String, Object> map = new HashMap<String, Object>();
			String userId = userAccountFlow.getUserId();
			User user = userDao.findOne(userId);
			map.put("userLoginName", user.getLoginName());
			UserAuthentication userAuthentication = userAuthenticationDao.findByUserId(userId);
			if (null != userAuthentication) {
				map.put("userName", userAuthentication.getUserRealName());
			} else {
				map.put("userName", user.getAliasName());
			}
			map.put("flowNo", userAccountFlow.getOrderNo());
			Order order = orderDao.findByOrderSn(userAccountFlow.getOrderNo());
			if (StringUtils.isEmpty(order)) {
				map.put("expressFee", null);
				map.put("goodName", null);
			} else {
				List<OrderGoods> orderGoods = orderGoodsDao.findByOrderId(order.getId());
				List<String> goodsName = null;
				for (OrderGoods orderGood : orderGoods) {
					goodsName = new ArrayList<String>();
					goodsName.add(orderGood.getGoodsName());
				}
				map.put("expressFee", order.getExpressFee());
				map.put("goodName", goodsName);
			}
			map.put("businessType", userAccountFlow.getBusinessType());
			map.put("flowType", userAccountFlow.getFlowType());
			if (userAccountFlow.getType() == 0) {
				map.put("income", userAccountFlow.getAmt());
				map.put("expend", 0);
			} else {
				map.put("income", 0);
				map.put("expend", userAccountFlow.getAmt());
			}
			map.put("described", userAccountFlow.getContent());
			map.put("flowCreateTime", userAccountFlow.getCreateTime());
			map.put("payMethod", userAccountFlow.getPayMethod());
			map.put("remark", userAccountFlow.getRemark());
			list.add(map);
		}
		// end
		return list;
	}

	/**
	 * 获取导出数据
	 */
	public List<ExportFinanceVo> getFinanceExport(){
		List<ExportFinanceVo> list = new ArrayList<ExportFinanceVo>();
		List<Object[]> financeList = userAccountFlowDao.getExportFinanceList();
		for (Object[] objects : financeList) {
			ExportFinanceVo exportFinanceVo = new ExportFinanceVo();
			exportFinanceVo.setUserLoginName((String) objects[0]);
			exportFinanceVo.setUserName(objects[1]==null?(String)objects[11]:(String)objects[1]);
			exportFinanceVo.setFlowNo((String) objects[2]);
			exportFinanceVo.setBusinessType((String) objects[3]);
			exportFinanceVo.setFlowType((String) objects[4]);
			if((Integer)objects[5]==0){
				exportFinanceVo.setIncome(OrderService.countAmt(new BigDecimal(objects[6].toString())));
				exportFinanceVo.setExpend(new BigDecimal(0));
			}else{
				exportFinanceVo.setIncome(new BigDecimal(0));
				exportFinanceVo.setExpend(OrderService.countAmt(new BigDecimal(objects[6].toString())));
			}
			exportFinanceVo.setDescribed((String) objects[7]);
			exportFinanceVo.setFlowCreateTime((Date) objects[8]);
			exportFinanceVo.setRemark((String) objects[9]);
			String payMethod = (String) objects[10];
			exportFinanceVo.setPayMethod(payMethod);
			if("GATEWAY".equals(payMethod)){
				exportFinanceVo.setPayMethod("银行卡支付");
			}
			if("BALANCE".equals(payMethod)){
				exportFinanceVo.setPayMethod("余额支付");
			}
			if("SCAN_ALIPAY".equals(payMethod)){
				exportFinanceVo.setPayMethod("支付宝扫码支付");
			}
			if("SCAN_WEIXIN".equals(payMethod)){
				exportFinanceVo.setPayMethod("微信扫码支付");
			}
			if("WECHAT_PUBLIC".equals(payMethod)){
				exportFinanceVo.setPayMethod("微信公众号支付");
			}
			list.add(exportFinanceVo);
		}
		BigDecimal allInAmt = new BigDecimal(0);
		BigDecimal allOutAmt = new BigDecimal(0);
		for (ExportFinanceVo exportFinanceVo : list) {
			allInAmt = allInAmt.add(exportFinanceVo.getIncome());
			allOutAmt = allOutAmt.add(exportFinanceVo.getExpend());
		}
		ExportFinanceVo exportFinanceVo = new ExportFinanceVo();
		exportFinanceVo.setExpend(allOutAmt);
		exportFinanceVo.setIncome(allInAmt);
		list.add(exportFinanceVo);
		return list;
	}
	/**
	 * 用户余额查询
	 * @param 
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public Map<String, Object> getAccountBalance(FinanceVo vo) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// begin 数据查询
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(vo.getUserLoginName())) {
			List<String> userId = userDao.findByloginName(vo.getUserLoginName());
			if (userId.size() == 0) {
				userId.add("1");
			}
			sqlMap.put("IN_userId", userId);
		}
		if (!StringUtils.isEmpty(vo.getMinBalance())) {
			sqlMap.put("GTE_withdrawal", vo.getMinBalance());
		}
		if (!StringUtils.isEmpty(vo.getMaxBalance())) {
			sqlMap.put("LTE_withdrawal", vo.getMaxBalance());
		}
		if (!StringUtils.isEmpty(vo.getMinFrozenAmt())) {
			sqlMap.put("GTE_frozenAmt", vo.getMinFrozenAmt());	
		}
		if (!StringUtils.isEmpty(vo.getMaxFrozenAmt())) {
			sqlMap.put("LTE_frozenAmt", vo.getMaxFrozenAmt());			
		}
		Map<String, SearchFilter> parse = SearchFilter.parse(sqlMap);
		PageInfo pageInfo = new PageInfo(vo.getPageIndex(), vo.getItemCount());
		Page<UserAccount> allAccountBalance = userAccountDao.findAll(
				DynamicSpecifications.bySearchFilter(parse.values(), UserAccount.class), pageInfo.getPageRequestInfo());
		// end
		// begin 返回数据拼装
		for (UserAccount userAccount : allAccountBalance) {
			Map<String, Object> map = new HashMap<String, Object>();
			User user = userDao.findOne(userAccount.getUserId());
			List<UserAccountFlow> userFlow = userAccountFlowDao.findInUserIdAndContent(userAccount.getUserId());
			BigDecimal totalAccount = new BigDecimal(0);
			for (UserAccountFlow userAccountFlow : userFlow) {
				totalAccount = totalAccount.add(userAccountFlow.getAmt());
			}
			FreezeRecord record = freezeRecordDao.findByBizIdAndUserId(user.getId(), user.getId(), "freeze");
			map.put("isFreeze", record != null ? "已冻结" : "未冻结");
			map.put("accountName", user.getLoginName());
			map.put("userName", user.getAliasName());
			BigDecimal frozenAmt = userAccount.getFrozenAmt();
			if (StringUtils.isEmpty(frozenAmt)) {
				frozenAmt = new BigDecimal(0);
			}
			BigDecimal totalPrice = new BigDecimal(0);
			BigDecimal total = orderDao.findTotalByShopOwnerId(user.getId());
			if (total != null) {
				totalPrice = totalPrice.add(total);
			}
			BigDecimal expressFee = new BigDecimal(0);
			BigDecimal express = orderDao.findExpressByShopOwnerId(user.getId());
			if (express != null) {
				expressFee = expressFee.add(express);
			}
			map.put("settleAccountsAmt", totalPrice.add(expressFee));
			map.put("availableBalance", userAccount.getBalance().subtract(frozenAmt));
			map.put("frozenAmt", userAccount.getFrozenAmt());
			KeyMapping keyMapping = keyMappingDao.findByKeyCode(user.getUserStatusCode());
			map.put("userStatus", keyMapping.getValueDesc());
			map.put("userType", user.getMemberType()==2?"企业用户":"个人用户");
			map.put("allRecharge", totalAccount);
			
			//店铺状态
			Shop shop = shopDao.findByUserId(user.getId());
			String statusCode = null;
			if(shop!=null){
				KeyMapping mapping = keyMappingDao.findByKeyCode(shop.getStatusCode());
				statusCode = mapping.getValueDesc();
			}
			map.put("shopStatus", statusCode);
			
			list.add(map);
		}
		// end
		// begin 分页信息封装
		returnMap.put("accountBalance", list);
		returnMap.put("itemCount", allAccountBalance.getTotalElements());
		returnMap.put("pageCount", allAccountBalance.getTotalPages());
		returnMap.put("curPageIndex", pageInfo.getPageIndex());
		returnMap.put("hasNext", allAccountBalance.hasNext());
		// end
		return returnMap;
	}

	/**
	 * 获取提现记录 @param @return Map<String,Object> @throws
	 */
	public Map<String, Object> getWithdrawalRecord(FinanceVo vo) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// begin 数据查询
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(vo.getUserLoginName())) {
			List<String> userId = userDao.findByloginName(vo.getUserLoginName());
			if (userId.size() == 0) {
				userId.add("1");
			}
			sqlMap.put("IN_userId", userId);
		}
		if (!StringUtils.isEmpty(vo.getStartTime())) {
			sqlMap.put("GTE_createTime", vo.getStartTime());
		}
		if (!StringUtils.isEmpty(vo.getEndTime())) {
			sqlMap.put("LTE_createTime", vo.getEndTime());
		}
		if (!StringUtils.isEmpty(vo.getDrawCashState())) {
			sqlMap.put("EQ_statusCode", vo.getDrawCashState());
		}
		if (!StringUtils.isEmpty(vo.getCardNo())) {
			sqlMap.put("LIKE_drawAccount", vo.getCardNo());
		}
		Map<String, SearchFilter> parse = SearchFilter.parse(sqlMap);
		PageInfo pageInfo = new PageInfo(vo.getPageIndex(), vo.getItemCount());
		Page<DrawCash> pageDrawCash = drawCashDao.findAll(
				DynamicSpecifications.bySearchFilter(parse.values(), DrawCash.class), pageInfo.getPageRequestInfo());
		// end
		// begin 返回数据拼装
		for (DrawCash drawCash : pageDrawCash) {
			Map<String, Object> map = new HashMap<String, Object>();
			User user = userDao.findById(drawCash.getUserId());
			map.put("drawCashId", drawCash.getId());
			map.put("drawWay", drawCash.getDrawWay());
			map.put("mbrName", user.getLoginName());
			map.put("withdrawalSal", drawCash.getDrawAmt());
			map.put("drawOrderNo", drawCash.getDrawOrderNo());
			map.put("collectionAccount", drawCash.getDrawAccount());
			map.put("accountName", drawCash.getAccountName());
			map.put("state", drawCash.getStatusCode());
			map.put("applyTime", drawCash.getCreateTime());
			map.put("rejectReason", drawCash.getRejectReason());
			list.add(map);
		}
		// end
		// begin 分页数据封装
		returnMap.put("WithdrawalRecord", list);
		returnMap.put("itemCount", pageDrawCash.getTotalElements());
		returnMap.put("pageCount", pageDrawCash.getTotalPages());
		returnMap.put("curPageIndex", pageInfo.getPageIndex());
		returnMap.put("hasNext", pageDrawCash.hasNext());
		// end
		return returnMap;
	}

	/**
	 * 获取保证金 @param @return BaseReturnVo<Object> @throws
	 */
	public Map<String, Object> getCashDeposit(FinanceVo vo) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	/*	if (!StringUtils.isEmpty(vo.getUserLoginName())){
			vo.setShopName(shopDao.findById(vo.getUserLoginName()).getShopName());
		}*/
		// begin 数据查询
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(vo.getShopName())) {
			List<String> shopIds = shopDao.getShopIds(vo.getShopName());
			if (shopIds.size() == 0) {
				shopIds.add("1");
			}
			sqlMap.put("IN_shopId", shopIds);
		}
		if (!StringUtils.isEmpty(vo.getUserLoginName())) {
			//List<String> userIds = userDao.findByloginName(vo.getUserLoginName());
			List<String> shopIds = new ArrayList<String>();
			shopIds.add(vo.getUserLoginName());
		/*	for (String userId : userIds) {
				Shop shop = shopDao.findByUserId(userId);
				shopIds.add(shop.getId());
			}*/
			sqlMap.put("IN_shopId", shopIds);
		}
		if (!StringUtils.isEmpty(vo.getPaymentStatus())) {
			sqlMap.put("EQ_bondStatus", vo.getPaymentStatus());
		}
		Map<String, SearchFilter> parse = SearchFilter.parse(sqlMap);
		PageInfo pageInfo = new PageInfo(vo.getPageIndex(), vo.getItemCount());
		Page<Bond> allBond = bondDao.findAll(DynamicSpecifications.bySearchFilter(parse.values(), Bond.class),
				pageInfo.getPageRequestInfo());
		// end
		// begin 返回数据拼装
		for (Bond bond : allBond) {
			Map<String, Object> map = new HashMap<String, Object>();
			Shop shop = shopDao.findById(bond.getShopId());
			map.put("userId", shop.getUserId());
			map.put("shopName", shop.getShopName());
			map.put("shopId", shop.getId());
			map.put("bondStatus", bond.getBondStatus());
			map.put("bondAmt", bond.getBondAmt());
			map.put("shopBalance", bond.getShopBalance());
			if (bond.getBondStatus() == 1) {
				map.put("payForBond", bond.getBondAmt().subtract(bond.getShopBalance()));
			} else {
				map.put("payForBond", 0);
			}
			map.put("balanceTime", bond.getUpdateTime());
			list.add(map);
		}
		// end
		// begin 分页数据封装
		returnMap.put("CashDeposit", list);
		returnMap.put("itemCount", allBond.getTotalElements());
		returnMap.put("pageCount", allBond.getTotalPages());
		returnMap.put("curPageIndex", pageInfo.getPageIndex());
		returnMap.put("hasNext", allBond.hasNext());
		// end
		return returnMap;
	}

	/**
	 * 保证金催缴 @param @return void @throws
	 */
	public void paymentCashDeposit(Map<String, Object> param, String adminId) {
		User user = userDao.findOne((String) param.get("userId"));
		MessageUtils.sendText(user.getPhone(), "{text}", "请尽快去缴纳保证金！");
		operateLogDao.save(addLog(null, adminId, "对" + user.getLoginName() + "催交保证金！"));
	}

	/**
	 * 编辑保证金 @param @return void @throws
	 */
	@Transactional
	public void editCashDeposit(Map<String, Object> param, String adminId) {
		User user = userDao.findOne((String) param.get("userId"));
		Shop shop = shopDao.findByUserId(user.getId());
		Bond bond = bondDao.findByShopId(shop.getId());
		bond.setBondAmt(new BigDecimal((Integer) param.get("bondAmt")));
		if(bond.getShopBalance().compareTo(bond.getBondAmt())==-1){
			bond.setBondStatus(1);
		}
		bond.setUpdateTime(DateUtil.getCurrentDate());
		bondDao.save(bond);
		addLog(null, adminId, "管理员编辑用户：" + user.getId() + "保证金，金额为：" + bond.getBondAmt());
	}

	/**
	 * 解绑银行卡
	 * @param 
	 * @return void
	 * @throws
	 */
	public void unbindBankCard(String bankCard, String userId) {
		JSONObject unbindBankCard = soaMemberService.unbindBankCard(userId, bankCard);
		try {
			if("OK".equals(unbindBankCard.get("status"))){
				UserBank userBank = userBankDao.findByUserIdAndCardNoAndStatus(userId, bankCard,"success");
				userBank.setStatus("unbind");
				userBankDao.save(userBank);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.THREE_SPACE_ERROR.getDesc());
		}
	}

	public List<BondVo> searchCashDeposit(Map<String, Object> param) {
		List<Object[]> searchBond = bondImpl.searchBond(param);
		List<BondVo> result = new ArrayList<BondVo>();
		for (Object[] objects : searchBond) {
			BondVo bondVo = new BondVo();
			bondVo.setShopName((String) objects[0]);
			Integer status = (Integer) objects[1];
			bondVo.setStatus(status==0?"正常":"欠费");
			bondVo.setBondAmt(OrderService.countAmt((BigDecimal) objects[2]));
			bondVo.setShopBalance(OrderService.countAmt((BigDecimal) objects[3]));
			BigDecimal bondAmt = (BigDecimal) objects[2];
			BigDecimal amt = (BigDecimal) objects[3];
			if (bondAmt.compareTo(amt)==1){				
				bondVo.setAmt(OrderService.countAmt(bondAmt.subtract(amt)));
			}else{
				bondVo.setAmt(new BigDecimal(0));
			}
			bondVo.setBondTime((Date) objects[4]);
			result.add(bondVo);
		}
		return result;
	}

	/**
	 * 分页使用
	 * @param 
	 * @return List<IncomeVo>
	 * @throws
	 */
	public Map<String,Object> searchIncomePage(Map<String,Object> param) {
		Map<String,Object> result = new HashMap<String, Object>();
		int pageIndex = (int) param.get("pageIndex");
		int itemCount = (int) param.get("itemCount");
		List<IncomeVo> list = new ArrayList<IncomeVo>();
		List<Object[]> searchIncome = incomeImpl.searchIncome(param);
		//数据总条数
		int total = searchIncome.size();
		//总页数
		int allPage = total%itemCount==0?total/itemCount:total/itemCount+1;
		if(pageIndex*itemCount<=searchIncome.size()){
			searchIncome = searchIncome.subList((pageIndex-1)*itemCount, pageIndex*itemCount);
		}else{
			searchIncome = searchIncome.subList((pageIndex-1)*itemCount, searchIncome.size());			
		}
		list = getResultList(list, searchIncome);
		result.put("Income", list);
		result.put("itemCount", total);
		result.put("pageCount", allPage);
		result.put("curPageIndex", pageIndex);
		result.put("hasNext", pageIndex<allPage?true:false);
		return result;
	}

	private List<IncomeVo> getResultList(List<IncomeVo> list, List<Object[]> searchIncome) {
		for (Object[] objects : searchIncome) {
			IncomeVo incomeVo = new IncomeVo();
			incomeVo.setUserLoginName((String) objects[0]);
			incomeVo.setUserName((String) objects[1]);
			incomeVo.setCreateTime((Date) objects[2]);
			incomeVo.setOrderNo((String) objects[3]);
            PaymentFlow paymentFlow = paymentFlowDao.findByBussOrderNo((String) objects[3]);
            if (StringUtils.isEmpty(paymentFlow)){
                incomeVo.setRemark((String) objects[6]);
            }else{
                incomeVo.setRemark("商品订单号："+paymentFlow.getSourceOrderNo());
            }
            incomeVo.setIncomeAmt(OrderService.countAmt((BigDecimal) objects[4]));
			incomeVo.setIncomeType((String) objects[5]);
			list.add(incomeVo);
		}
		return list;
	}


	public List<IncomeVo> searchIncome(Map<String,Object> param) {
		List<IncomeVo> list = new ArrayList<IncomeVo>();
		List<Object[]> searchIncome = incomeImpl.searchIncome(param);
		list = getResultList(list, searchIncome);
		return list;
	}

	/**
	 * 页面显示分页数据
	 * @param 
	 * @return FundSummaryVo
	 * @throws
	 */
	public FundSummaryVo searchFundSummary(FundSummaryPo po) {
		FundSummaryVo fundSummaryVo = new FundSummaryVo();
		List<FundSummary> list = new ArrayList<FundSummary>();
		List<Object[]> searchFundSummary = fundSummaryImpl.searchFundSummary(po);
		List<Object[]> searchFund = null;
		if(po.getPageIndex()*po.getItemCount()<=searchFundSummary.size()){
			searchFund = searchFundSummary.subList((po.getPageIndex()-1)*po.getItemCount(), po.getPageIndex()*po.getItemCount());
		}else{
			searchFund = searchFundSummary.subList((po.getPageIndex()-1)*po.getItemCount(),searchFundSummary.size());
		}
		//查询结果赋值
		list = fundSummaryResult(list, searchFund);
		fundSummaryVo.setFundSummarys(list);
		fundSummaryVo.setItemCount(searchFundSummary.size());
		fundSummaryVo.setPageCount(searchFundSummary.size()%po.getItemCount()==0?searchFundSummary.size()/po.getItemCount():searchFundSummary.size()/po.getItemCount()+1);
		fundSummaryVo.setCurPageIndex(po.getPageIndex());
		fundSummaryVo.setHasNext(po.getPageIndex()<fundSummaryVo.getPageCount()?true:false);
		return fundSummaryVo;
	}
	
	/**
	 * 导出数据使用
	 * @param 
	 * @return FundSummaryVo
	 * @throws
	 */
	public FundSummaryVo exportFundSummary(FundSummaryPo po) {
		FundSummaryVo fundSummaryVo = new FundSummaryVo();
		List<FundSummary> list = new ArrayList<FundSummary>();
		List<Object[]> searchFundSummary = fundSummaryImpl.searchFundSummary(po);
		//查询结果赋值
		list = fundSummaryResult(list, searchFundSummary);
		fundSummaryVo.setFundSummarys(list);
		return fundSummaryVo;
	}

	private List<FundSummary> fundSummaryResult(List<FundSummary> list, List<Object[]> searchFundSummary) {
		for (Object[] objects : searchFundSummary) {
			FundSummary fundSummary = new FundSummary();
			fundSummary.setUserLoginName((String) objects[0]);
			fundSummary.setUserName((String) objects[1]);
			fundSummary.setAccountCreateTime((Date) objects[2]);
			fundSummary.setUserType((int)objects[26]==2?"企业用户":"个人用户");
			BigDecimal fIncome = (BigDecimal) objects[3];//期初收入
			if(null==fIncome){
				fIncome = new BigDecimal(0);
			}
			BigDecimal fExpend = (BigDecimal) objects[4];//期初支出
			if(null==fExpend){
				fExpend = new BigDecimal(0);
			}
			BigDecimal lIncome = (BigDecimal) objects[5];//期末收入
			if(null==lIncome){
				lIncome = new BigDecimal(0);
			}
			BigDecimal lexpend = (BigDecimal) objects[6];//期末支出
			if(null==lexpend){
				lexpend = new BigDecimal(0);
			}
			fundSummary.setFirstAmt(OrderService.countAmt(fIncome.subtract(fExpend)));
			fundSummary.setLastAmt(OrderService.countAmt(lIncome.subtract(lexpend)));
			fundSummary.setFrozenAmt(OrderService.countAmt((BigDecimal) objects[7]==null?new BigDecimal(0):(BigDecimal) objects[7]));
			BigDecimal frozen = (BigDecimal) objects[7];
			if(null==frozen){
				frozen = new BigDecimal(0);
			}
			fundSummary.setWithdrawal(OrderService.countAmt(fundSummary.getLastAmt().subtract(frozen)));
			fundSummary.setIncomeAmt(OrderService.countAmt((BigDecimal) objects[27]==null?new BigDecimal(0):(BigDecimal) objects[27]));
			fundSummary.setWithdrawAmt(OrderService.countAmt((BigDecimal) objects[8]==null?new BigDecimal(0):(BigDecimal) objects[8]));
			fundSummary.setSellIncome(OrderService.countAmt((BigDecimal) objects[9]==null?new BigDecimal(0):(BigDecimal) objects[9]));
			fundSummary.setSellExpend(OrderService.countAmt((BigDecimal) objects[10]==null?new BigDecimal(0):(BigDecimal) objects[10]));
			fundSummary.setBidIncome(OrderService.countAmt((BigDecimal) objects[11]==null?new BigDecimal(0):(BigDecimal) objects[11]));
			fundSummary.setBidExpend(OrderService.countAmt((BigDecimal) objects[12]==null?new BigDecimal(0):(BigDecimal) objects[12]));
			fundSummary.setBuyIncome(OrderService.countAmt((BigDecimal) objects[13]==null?new BigDecimal(0):(BigDecimal) objects[13]));
			fundSummary.setBuyExpend(OrderService.countAmt((BigDecimal) objects[14]==null?new BigDecimal(0):(BigDecimal) objects[14]));
			fundSummary.setPenalSumIncome(OrderService.countAmt((BigDecimal) objects[15]==null?new BigDecimal(0):(BigDecimal) objects[15]));
			fundSummary.setPenalSumExpend(OrderService.countAmt((BigDecimal) objects[16]==null?new BigDecimal(0):(BigDecimal) objects[16]));
			fundSummary.setMarketIncome(OrderService.countAmt((BigDecimal) objects[17]==null?new BigDecimal(0):(BigDecimal) objects[17]));
			fundSummary.setMarketExpend(OrderService.countAmt((BigDecimal) objects[18]==null?new BigDecimal(0):(BigDecimal) objects[18]));
			fundSummary.setFreightIncome(OrderService.countAmt((BigDecimal) objects[19]==null?new BigDecimal(0):(BigDecimal) objects[19]));
			fundSummary.setPremiumExpendAmt(OrderService.countAmt((BigDecimal) objects[20]==null?new BigDecimal(0):(BigDecimal) objects[20]));
			fundSummary.setPremiumIncomeAmt(OrderService.countAmt((BigDecimal) objects[21]==null?new BigDecimal(0):(BigDecimal) objects[21]));
			fundSummary.setServiceExpendAmt(OrderService.countAmt((BigDecimal) objects[22]==null?new BigDecimal(0):(BigDecimal) objects[22]));
			fundSummary.setServiceIncomeAmt(OrderService.countAmt((BigDecimal) objects[23]==null?new BigDecimal(0):(BigDecimal) objects[23]));
			fundSummary.setCommissionIncomeAmt(OrderService.countAmt((BigDecimal) objects[24]==null?new BigDecimal(0):(BigDecimal) objects[24]));
			fundSummary.setCommissionExpendAmt(OrderService.countAmt((BigDecimal) objects[25]==null?new BigDecimal(0):(BigDecimal) objects[25]));
			list.add(fundSummary);
		}
		return list;
	}

	/**
	 * 保证金支付
	 * @param 
	 * @return String
	 * @throws
	 */
	public Map<String,Object> paymentCashDeposit(FinanceVo vo,String userId) throws Exception {
		// 验证余额是否充足
		if ("BALANCE".equals(vo.getPayMethod())) {
			UserAccount userAccount = userAccountService.getUserAccountInfo(userId);
			if (null == userAccount || -1 == userAccount.getWithdrawal().compareTo(vo.getAmt())) {
				throw new RestException("账户可用余额不足");
			}
		}
		Long amt = Long.valueOf(AmountUtils.changeY2F(vo.getAmt().toString())); // 支付金额 元转分

		String orderNo = SequenceUUID.getOrderIdByUUId("B");
		
		// 提前添加待确认流水
		UserAccountFlow flow = new UserAccountFlow();
		flow.setAmt(vo.getAmt());
		flow.setContent("保证金支出");
		flow.setOrderNo(orderNo);
		flow.setType(1);
		flow.setUserId(userId);
		flow.setBusinessType("交易");
		flow.setFlowType("支出");
		flow.setStatus("等待确认");
		flow.setPayMethod(vo.getPayMethod());
		userAccountFlowDao.save(flow);
		
		//获取认证信息，取到bankCode
		UserAuthentication authentication = userAuthenticationDao.findByUserId(userId);
		String bankCode = authentication.getBankCode();
		Map<String, String> result = soaDepositService.applyHostCollect(userId, amt, vo.getPayMethod(), orderNo, bankCode, vo.getFrontUrl(), vo.getShopId());
		
		//Map<String, String> result = soaDepositService.applyHostCollect(userId, amt, vo.getPayMethod(), orderNo, "vbank", vo.getFrontUrl(), vo.getShopId());
		String payUrl = null;
		Map<String,Object> res = new HashMap<String,Object>();
		// 提交托管代收申请
		if ("BALANCE".equals(vo.getPayMethod())) {
			logger.info("提交托管代付申请：{}",vo.getPayMethod());
			payUrl = orderPayService.confirmationPay(userId, orderNo, vo.getFrontUrl());
		}
		// 扫码支付返回二维码字符串
		if ("SCAN_WEIXIN".equals(vo.getPayMethod()) || "SCAN_ALIPAY".equals(vo.getPayMethod())) {
			payUrl = result.get("payInfo");
		}
		// 网关确认支付url
		if ("GATEWAY".equals(vo.getPayMethod())) {
			payUrl = soaAccountService.confirmPay(userId, orderNo);
		}
		res.put("payUrl", payUrl);
		res.put("flowNo", orderNo);
		return res;
	}
	
	/**
	 * 保证金订单确认
	 * @param 
	 * @return String
	 * @throws
	 */
	public void signalPayNotify(String flowNo,String payMethod,BigDecimal amt){
		//生成业务流水编号
		String orderNo = SequenceUUID.getOrderIdByUUId("B");
		// 提前添加待确认流水
		UserAccountFlow flow = new UserAccountFlow();
		flow.setAmt(amt);
		flow.setContent("保证金收入");
		flow.setOrderNo(orderNo);
		flow.setType(0);
		flow.setUserId(PayClientUtil.syhNo);
		flow.setBusinessType("交易");
		flow.setFlowType("收入");
		flow.setStatus("等待确认");
		flow.setPayMethod(payMethod);
		userAccountFlowDao.save(flow);
		try {
			soaDepositService.signalAgentPay(orderNo, flowNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 扣除保证金
	 * @param 
	 * @return void
	 * @throws
	 */
	@Transactional
	public void deductCashDeposit(Map<String, Object> param) {
		String bizTransferNo = SequenceUUID.getOrderIdByUUId("B");
		String shopId = (String) param.get("shopId");
		String type = param.get("type").toString();
		Bond bond = bondDao.findByShopId(shopId);
		BigDecimal amt = new BigDecimal(param.get("amt").toString());
		//余额资金小于扣减金额抛异常
		if(bond.getShopBalance().compareTo(amt)==-1){
			throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.AUCTION2_ERROR.getDesc());
		}
		//保证金
		User user = null;
		//扣减
		if("0".equals(type)){
			user = userDao.findById(PayClientUtil.syhNo);
		}
		//退还
		if("1".equals(type)){
			Shop shop = shopDao.findById(shopId);
			user = userDao.findById(shop.getUserId());
		}
		if(user==null){
			throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.USER_NOTEXIST.getDesc());
		}
		String targetBizUserId = user.getId();
		Long amount = Long.valueOf(AmountUtils.changeY2F(param.get("amt").toString()));
		//转账开始
		try {
			soaTransferAccounts.marketTransferAccounts(bizTransferNo, "100002", targetBizUserId, PayClientUtil.accountSetNo, amount);
		} catch (RestException e) {
			throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.DATE_ERROR.getDesc());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//修改用户账户的保证金余额
		bond.setShopBalance(bond.getShopBalance().subtract(amt));
		//如果余额低于保证金要求，就进行状态变更
		if(bond.getShopBalance().compareTo(bond.getBondAmt())==-1){
			bond.setBondStatus(1);
		}
		//保证金支出流水添加
        UserAccountFlow AccountFlow = new UserAccountFlow();
        AccountFlow.setAmt(new BigDecimal(param.get("amt").toString()));
        AccountFlow.setBusinessType(null);
        if("0".equals(type)){
            AccountFlow.setContent("保证金退还");
        }else{
            AccountFlow.setContent("保证金扣减(保证金账户转至收入账户)");
        }
        AccountFlow.setCreateTime(DateUtil.getCurrentDate());
        AccountFlow.setFlowType("支出");
        AccountFlow.setOrderNo(bizTransferNo);
        AccountFlow.setStatus("success");
        AccountFlow.setUserId(PayClientUtil.syhNo);
        AccountFlow.setType(1);
        AccountFlow.setUpdateTime(DateUtil.getCurrentDate());
        userAccountFlowDao.save(AccountFlow);
		//保证金收入流水添加
		UserAccountFlow userAccountFlow = new UserAccountFlow();
		userAccountFlow.setAmt(new BigDecimal(param.get("amt").toString()));
		userAccountFlow.setBusinessType(null);
		if("0".equals(type)){
            userAccountFlow.setContent("保证金退还");
        }else{
            userAccountFlow.setContent("保证金扣减(保证金账户转至收入账户)");
        }
		userAccountFlow.setCreateTime(DateUtil.getCurrentDate());
		userAccountFlow.setFlowType("收入");
		userAccountFlow.setOrderNo(bizTransferNo);
		userAccountFlow.setStatus("success");
		if("0".equals(type)){
            userAccountFlow.setUserId(targetBizUserId);
        }else{
            userAccountFlow.setUserId(PayClientUtil.syhNo);
        }
		userAccountFlow.setType(0);
		userAccountFlow.setUpdateTime(DateUtil.getCurrentDate());
		userAccountFlowDao.save(userAccountFlow);
		bondDao.save(bond);
	}
}
