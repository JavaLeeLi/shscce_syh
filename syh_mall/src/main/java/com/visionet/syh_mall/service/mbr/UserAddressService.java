package com.visionet.syh_mall.service.mbr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.Validator;
import com.visionet.syh_mall.entity.mbr.UserAddress;
import com.visionet.syh_mall.repository.mbr.UserAddressRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.vo.UserAddressQo;

/**
 * @Author DM
 * @version ：2017年8月31日下午2:40:43 实体类
 */
@Service
public class UserAddressService extends BaseService {
	@Autowired
	private UserAddressRepository userAddressRepository;

	/**
	 * 根据用户ID获取收货地址列表
	 * 
	 * @param UserAddress
	 */
	public List<UserAddress> queryList(UserAddressQo qo) throws Exception {
		List<UserAddress> list = userAddressRepository.findAddressById(qo.getUserId());
		return list;
	}

	/**
	 * @author DM
	 * @Description:添加/编辑收货地址
	 */
	public void updateUserAddress(UserAddressQo qo) {
		// 判断是新增操作还是修改操作
		if (Validator.isNull(qo.getAddrId())) {
			UserAddress u = new UserAddress();
			u.setUserId(qo.getUserId());
			u.setReceiverName(qo.getReceiverName());
			u.setPhone(qo.getReceiverPhone());
			u.setProvince(qo.getAddrProvince());
			u.setCity(qo.getAddrCity());
			u.setArea(qo.getAddrArea());
			u.setStreet(qo.getAddrStreet());
			u.setAddress(qo.getAddrDetail());
			if (1 == qo.getIsDefault()) {
				UserAddress ua = userAddressRepository.findAddressByIsDefault(qo.getUserId());
				if (null != ua && null != ua.getId()) {
					ua.setIsDefault(0);
					ua = userAddressRepository.save(ua);
				}
			}
			u.setIsDefault(qo.getIsDefault());
			u.setIsDeleted(0);
			u = userAddressRepository.save(u);
		} else {
			// 修改操作
			UserAddress address = userAddressRepository.findOne(qo.getAddrId());
				address.setUserId(qo.getUserId());
				address.setReceiverName(qo.getReceiverName());
				address.setPhone(qo.getReceiverPhone());
				address.setProvince(qo.getAddrProvince());
				address.setCity(qo.getAddrCity());
				address.setArea(qo.getAddrArea());
				address.setStreet(qo.getAddrStreet());
				address.setAddress(qo.getAddrDetail());
			if (Validator.isNotNull(qo.getIsDefault())) {
				if (1 == qo.getIsDefault()) {
					UserAddress ua = userAddressRepository.findAddressByIsDefault(qo.getUserId());
					if (null != ua && null != ua.getId()) {
						ua.setIsDefault(0);
						ua = userAddressRepository.save(ua);
					}
				}
				address.setIsDefault(qo.getIsDefault());
			}
			address.setUpdateTime(DateUtil.getCurrentDate());
			userAddressRepository.save(address);
		}
	}

	/**
	 * @author DM
	 * @Description:删除收货地址
	 */
	public void deleteMessageApp(UserAddressQo qo) {
		UserAddress address = userAddressRepository.findOne(qo.getAddrId());
		address.setIsDeleted(1);
		address.setUpdateTime(DateUtil.getCurrentDate());
		userAddressRepository.save(address);
	}

	public UserAddress getAddrDetail(UserAddressQo qo) throws Exception {
		UserAddress address = userAddressRepository.findOne(qo.getAddrId());
		return address;
	}
}
