package com.visionet.syh_mall.vo.goods;

import java.util.List;

import com.visionet.syh_mall.vo.DrawVo;

public class DrawCashVo {
	
	private List<DrawVo> drawCashs;

	public List<DrawVo> getDrawCashs() {
		return drawCashs;
	}

	public void setDrawCashs(List<DrawVo> drawCashs) {
		this.drawCashs = drawCashs;
	}

	@Override
	public String toString() {
		return "DrawCashVo [drawCashs=" + drawCashs + "]";
	}
}
