package com.visionet.syh_mall.web.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.entity.shop.Marketing;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.mobile.ServiceReservationService;
import com.visionet.syh_mall.vo.ServiceReservationVo;
import com.visionet.syh_mall.web.BaseController;

/**
 * 服务预约的Controller
 * 
 * @author chenghongzhan
 * @version 2017年8月23日 下午10:46:44
 *
 */
@RestController
@RequestMapping(value = "api/service")
public class ServiceReservationController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ServiceReservationController.class);

	@Autowired
	private ServiceReservationService reservationService;

	/**
	 * 
	 * @Title: getAddonPkgs @Description: 搜索增值服务 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/getAddonPkgs", method = RequestMethod.POST)
	public BaseReturnVo<Object> getAddonPkgs() {
		logger.info("搜索增值服务的参数:{}");
		List<Marketing> result = null;
		try {
			result =reservationService.getAddonPkgs();
		} catch (RestException e) {
			logger.error("搜索增值服务异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索增值服务异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}
	
	
	/**
	 * @Title: searchReservation @Description: 查询服务的方法 @param @param
	 *         param @param @return @param @throws ParseException 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "searchReservation", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchReservation(@RequestBody Map<String, Object> param) throws ParseException {
		logger.info("查询服务的参数:{}", param);
		Map<String, Object> result = null;
		try {
			result = reservationService.searchReservation(param, getPageInfo(param, Direction.DESC,"updateTime"));
		} catch (RestException e) {
			logger.error("查询服务异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("查询服务异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: reserveService @Description: 增加服务预约 @param @param
	 *         param @param @return @param @throws ParseException 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "reserveService", method = RequestMethod.POST)
	@Log(name="增加服务预约",model="服务模块")
	public BaseReturnVo<Object> reserveService(@RequestBody @Valid ServiceReservationVo reservationVo,
			BindingResult result) {
		logger.info("增加服务预约的参数:{}", reservationVo.toString());
		try {
			reservationService.addReserveService(reservationVo, getCurrentUserId());
		} catch (RestException e) {
			logger.error("增加服务预约异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("增加服务预约异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: getReservationDetail @Description: 得到服务预约的详细信息 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "getReservationDetail", method = RequestMethod.POST)
	public BaseReturnVo<Object> getReservationDetail(@RequestBody Map<String, String> param) {
		logger.info("服务预详情约的参数:{}", param);
		Map<String, Object> result = null;
		try {
			result = reservationService.getReservationDetail(param.get("reservationID"));
		} catch (RestException e) {
			logger.error("得到服务预约详情异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("得到服务预约详情异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: inquiryRecognizeCode @Description: 鉴评码查询 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "inquiryRecognizeCode", method = RequestMethod.POST)
	public BaseReturnVo<Object> inquiryRecognizeCode(@RequestBody Map<String, String> param) {
		logger.info("鉴评码查询的参数:{}", param);
		Map<String, Object> result = null;
		try {
			result = reservationService.inquiryRecognizeCode(param.get("userID"), param.get("recognizeCode"));
		} catch (RestException e) {
			logger.error("鉴评码查询异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("鉴评码查询异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

}
