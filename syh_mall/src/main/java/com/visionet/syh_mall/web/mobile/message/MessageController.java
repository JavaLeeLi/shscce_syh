package com.visionet.syh_mall.web.mobile.message;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.message.Message;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mobile.MessageRepository;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
import com.visionet.syh_mall.service.mobile.MessageService;
import com.visionet.syh_mall.vo.MsgVo;
import com.visionet.syh_mall.vo.message.MessageQo;
import com.visionet.syh_mall.vo.message.MessageVo;
import com.visionet.syh_mall.vo.message.sendMessageVo;
import com.visionet.syh_mall.web.BaseController;

/**
 *@Author DM
 *@version ：2017年8月23日下午2:47:33
 *消息业务控制层
 */
@RestController
@RequestMapping("/api/message")
public class MessageController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageRepository messageDao;
	@Autowired
	private UserRepository userDao;
	/**
	 * 获取消息列表
	 * @param roleID
	 * @return
	 * @throws ParseException 
	 */
	@RequiresAuthentication
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public BaseReturnVo<Object> queryRoleList(@RequestBody String receiverId) throws Exception{
		logger.info("获取消息列表");
		List<MessageVo> list = messageService.getMessageList(this.getCurrentUserId());
		return BaseReturnVo.success(list);
	}
	@SuppressWarnings("static-access")
	@RequiresAuthentication
	@RequestMapping(value = "/getMsgDetail",method = RequestMethod.POST)
	public BaseReturnVo<Object> getMsgDetail(@RequestBody MessageQo qo) throws Exception{
		logger.info("获取消息详情:{}",qo);
		qo.setReceiverId(this.getCurrentUserId());
		//修改未读消息状态
		messageService.editMessage(qo);
		Page<MessageVo> page = messageService.getMsgDetail(qo);
		MsgVo result = new MsgVo();
		int msgsCount = messageDao.findMsgsCount(qo.getReceiverId(),qo.getSenderId());
		result.setMsgsCount(msgsCount);
//		String senderName = messageDao.findNameByReceiverId(qo.getReceiverId(),qo.getSenderId());
//		result.setSenderName(senderName);
		User user = userDao.findOne(qo.getSenderId());
		result.setSenderName(user.getAliasName());
		String senderImgUrl = messageDao.findUrlByReceiverId(qo.getReceiverId(),qo.getSenderId());
		result.setSenderImgUrl(FilePathUtils.fileUrl(senderImgUrl));
		String receiverImgUrl= messageDao.findUrlBysenderId(qo.getReceiverId(),qo.getSenderId());
		result.setReceiverImgUrl(FilePathUtils.fileUrl(receiverImgUrl));
		result.setCurPageIndex(qo.getPageIndex());
		result.setHasNext(page.hasNext());
		result.setItemCount(page.getTotalElements());
		result.setPageCount(page.getTotalPages());
		result.setMsgs(page.getContent());
		List<MessageVo> content = page.getContent();
		if(content.size()>0){
			String currentUserId = getCurrentUserId();
			String senderId = content.get(0).getSenderId();
			String receiverId = content.get(0).getReceiverId();
			result.setChatUserId(currentUserId.equals(senderId)?receiverId:senderId);
		}
		return BaseReturnVo.success(result);
	}
	/**
	 * 发送消息
	 * @param Map
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@SuppressWarnings("static-access")
	@RequiresAuthentication
	@RequestMapping(value="/sendMsg",method=RequestMethod.POST)
	public BaseReturnVo<Object> sendMsg(@RequestBody @Valid sendMessageVo messageVo,BindingResult result){
		logger.info("发送消息：{}",messageVo);
		Message message = new Message();
		message.setSenderId(this.getCurrentUserId());
		message.setContent(messageVo.getMsgContent());
		message.setReceiverId(messageVo.getReceiverID());
		try {
			messageService.sendMsg(message);
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
	/**
	 * 删除信息
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@SuppressWarnings("static-access")
	//@RequestMapping(value="/delMsg",method=RequestMethod.POST)
	public BaseReturnVo<Object> delMsg(@RequestBody Map<String,Object> map){
		logger.info("删除信息:{}", map);
		if(StringUtils.isEmpty(map.get("senderID"))){
			throw new RestException(BusinessStatus.NULL_LIMIT.getCode(), BusinessStatus.NULL_LIMIT.getDesc());
		}
		try {
			messageService.delMsg(this.getCurrentUserId(),(String) map.get("senderID"));
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
}
