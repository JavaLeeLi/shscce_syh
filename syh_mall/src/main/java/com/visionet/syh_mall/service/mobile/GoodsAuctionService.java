package com.visionet.syh_mall.service.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MathUtils;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.common.utils.SequenceUUID;
import com.visionet.syh_mall.common.utils.Validator;
import com.visionet.syh_mall.entity.FileManage;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.account.UserAccount;
import com.visionet.syh_mall.entity.goods.BidRecord;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.mbr.UserAddress;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mbr.UserAddressRepository;
import com.visionet.syh_mall.repository.mobile.GoodsAuctionRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.account.FreezeRecordService;
import com.visionet.syh_mall.service.account.UserAccountService;
import com.visionet.syh_mall.service.order.OrderService;
import com.visionet.syh_mall.service.thridAccount.SoaFundService;
import com.visionet.syh_mall.vo.GoodQo;
import com.visionet.syh_mall.vo.OrderQo;
import com.visionet.syh_mall.vo.order.OrderVo;

/**
 * 商品竞拍模块service层
 * 
 * @author mulongfei
 * @date 2017年8月31日下午2:34:02
 */
@Service
public class GoodsAuctionService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(GoodsAuctionService.class);
	@Autowired
	private GoodsAuctionRepository goodsAuctionDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private FileManageRepostory fileManageDao;
	@Autowired
	private GoodsRepository goodsDao;
	@Autowired
	private KeyMappingRepository kayMappingDao;
	@Autowired
	private SoaFundService soaFundService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserAddressRepository userAddressDao;
	@Autowired
	private FreezeRecordService fRecordService;

	/**
	 * 生成竞拍订单 @param @return Map<String,Object> @throws
	 * @throws Exception 
	 */
	public Map<String, Object> getBidOrder(String GoodsId) throws Exception {
		// 获取最高竞拍记录
		BidRecord bidRecord = goodsAuctionDao.findByGoodsId(GoodsId);
		// 商品信息获取
		GoodQo goodsQo = new GoodQo();
		Goods goods = goodsDao.findById(GoodsId);
		goodsQo.setGoodsID(GoodsId);
		goodsQo.setGoodsNum(goods.getStockNum());
		goodsQo.setGoodsPrice(bidRecord.getLastBidPrice().divide(new BigDecimal(goods.getStockNum())));
		ArrayList<GoodQo> goodQos = new ArrayList<GoodQo>();
		goodQos.add(goodsQo);
		// 订单数据组装
		OrderQo orderQo = new OrderQo();
		orderQo.setReceiverName(bidRecord.getReceiverName());
		orderQo.setReceiverPhone(bidRecord.getReceiverPhone());
		orderQo.setReceiverProvince(bidRecord.getReceiverProvince());
		orderQo.setReceiverCity(bidRecord.getReceiverCity());
		orderQo.setReceiverStreet(bidRecord.getReceiverStreet());
		orderQo.setReceiverArea(bidRecord.getReceiverArea());
		orderQo.setReceiverAddress(bidRecord.getReceiverAddress());
		orderQo.setGoodsInfos(goodQos);
		// 整体数据整合
		OrderVo orderVo = new OrderVo();
		List<OrderQo> orderInfos = new ArrayList<OrderQo>();
		orderInfos.add(orderQo);
		orderVo.setOrderInfos(orderInfos);
		// 调用订单生成方法
		Map<String, Object> bidOrder = orderService.getBidOrder(bidRecord.getLastBidPrice(), orderVo,
				bidRecord.getUserId());
		return bidOrder;
	}

	/**
	 * @throws Exception
	 *             拍卖出价 @param String @return void @throws
	 */
	@Transactional
	public void BidAmt(String addressId, String GoodId, BigDecimal BidAmt, String UserId,BigDecimal expressFee) throws Exception {
		logger.info("----------出价用户------:{}", UserId);
		
		BigDecimal bidFee = MathUtils.add(BidAmt,expressFee);
		// 账户余额验证
		UserAccount userAccount = userAccountService.getUserAccountInfo(UserId);
		// 可用金额（余额-冻结金额）
		BigDecimal useAmt = MathUtils.sub(userAccount.getBalance(), userAccount.getFrozenAmt());
		if (useAmt.compareTo(bidFee) < 0) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.AUCTION2_ERROR.getDesc());
		}
		// 竞价地址
		UserAddress userAddress = userAddressDao.findOne(addressId);
		// 竞价商品
		Goods good = goodsDao.findOne(GoodId);
		if (UserId.equals(good.getOwnerId())) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.AUCTION_ERROR.getDesc());
		}
		// 当前竞价人信息
		BidRecord bidRecord = new BidRecord();
		bidRecord.setReceiverName(userAddress.getReceiverName());
		bidRecord.setReceiverPhone(userAddress.getPhone());
		bidRecord.setReceiverProvince(userAddress.getProvince());
		bidRecord.setReceiverCity(userAddress.getCity());
		bidRecord.setReceiverArea(userAddress.getArea());
		bidRecord.setReceiverStreet(userAddress.getStreet());
		bidRecord.setReceiverAddress(userAddress.getAddress());
		bidRecord.setGoodsId(GoodId);
		bidRecord.setExpressFee(expressFee);
		bidRecord.setHasRefunded(0);
		bidRecord.setIsBidWinner(0);
		bidRecord.setLastBidPrice(BidAmt);
		bidRecord.setCreateTime(DateUtil.getCurrentDate());

		Date validStartTime = good.getValidStartTime();
		Date validEndTime = good.getValidEndTime();
		Date createTime = bidRecord.getCreateTime();
		logger.debug("拍卖出价时间判断开始");
		Calendar calendar = Calendar.getInstance();
		// 截止时间
		calendar.setTime(validEndTime);
		long validEndMillis = calendar.getTimeInMillis();
		logger.info("----------截止时间------:{}", validEndMillis);
		// 开始时间
		calendar.setTime(validStartTime);
		long validStartMillis = calendar.getTimeInMillis();
		logger.info("----------开始时间------:{}", validStartMillis);
		// 出价创建时间
		calendar.setTime(createTime);
		long bidAmtMillis = calendar.getTimeInMillis();
		logger.info("----------出价创建时间-------:{}", bidAmtMillis);
		if (validStartMillis - bidAmtMillis > 0) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.AUCTION_NOT_START.getDesc());
		}
		if (validEndMillis - bidAmtMillis <= 0) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.AUCTION_OVER.getDesc());
		}
		logger.debug("拍卖出价时间判断结束");
		bidRecord.setUpdateTime(DateUtil.getCurrentDate());
		bidRecord.setUserId(UserId);

		// 商品竞价记录
		BidRecord record = goodsAuctionDao.findByGoodsId(GoodId);
		// 竞价金额比较
		if (null != record) {
			if (record.getLastBidPrice().compareTo(BidAmt) >= 0) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.AUCTION1_ERROR.getDesc());
			}
		}
		// 解冻之前最高价
		this.unfreezeAmt(GoodId);

		// 所有竞价记录
		BidRecord bidRecord2 = goodsAuctionDao.findByGoodsId(GoodId);
		if (bidRecord2 != null) {
			bidRecord2.setIsBidWinner(0);
			goodsAuctionDao.save(bidRecord2);
		}

		bidRecord.setIsBidWinner(1);
		// 保存当前竞价者信息
		goodsAuctionDao.save(bidRecord);
		// 资金冻结
		String bizId = goodsAuctionDao.findByGoodsId(GoodId).getId(); // 业务id
		fRecordService.saveFreezdRecord(UserId, bizId, bidFee);
		logger.info("----------出价结束用户------:{}", UserId);
	}

	/**
	 * 拍卖资金冻结
	 * 
	 * @param userId
	 * @param amount
	 * @throws Exception
	 */
	@Transactional
	public void freezeAmt(String userId, BigDecimal amount) throws Exception {
		// 账户余额验证
		UserAccount userAccount = userAccountService.getUserAccountInfo(userId);
		// 可用金额（余额-冻结金额）
		BigDecimal useAmt = MathUtils.sub(userAccount.getBalance(), userAccount.getFrozenAmt());
		if (useAmt.compareTo(amount) < 0) {
			throw new RestException("账户可用余额不足");
		}
		String bizFreezenNo = SequenceUUID.getOrderIdByUUId("B");
		Long amt = Long.valueOf(AmountUtils.changeY2F(amount.toString())); // 元转分
		// soa资金冻结
		soaFundService.freezeMoney(userId, bizFreezenNo, amt);
		// 账户资金冻结
		BigDecimal withdrawal = MathUtils.add(userAccount.getWithdrawal(),
				MathUtils.sub(userAccount.getBalance(), amount));
		userAccount.setWithdrawal(withdrawal);
		userAccount.setFrozenAmt(MathUtils.add(userAccount.getFrozenAmt(), amount));
		userAccountService.saveUserAccount(userAccount);
	}

	/**
	 * 拍卖竞价解冻金额
	 * 
	 * @param goodsId
	 * @throws Exception
	 */
	public void unfreezeAmt(String goodsId) throws Exception {
		// 未解冻记录
		List<BidRecord> recordList = goodsAuctionDao.findByHasRefundedAndGoodsId(goodsId, 0);
		for (BidRecord bidRecord : recordList) {
			fRecordService.unfreezeAmt(bidRecord.getId(), bidRecord.getUserId());
			bidRecord.setHasRefunded(1); // 修改为已退款
			bidRecord.setUpdateTime(DateUtil.getCurrentDate());
			goodsAuctionDao.save(bidRecord);
		}
	}

	/**
	 * 获取拍卖出价记录 @param String @return List<Map<String,Object>> @throws
	 */
	public List<Map<String, Object>> getBidRecord(String GoodId, PageInfo pageInfo) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> bidRecord2 = DynamicParamConvert.getBidRecord(GoodId);
		Map<String, SearchFilter> parse = SearchFilter.parse(bidRecord2);
		PageRequest pageRequest = buildPageRequest(pageInfo.getPageIndex(), pageInfo.getItemCount(), "lastBidPrice",
				"DESC");
		Page<BidRecord> findAll = goodsAuctionDao
				.findAll(DynamicSpecifications.bySearchFilter(parse.values(), BidRecord.class), pageRequest);
		// List<BidRecord> findAll = goodsAuctionDao.findAll(GoodId);
		for (BidRecord bidRecord : findAll) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bidRecordID", bidRecord.getId());
			map.put("userID", bidRecord.getUserId());
			User payByUser = userDao.findOne(bidRecord.getUserId());
			map.put("userName", "*******" + payByUser.getLoginName().substring(7));
			if (Validator.isNotNull(payByUser.getAliasName())) {
				map.put("userName", payByUser.getAliasName());
			}
			map.put("userTypeCode", payByUser.getUserTypeCode());
			KeyMapping keyMapping = kayMappingDao.findByKeyCode(payByUser.getUserTypeCode());
			if (keyMapping != null) {
				map.put("userTypeDesc", keyMapping.getValueDesc());
			} else {
				map.put("userTypeDesc", null);
			}
			FileManage userPic = fileManageDao.findOne(payByUser.getImgFileId());
			map.put("userImgUrl", userPic.getFilePath());
			map.put("bidPrice", bidRecord.getLastBidPrice());
			map.put("isBidWinner", bidRecord.getIsBidWinner());
			map.put("bidTime", bidRecord.getUpdateTime());
			list.add(map);
		}
		return list;
	}

	/**
	 * 拍卖出价申请退款 @param String @return void @throws
	 */
	public void refundBidAmt(String Id) {
		BidRecord BidAmt = goodsAuctionDao.findOne(Id);
		if (BidAmt == null) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		BidAmt.setHasRefunded(1);
		goodsAuctionDao.save(BidAmt);
	}

	/**
	 * 商品竞价成交记录
	 * 
	 * @param goodsId
	 */
	public BidRecord findWinner(String goodsId) {
		return goodsAuctionDao.findByGoodsId(goodsId);
	}
}
