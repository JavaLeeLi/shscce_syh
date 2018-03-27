package com.visionet.syh_mall.vo.order;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.visionet.syh_mall.vo.OrderQo;
/**
 * 订单结算入参集合
 * @author mulongfei
 * @date 2017年9月22日下午3:54:53
 */
public class OrderVo {
	@Valid
	@NotEmpty(message="订单信息不能为空")
	private List<OrderQo> orderInfos;//订单集合
	private Integer source;//订单来源（0:直接购买,1:购物车购买）

	public List<OrderQo> getOrderInfos() {
		return orderInfos;
	}

	public void setOrderInfos(List<OrderQo> orderInfos) {
		this.orderInfos = orderInfos;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "OrderVo [orderInfos=" + orderInfos + ", source=" + source + "]";
	}


}