package com.visionet.syh_mall.service.mobile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.Validator;
import com.visionet.syh_mall.entity.FileManage;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.message.Message;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mobile.MessageDaolmpl;
import com.visionet.syh_mall.repository.mobile.MessageRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
import com.visionet.syh_mall.vo.message.MessageQo;
import com.visionet.syh_mall.vo.message.MessageVo;

/**
 *@Author DM
 *@version ：2017年8月23日下午3:12:31
 *消息service
 */
@Service
public class MessageService extends BaseService {
	@Autowired
	private MessageRepository messageDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private FileManageRepostory fileManageDao;
	@Autowired  
	private MessageDaolmpl messageDaolmpl;
	/**
	 * 消息列表
	 * @return
	 * @throws ParseException 
	 */
	public List<MessageVo> getMessageList(String receiverId) throws Exception{
		SimpleDateFormat df = new SimpleDateFormat(DateUtil.YMD_FULL);//定义格式
		//receiverId="40288099213d76e2015e2d772b020000";//测试数据
		List<Object[]> obj= messageDao.findByReceiverId(receiverId);
		if(StringUtils.isEmpty(obj)){
			return null;
		}
		List<MessageVo> returnVo=new ArrayList<MessageVo>(); 
		for (Object[] object : obj) {
			MessageVo vo = new MessageVo();
			vo.setId(object[0].toString());
			if(receiverId.equals(object[1].toString())){
				vo.setSenderId(object[6].toString());
				vo.setReceiverId(object[1].toString());
				User user = userDao.findOne(object[6].toString());
				vo.setSenderName(user.getAliasName());
				if (Validator.isNotNull(user.getImgFileId())) {
				FileManage manage = fileManageDao.findOne(user.getImgFileId());
				vo.setSenderImgUrl(FilePathUtils.fileUrl(manage.getFilePath()));
				}
			}else{
				vo.setSenderId(object[1].toString());
				vo.setReceiverId(object[6].toString());
				vo.setSenderName(object[2].toString());
				vo.setSenderImgUrl(FilePathUtils.fileUrl(String.valueOf(object[3])));
			}
			vo.setMsgContent(object[4].toString());
			vo.setMsgTime(df.parse(object[5].toString()));
			int isSelfmsgCount = messageDao.isSelfmsgCount(receiverId,vo.getSenderId());
			vo.setIsSelfmsgCount(isSelfmsgCount);
			returnVo.add(vo);		
	     }
		List<MessageVo> removeVo=new ArrayList<MessageVo>();
		for (MessageVo messageVo : returnVo) {
			for (MessageVo message : returnVo) {
				if(messageVo.getSenderId().equals(message.getSenderId())&&messageVo.getReceiverId().equals(message.getReceiverId())&&messageVo.getId()!=message.getId()){
					if(messageVo.getMsgTime().getTime()>message.getMsgTime().getTime()){
						removeVo.add(message);
					}else{
						removeVo.add(messageVo);
					}
				}
			}
		}
		returnVo.removeAll(removeVo);
		return returnVo;
	}
	
	/**
	 * 消息详情
	 * @param 
	 * @return MessageVo
	 * @throws
	 */
	public Page<MessageVo> getMsgDetail(MessageQo qo) throws Exception{
		PageRequest pr = getPageRequest(qo.getPageIndex(), qo.getItemCount(), qo.getOrderConditions());
		return messageDaolmpl.queryCondition(qo, pr);
	}
	/**
	 *修改消息状态
	 * @param 
	 * @return void
	 * @throws
	 */
	public void editMessage(MessageQo qo){
		List<Message> list = messageDao.findAllIsSelf(qo.getReceiverId(),qo.getSenderId());
		for (int i=0;i<list.size();i++) {
			list.get(i).setStatus(1);
			messageDao.save(list.get(i));
		}
	}
	/**
	 * 发送消息
	 * @param 
	 * @return void
	 * @throws
	 */
	public void sendMsg(Message message){
		List<Message> list = messageDao.findAll(message.getReceiverId(),message.getSenderId());
		for (int i=0;i<list.size();i++) {
			list.get(i).setStatus(1);
			messageDao.save(list.get(i));
		}
		message.setStatus(0);
		messageDao.save(message);
	}
	/**
	 * 删除消息
	 * @param 
	 * @return void
	 * @throws
	 */
	public void delMsg(String currentUserId, String senderID) {
		List<Message> messages = messageDao.findAll(currentUserId,senderID);
		if(StringUtils.isEmpty(messages)){
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		for (Message message : messages) {
			message.setIsDeleted(1);
			messageDao.save(message);
		}
	}
}
