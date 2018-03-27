package com.visionet.syh_mall.service.scheduler.job;

import java.math.BigDecimal;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.SysConstants;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MathUtils;
import com.visionet.syh_mall.entity.goods.AuctionJob;
import com.visionet.syh_mall.entity.goods.BidRecord;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.HomeGoods;
import com.visionet.syh_mall.entity.order.OrderGoods;
import com.visionet.syh_mall.repository.goods.AuctionJobRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.repository.mobile.HomeGoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderGoodsRepository;
import com.visionet.syh_mall.service.account.FreezeRecordService;
import com.visionet.syh_mall.service.mobile.GoodsAuctionService;
import com.visionet.syh_mall.service.order.OrderPayService;
import com.visionet.syh_mall.web.syhservice.SyhserviceController;

/**
 * 商品拍卖结束任务 job
 * 
 * @author xiaofb
 * @time 2017年10月26日
 */
public class GoodsAuctionEndJob implements Job {
	private static final Logger logger = LoggerFactory.getLogger(SyhserviceController.class);

	@Autowired
	private AuctionJobRepository auctionJobRepo;
	@Autowired
	private GoodsAuctionService goodsAuctionService;
	@Autowired
	private FreezeRecordService fRecordService;
	@Autowired
	private OrderGoodsRepository orderGoodsRepo;
	@Autowired
	private HomeGoodsRepository homeGoodsDao;
	@Autowired
	private GoodsRepository goodsDao;

	@Override
	public void execute(JobExecutionContext context) {
		String goodsId = context.getJobDetail().getJobDataMap().getString("goodsId");
		AuctionJob auctionJob = auctionJobRepo.findByJobName("job_goods_auction_" + goodsId);
		logger.info("商品拍卖结束【{}】", goodsId);
		try {
			// 商品竞价最高记录
			BidRecord bidRecord = goodsAuctionService.findWinner(goodsId);
			if (null == bidRecord) {
				logger.info("商品竞价记录不存在【{}】", goodsId);
				auctionJob.setJobStatus(1);
				auctionJobRepo.save(auctionJob);
				HomeGoods homeGoods = homeGoodsDao.findByGoodsId(goodsId);
				if (!StringUtils.isEmpty(homeGoods)) {
					homeGoods.setUpdateTime(DateUtil.getCurrentDate());
					homeGoods.setIsDeleted(SysConstants.DELETE_FLAG_YES);
					homeGoodsDao.save(homeGoods);
				}
				Goods goods = goodsDao.findById(goodsId);
				if(!StringUtils.isEmpty(goods)){
					goods.setStatusCode("goods_undercarriage");
					goodsDao.save(goods);
				}
				return;
			}
			String userId = bidRecord.getUserId();
			logger.info("资金解冻userId：{}", bidRecord.getUserId());
			// 资金解冻
			fRecordService.unfreezeAmt(bidRecord.getId(), userId);
			// 生成订单
			Map<String, Object> orderMap = goodsAuctionService.getBidOrder(goodsId);
			String orderId = String.valueOf(orderMap.get("orderID")); // 订单id
			String orderNo = String.valueOf(orderMap.get("orderSn")); // 订单编号
			String orderAmt = String.valueOf(orderMap.get("orderAmt")); // 订单金额
			// 余额支付
//			Map<String,String> payResult = orderPayService.orderPay(userId, "BALANCE", orderAmt, orderNo, null, null);
//			logger.info("商品拍卖支付响应 payResult：{}", payResult);
//			 修改拍卖任务为结束
			auctionJob.setJobStatus(1);
			auctionJobRepo.save(auctionJob);
			// 订单商品添加实际成交价格
			OrderGoods orderGoods = orderGoodsRepo.findByOrderId(orderId).get(0);
			BigDecimal realPrice = MathUtils.div(bidRecord.getLastBidPrice(), new BigDecimal(orderGoods.getGoodsNum()));
			orderGoods.setGoodsRealPrice(realPrice);
			orderGoods.setUpdateTime(DateUtil.getCurrentDate());
			orderGoodsRepo.save(orderGoods);
			HomeGoods homeGoods = homeGoodsDao.findByGoodsId(goodsId);
			if (!StringUtils.isEmpty(homeGoods)) {
				homeGoods.setUpdateTime(DateUtil.getCurrentDate());
				homeGoods.setIsDeleted(SysConstants.DELETE_FLAG_YES);
				homeGoodsDao.save(homeGoods);
			}
			Goods goods = goodsDao.findById(goodsId);
			if(!StringUtils.isEmpty(goods)){
				goods.setStatusCode("goods_undercarriage");
				goodsDao.save(goods);
			}
		} catch (Exception e) {
			logger.error("商品拍卖结束付款异常", e);
			e.printStackTrace();
		}
	}
}
