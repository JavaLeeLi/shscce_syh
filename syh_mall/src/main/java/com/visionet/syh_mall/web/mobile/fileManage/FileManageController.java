package com.visionet.syh_mall.web.mobile.fileManage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.file.FileUtil;
import com.visionet.syh_mall.common.utils.image.UploadUtil;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.fileManage.FileManageService;
import com.visionet.syh_mall.web.BaseController;

/**
 * 文件管理
 * 
 * @author xiaofb
 * @time 2017年9月5日
 */
@RestController
@RequestMapping("/api/file")
public class FileManageController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(FileManageController.class);

	@Autowired
	private FileManageService fileService;

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public BaseReturnVo<Object> uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		logger.info("文件上传请求参数:{}", request);
		Map<String, Object> map = new HashMap<String, Object>();
		// String style = request.getParameter("style"); //文件类型(文件后缀名)
		String sufix = FileUtil.getExtension(file.getOriginalFilename()); // 文件后缀名
		int usage = Integer.valueOf(request.getParameter("usage")); // 文件用途
		int zip = Integer.valueOf(request.getParameter("zip")); // 是否压缩
		try {
			String[] tempPathArr = UploadUtil.GetCreateTempPath();
			file.transferTo(new File(tempPathArr[0])); // 物理存储临时文件
			Map<String, String> param = new HashMap<String, String>();
			param.put("sufix", sufix);
			param.put("usage", String.valueOf(usage));
			param.put("tempFilePath", tempPathArr[0]);
			param.put("originalFilename", file.getOriginalFilename());
			param.put("zip", String.valueOf(zip));
			// 压缩文件
			map = fileService.upLoadPic(param);
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			e.printStackTrace();
			throw new RestException("未知异常");
		}
		return BaseReturnVo.success(map);
	}
	/**
	 * 文件删除
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/deleteFile",method=RequestMethod.POST)
	public BaseReturnVo<Object> deleteFile(@RequestBody Map<String,Object> map){
		logger.info("文件删除:{}",map);
		List<String> fileID = (List<String>) map.get("imgs");
		fileService.delFile(fileID,map.get("type"));
		return BaseReturnVo.success("删除成功");
	}
}
