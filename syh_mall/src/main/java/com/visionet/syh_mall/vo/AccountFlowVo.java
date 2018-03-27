package com.visionet.syh_mall.vo;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.visionet.syh_mall.entity.UserAccountFlow;

/**
 * 账户流水返回vo
 * @author xiaofb
 * @time 2017年9月20日
 */
public class AccountFlowVo {
	private String journalID;//流水id
	private BigDecimal journalAmt;//流水金额
	private String journalType;//流水类型
	private String journalDesc;//流水描述
	private Date journalTime;//流水生成时间
	private  String content;//流水描述
	public String getJournalID() {
		return journalID;
	}

	public void setJournalType(String journalType) {
		this.journalType = journalType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setJournalID(String journalID) {
		this.journalID = journalID;
	}
	
	public BigDecimal getJournalAmt() {
		return journalAmt;
	}
	public void setJournalAmt(BigDecimal journalAmt) {
		this.journalAmt = journalAmt;
	}
	public void setJournalTime(Date journalTime) {
		this.journalTime = journalTime;
	}
	public String getJournalType() {
		return journalType;
	}
	public void setJournalType(int journalType) {
		if(journalType == 0){
			this.journalType = "收入";
		}
		if(journalType == 1){
			this.journalType = "支出";
		}
	}
	public String getJournalDesc() {
		return journalDesc;
	}
	public void setJournalDesc(String journalDesc) {
		this.journalDesc = journalDesc;
	}
	
	public Date getJournalTime() {
		return journalTime;
	}
	/**
	 * 实体转vo
	 * @param
	 * @return
	 */
	public static List<AccountFlowVo> convert(List<UserAccountFlow> userAccountFlowList){
		List<AccountFlowVo> list = new ArrayList<AccountFlowVo>();
		for (UserAccountFlow userAccountFlow : userAccountFlowList) {
			AccountFlowVo vo = new AccountFlowVo();
			vo.setJournalID(userAccountFlow.getOrderNo());
			vo.setJournalType(userAccountFlow.getType());
			vo.setJournalAmt(userAccountFlow.getAmt());
			vo.setJournalDesc(userAccountFlow.getContent());
			vo.setJournalTime(userAccountFlow.getCreateTime());
			vo.setContent(userAccountFlow.getContent());
			list.add(vo);
		}
		return list;
	}
	
}
