package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.message.Message;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 *@Author DM
 *@version ：2017年8月23日下午3:10:12
 */
public interface MessageRepository extends BaseRepository<Message, String> {
	@Query(value="SELECT t2.id,t2.senderId,t2.senderName,f.file_path senderImgUrl,t2.content,t2.lastMsgTime,t2.receiver_id from (SELECT * from (select u.img_file_id as imgid,m.id,m.sender_id as senderId,u.alias_name as senderName,m.content,m.create_time as lastMsgTime,m.receiver_id from tbl_user_message m,tbl_user u where (u.id=m.sender_id and m.sender_id=?1) OR (u.id=m.sender_id AND m.receiver_id=?1) AND m.is_deleted=0 ORDER BY m.create_time DESC) t GROUP BY t.receiver_id,t.senderId)t2 LEFT JOIN tbl_file_manage f ON f.id=t2.imgid ORDER BY t2.lastMsgTime DESC",nativeQuery=true)
	public List<Object[]> findByReceiverId(String receiverId);
	
	@Query(value="select m.id as msgId,m.sender_id as senderId,m.receiver_id as receiverId,m.title as msgTitle,m.content as msgContent,m.create_time as msgTime from tbl_user_message m where (m.receiver_id=?1 and m.sender_id=?2) or (m.receiver_id=?2 and m.sender_id=?1)",nativeQuery=true)
	public List<Object[]> findInfoById(String receiverId,String senderId);
	
	@Query(value="select count(*) as msgsCount from tbl_user_message m where (m.receiver_id=?1 and m.sender_id=?2) or (m.receiver_id=?2 and m.sender_id=?1)",nativeQuery=true)
	public int findMsgsCount(String receiverId,String senderId);
	@Query(value="select count(*) as msgsCount from tbl_user_message m where (m.receiver_id=?1 and m.sender_id=?2) and m.status=0",nativeQuery=true)
	public int isSelfmsgCount(String receiverId,String senderId);
	
	@Query(value="select u.alias_name AS senderName FROM tbl_user_message m LEFT JOIN tbl_user u ON u.id = m.sender_id LEFT JOIN tbl_file_manage f ON f.id = u.img_file_id WHERE m.receiver_id =?1 and m.sender_id=?2 GROUP BY m.sender_id",nativeQuery=true)
	public String findNameByReceiverId(String receiverId,String senderId);
	
	@Query(value="select f.file_path AS senderImgUrl FROM tbl_user_message m LEFT JOIN tbl_user u ON u.id = m.sender_id LEFT JOIN tbl_file_manage f ON f.id = u.img_file_id WHERE m.receiver_id =?1 and m.sender_id=?2 GROUP BY m.sender_id",nativeQuery=true)
	public String findUrlByReceiverId(String receiverId,String senderId);
	@Query(value="select f.file_path AS senderImgUrl FROM tbl_user_message m LEFT JOIN tbl_user u ON u.id = m.receiver_id LEFT JOIN tbl_file_manage f ON f.id = u.img_file_id WHERE m.receiver_id =?1 and m.sender_id=?2 GROUP BY m.sender_id",nativeQuery=true)
	public String findUrlBysenderId(String receiverId,String senderId);
	@Query(value="FROM Message m WHERE m.receiverId=?1 AND m.senderId=?2 AND m.status=0")
	List<Message> findAllIsSelf(String receiverId,String senderId);

	@Query(value="FROM Message m WHERE m.senderId=?1 AND m.receiverId=?2 AND m.isDeleted=0")
	List<Message> findAll(String currentUserId, String senderID);

	@Query(value="SELECT COUNT(1) FROM tbl_user_message u WHERE u.receiver_id=?1 AND u.status=0 AND u.is_deleted=0",nativeQuery=true)
	public Integer findNewsNum(String currentUserId);

}
