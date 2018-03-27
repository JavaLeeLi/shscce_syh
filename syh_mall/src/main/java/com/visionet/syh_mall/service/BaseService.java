package com.visionet.syh_mall.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.visionet.syh_mall.common.utils.OrderCondition;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.OperateLog;
import com.visionet.syh_mall.exception.RestException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseService {

	/**
	 * 返回分页信息
	 * 
	 * @param pageNumber
	 * @param pagzSize
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public PageRequest getPageRequest(int pageNumber, int pagzSize, List<OrderCondition> conditions) throws Exception {
		if (conditions != null && conditions.size() > 0) {
			List<Order> orders = new ArrayList<Order>();
			Order order = null;
			for (OrderCondition o : conditions) {
				order = new Order(Direction.fromString(o.getDirection()), o.getProperty());
				orders.add(order);
			}
			return buildPageRequest(pageNumber, pagzSize, orders);
		} else {
			// PageInfo pi = new PageInfo(pageNumber,pagzSize);
			PageInfo pi = new PageInfo(pageNumber, pagzSize);
			return buildPageRequest(pi.getPageIndex(), pi.getItemCount(), pi.getSortColumn());
		}
	}

	/**
	 * 创建分页请求.
	 */
	protected PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType) || "id".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if (sortType != null) {
			sort = new Sort(Direction.ASC, sortType);
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	protected PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType, String directionStr) {
		Direction direction = Direction.DESC;
		if (directionStr != null && "asc".equals(directionStr.toLowerCase())) {
			direction = Direction.ASC;
		}
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(direction, "id");
		} else if (sortType != null) {
			sort = new Sort(direction, sortType);
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	protected PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType, Direction direction) {

		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(direction, "id");
		} else if (sortType != null) {
			sort = new Sort(direction, sortType);
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	protected PageRequest buildPageRequest(PageInfo pageInfo) {
		Sort sort = null;
		if ("auto".equals(pageInfo.getSortColumn())) {
			sort = new Sort(pageInfo.getType(), "id");
		} else if (pageInfo.getSortColumn() != null) {
			sort = new Sort(pageInfo.getType(), pageInfo.getSortColumn());
		}
		return new PageRequest(pageInfo.getPageIndex() - 1, pageInfo.getItemCount(), sort);
	}

	protected PageRequest buildPageRequest(int pageNumber, int pagzSize, List<Order> orders) {
		Sort sort = new Sort(orders);

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	protected static void throwException(String code, String msg) {
		Map<String, String> response = new HashMap<String, String>();
		response.put("code", code);
		response.put("msg", msg);
		throw new RestException(response);
	}

	public static OperateLog addLog(String moduleId, String userId, String operateContent) {
		OperateLog log = new OperateLog();
		log.setModuleId(moduleId);
		log.setUserId(userId);
		log.setOperateContent(operateContent);
		log.setCreateTime(new Date());
		log.setUpdateTime(new Date());
		return log;
	}

	public static <E> Map<String, Object> getReturnMap(Map<String, Object> result, Page<E> page, PageInfo pageInfo) {
		if (page == null) {
			result.put("itemCount", 0);
			result.put("pageCount", 0);
			result.put("curPageIndex", pageInfo.getPageIndex());
			result.put("hasNext", false);
			return result;
		}
		result.put("itemCount", page.getTotalElements());
		result.put("pageCount", page.getTotalPages());
		result.put("curPageIndex", pageInfo.getPageIndex());
		result.put("hasNext", page.hasNext());
		return result;
	}

}
