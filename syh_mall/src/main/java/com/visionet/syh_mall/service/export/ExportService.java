package com.visionet.syh_mall.service.export;

import java.io.IOException;
import java.util.List;

import com.visionet.syh_mall.entity.channel.CommissionFlow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.ExportExcelUtil;
import com.visionet.syh_mall.vo.ChannelCollctVo;
import com.visionet.syh_mall.vo.ExportOrderVo;
import com.visionet.syh_mall.vo.finance.FundSummary;
import com.visionet.syh_mall.vo.finance.IncomeVo;
import com.visionet.syh_mall.vo.user.BondVo;
import com.visionet.syh_mall.vo.user.UserAccountVo;

@Service
public class ExportService {
	
	//导出财务流水表
	public HSSFWorkbook exportFinanceExcel(List<ExportFinanceVo> list) throws Exception {
		String[] secondTitles = {"用户登录名","用户姓名","流水编号","业务类型","流水类型","收入","支出","详细描述","流水创建时间","备注","支付方式"};
		HSSFWorkbook workbook1 = ExportExcelUtil.makeSecondHead(secondTitles);
		String[] beanPropertys = {"userLoginName","userName","flowNo","businessType","flowType","income","expend","described","flowCreateTime","remark","payMethod"};
		HSSFWorkbook workbook = ExportExcelUtil.exportExcelData(workbook1, list, beanPropertys);
		return workbook;
	}

	public HSSFWorkbook exportOrderExcel(List<ExportOrderVo> list) throws Exception {
		String[] secondTitles = {"订单编号","创建时间","订单总金额","商品金额","邮费","买家","卖家","店铺","订单状态","物流"};
		HSSFWorkbook workbook1 = ExportExcelUtil.makeSecondHead(secondTitles);
		String[] beanPropertys = {"orderNo","createTime","orderAmt","goodsAmt","postage","buyerAliasName","sellerAliasName","buyerShopName","orderStatus","expressBillNo"};
		HSSFWorkbook workbook = ExportExcelUtil.exportExcelData(workbook1, list, beanPropertys);
		return workbook;
	}

	public HSSFWorkbook exportWithdrawExcel(List<ExportWithdrawVo> list) {
		String[] secondTitles = {"会员账号","提现金额","收款账户","账户姓名","状态","申请时间"};
		HSSFWorkbook workbook1 = ExportExcelUtil.makeSecondHead(secondTitles);
		String[] beanPropertys = {"userLoginName","withdrawAmt","cardNo","cardUserName","status","applyTime"};
		HSSFWorkbook workbook = ExportExcelUtil.exportExcelData(workbook1, list, beanPropertys);
		return workbook;
	}

