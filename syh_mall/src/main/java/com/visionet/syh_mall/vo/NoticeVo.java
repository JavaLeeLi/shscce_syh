package com.visionet.syh_mall.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.visionet.syh_mall.entity.Notice;

/**
 * @ClassName: NoticeVo
 * @Description: 公告的Vo
 * @author chenghongzhan
 * @date 2017年10月18日 下午7:43:10
 *
 */
public class NoticeVo {

	private String noticeID;
	@NotNull(message = "公告类型不能为空")
	private Integer noticeType;
	@NotBlank(message = "公告标题不能为空")
	private String noticeTitle;
	@NotBlank(message = "公告内容不能为空")
	private String noticeContent;

	public String getNoticeID() {
		return noticeID;
	}

	public void setNoticeID(String noticeID) {
		this.noticeID = noticeID;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	/**
	 * @Title: convertPo @Description: 公告的Vo转Po @param @param
	 *         noticeVo @param @param notice @param @return 设定文件 @return Notice
	 *         返回类型 @throws
	 */
	public Notice convertPo(NoticeVo noticeVo, Notice notice) {
		notice.setId(noticeVo.getNoticeID());
		notice.setNoticeTitle(noticeVo.getNoticeTitle());
		notice.setNoticeType(noticeVo.getNoticeType());
		notice.setNoticeContent(noticeVo.getNoticeContent());
		return notice;
	}

	@Override
	public String toString() {
		return "NoticeVo [noticeID=" + noticeID + ", noticeType=" + noticeType + ", noticeTitle=" + noticeTitle
				+ ", noticeContent=" + noticeContent + "]";
	}

}
