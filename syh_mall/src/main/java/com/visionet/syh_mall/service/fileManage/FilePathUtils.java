package com.visionet.syh_mall.service.fileManage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.visionet.syh_mall.common.string.StringUtil;

/**
 * 文件上传util
 * @author xiaofb
 * @time 2017年9月5日
 */
@Component
public class FilePathUtils {
	
	
	public static String FILE_PATH;
	
	@Value("${filePath}")
	public void setFILE_PATH(String fILE_PATH) {
		FILE_PATH = fILE_PATH;
	}
	
	private static String HOST;
	
	@Value("${fileHost}")
	public void setHOST(String hOST) {
		HOST = hOST;
	}
	
	private final static String FILE_URL = "/images/";
	public final static Integer UPLOAD_IMG_SIZE_MID_WIDTH = 400;
	public final static Integer UPLOAD_IMG_SIZE_MIN_WIDTH = 150;
	
	
	/**
	 * 将path转换成访问url
	 * @param filePath
	 * @return
	 */
	public static String fileUrl(String filePath){
		return HOST + StringUtil.replace(filePath, FILE_PATH, FILE_URL);
	}
	
	private static String NOTIFYADDRESS;

	@Value("${notifyAddress}")
	public void setNOTIFYADDRESS(String nOTIFYADDRESS) {
		NOTIFYADDRESS = nOTIFYADDRESS;
	}
	
	public static String QRCode(String url){
		return NOTIFYADDRESS+url;
	}
	
}