	public HSSFWorkbook exportEvaluationExcel(List<ExportEvaluationRestVo> list) {
		String[] secondTitles = {"藏品编码","藏品名称","评级分数","志号","面值","发行年份","藏品分类","发行单位","材质","水印","版别/工艺","二维码"};
		HSSFWorkbook workbook1 = ExportExcelUtil.makeEvaluationSecondHead(secondTitles);
		String[] beanPropertys = {"collectCode","collectName","ratingScore","serialNumber","faceValue","releaseYear","collectType","releaseUnit","texture","Watermark","printing","QRCode"};
		HSSFWorkbook workbook = null;
		try {
			workbook = ExportExcelUtil.exportEvaluationExcelData(workbook1, list, beanPropertys);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	public HSSFWorkbook exportChannelExcel(List<CommissionFlow> list) {
		String[] secondTitles = {"序号","订单流水号","成交金额","买家用户账户","买家用户姓名","买家父级账户","买家父级用户名称",
				"买家父级用户结算比例","买家父级用户结算金额","买家祖父级账户","买家祖父级用户名称","买家祖父级用户结算比例","买家祖父级用户结算金额",
				"卖家用户账户","卖家用户姓名","卖家父级账户","卖家父级用户名称","卖家父级用户结算比例","卖家父级用户结算金额","卖家祖父级账户",
				"卖家祖父级用户名称","卖家祖父级用户结算比例","卖家祖父级用户结算金额","买家分佣总金额","卖家分佣总金额","交易时间"};
		HSSFWorkbook workbook1 = ExportExcelUtil.makeSecondHead(secondTitles);
		String[] beanPropertys = {"id","buyOrderNo","buyTransactionAmount","buyUserAccount","buyUserName","buyFatherUserAccount",
				"buyFatherUserName","buyFatherUserCommissionRate","buyFatherUserCommissionFee","buyGrandFatherUserAccount","buyGrandFatherUserName",
				"buyGrandFatherUserCommissionRate","buyGrandFatherUserCommissionFee","sellUserAccount","sellUserName",
				"sellFatherUserAccount","sellFatherUserName","sellFatherUserCommissionRate","sellFatherUserCommissionFee","sellGrandFatherUserAccount",
				"sellGrandFatherUserName","sellGrandFatherUserCommissionRate","sellGrandFatherUserCommissionFee","buyAllCommissionFee","sellAllCommissionFee","createTime"};
		HSSFWorkbook workbook = ExportExcelUtil.exportExcelData(workbook1, list, beanPropertys);
		return workbook;
	}

	public HSSFWorkbook exportAccountExcel(List<UserAccountVo> searchUserAccount) {
		String[] secondTitles = {"会员账号","会员昵称","账户余额","可使用余额","冻结金额"};
		HSSFWorkbook workbook1 = ExportExcelUtil.makeSecondHead(secondTitles);
		String[] beanPropertys = {"loginName","userName","balance","withdrawal","frozenAmt"};
		HSSFWorkbook workbook = ExportExcelUtil.exportExcelData(workbook1, searchUserAccount, beanPropertys);
		return workbook;
	}

	public HSSFWorkbook exportCashDepositExcel(List<BondVo> list) {
		String[] secondTitles = {"店铺名","缴纳状态","缴纳金额","余额","应缴金额","最近缴纳时间"};
		HSSFWorkbook workbook1 = ExportExcelUtil.makeSecondHead(secondTitles);
		String[] beanPropertys = {"shopName","status","bondAmt","shopBalance","amt","bondTime"};
		HSSFWorkbook workbook = ExportExcelUtil.exportExcelData(workbook1, list, beanPropertys);
		return workbook;
	}

	public HSSFWorkbook exportIncomeExcel(List<IncomeVo> list) {
		String[] secondTitles = {"会员账号","会员姓名","交易日期","订单流水号","收入金额","收入类型","备注"};
		HSSFWorkbook workbook1 = ExportExcelUtil.makeSecondHead(secondTitles);
		String[] beanPropertys = {"userLoginName","userName","createTime","orderNo","incomeAmt","incomeType","remark"};
		HSSFWorkbook workbook = ExportExcelUtil.exportExcelData(workbook1, list, beanPropertys);
		return workbook;
	}
	
	public HSSFWorkbook exportChannelCollectExcel(List<ChannelCollctVo> list) {
		String[] secondTitles = {"日期","会员账号","会员姓名","佣金比例","佣金金额","层级"};
		HSSFWorkbook workbook1 = ExportExcelUtil.makeSecondHead(secondTitles);
		String[] beanPropertys = {"time","loginName","aliasName","commissionRate","amount","hierarchy"};
		HSSFWorkbook workbook = ExportExcelUtil.exportExcelData(workbook1, list, beanPropertys);
		return workbook;
	}

	public HSSFWorkbook exportFundSummaryExcel(List<FundSummary> list) {
		String[] secondTitles = {"登录名","用户名","用户类型","开户时间","初始金额","期末金额","冻结金额","可用金额","入金","提现金额"
				,"出售收入","出售支出","竞拍收入","竞拍支出","求购收入","求购支出","违约金收入","违约金支出","营销收入","营销支出"
				,"邮费收入","保险费收入","保险费支出","服务费收入","服务费支出","佣金收入","佣金支出"};
		HSSFWorkbook workbook1 = ExportExcelUtil.makeSecondHead(secondTitles);
		String[] beanPropertys = {"userLoginName","userName","userType","accountCreateTime","firstAmt","lastAmt","frozenAmt"
				,"withdrawal","incomeAmt","withdrawAmt","sellIncome","sellExpend","bidIncome","bidExpend","buyIncome"
				,"buyExpend","penalSumIncome","penalSumExpend","marketIncome","marketExpend","freightIncome","premiumIncomeAmt"
				,"premiumExpendAmt","serviceIncomeAmt","serviceExpendAmt","commissionIncomeAmt","commissionExpendAmt"};
		HSSFWorkbook workbook = ExportExcelUtil.exportExcelData(workbook1, list, beanPropertys);
		return workbook;
	}
}
