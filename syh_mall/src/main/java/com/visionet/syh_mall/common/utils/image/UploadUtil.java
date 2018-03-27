package com.visionet.syh_mall.common.utils.image;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import com.visionet.syh_mall.common.file.FileUtil;
import com.visionet.syh_mall.common.string.StringPool;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.props.PropsKeys;
import com.visionet.syh_mall.common.utils.props.PropsUtil;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;


public class UploadUtil {
	public static String DOCUMENT = "document";
	public static String IMAGE = "image";
	public static String DEFAULT = "default";
	
	/**
	 * @param sign
	 * @return
	 */
	public static String[] GetCreatePath(String sign){
		String path = PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_ROOT_PATH);
		
		String name = UUID.randomUUID().toString() + "-" + (int)(Math.random() * 10000);
		
		String dateStr = DateUtil.convertToString(new Date(), DateUtil.YMD1);
		
		String relativePath = dateStr + StringPool.FORWARD_SLASH + (sign == null ? DEFAULT : sign) + StringPool.FORWARD_SLASH + name;
		
		path = path + StringPool.FORWARD_SLASH + relativePath;
		
		FileUtil.mkdirs((new File(path)).getParent());
		
		return new String[]{path,relativePath,name};
	}
	
	/**
	 * path: /home/visionet/sloth/product_affix/cmcc/uploadFile/YYYYMMDD/stream/7efbd59d9741d34f.doc
	 * relativePath: YYYYMMDD/stream/7efbd59d9741d34f.doc
	 * name: 7efbd59d9741d34f.doc
	 * @param sign
	 * @return
	 */
	public static String[] GetCreatePathWithSuffix(String realName,String sign) throws Exception{
		String type = realName.substring(realName.lastIndexOf(".") + 1);
		sign = sign == null ? GetSignByType(type) : sign;
		String suffix = "";
		if(!type.isEmpty()){
			suffix = StringPool.PERIOD + type;
		}
		String path = FilePathUtils.FILE_PATH;
		String name = UUID.randomUUID().toString() + "-" + (int)(Math.random() * 10000) + suffix;
		
		String dateStr = DateUtil.convertToString(new Date(), DateUtil.YMD1);
		
		String relativePath = dateStr + StringPool.FORWARD_SLASH + (sign == null ? DEFAULT : sign) + StringPool.FORWARD_SLASH + name;
		
		path = path + StringPool.FORWARD_SLASH + relativePath;
		
		FileUtil.mkdirs((new File(path)).getParent());
		
		return new String[]{path,relativePath,name};
	}
	
	public static String[] GetCreateTempPath(){
		String path = FilePathUtils.FILE_PATH;
		
		String name = UUID.randomUUID().toString() + "-" + (int)(Math.random() * 10000);
		
		String dateStr = DateUtil.convertToString(new Date(), DateUtil.YMD1);
		
		String relativePath = "temp" + StringPool.FORWARD_SLASH + dateStr + StringPool.FORWARD_SLASH + name;
		
		path = path + StringPool.FORWARD_SLASH + relativePath;
		
		FileUtil.mkdirs((new File(path)).getParent());
		
		return new String[]{path,relativePath,name};
	}
	
	public static String GetSignByName(String fileName){
		String type = fileName.substring(fileName.lastIndexOf(".") + 1);
		return GetSignByType(type);
	}
	
	public static String GetSignByType(String type){
		if (PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_TYPE_DOCUMENT).contains(type.toLowerCase())) {
			return DOCUMENT;
		} else if (PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_TYPE_IMG).contains(type.toLowerCase())) {
			return IMAGE;
		}else{
			return DEFAULT;
		}		
		
	}
	
	public static String GetDownloadPath(){
		return PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH);
	}
	
	public static String GetAPKDownloadPath(){
		return PropsUtil.getProperty(PropsKeys.MOBILE_CLIENT_DOWNLOAD_ANDRIOD);
	}
	
	public static String GetIOSDownloadPath(){
		return PropsUtil.getProperty(PropsKeys.MOBILE_CLIENT_DOWNLOAD_IOS);
	}
	public static String GetIOSHdDownloadPath(){
		return PropsUtil.getProperty(PropsKeys.MOBILE_CLIENT_DOWNLOAD_IOS_HD);
	}
	
	public static String GetSMSIOSDownloadPath(){
		return PropsUtil.getProperty(PropsKeys.SMS_DOWNLOAD_MOBILE_CLIENT_IOS);
	}
    public static String GetSMSIOSHDDownloadPath(){
        return PropsUtil.getProperty(PropsKeys.SMS_DOWNLOAD_MOBILE_CLIENT_IOS_HD);
    }

    public static String GetQrcodeIOSDownloadPath(){
        return PropsUtil.getProperty(PropsKeys.MOBILE_QRCODE_DOWNLOAD_IOS);
    }
    public static String GetQrcodeIOSHDDownloadPath(){
        return PropsUtil.getProperty(PropsKeys.MOBILE_QRCODE_DOWNLOAD_IOS_HD);
    }
    public static String GetQrcodeAndroidDownloadPath(){
        return PropsUtil.getProperty(PropsKeys.MOBILE_QRCODE_DOWNLOAD_ANDRIOD);
    }
	
	public static String getDomainDownloadPath(){
		return PropsUtil.getProperty(PropsKeys.SERVICE_DOMAIN)+
				"/"+PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH); 
	}

	public static String GetQrcodeLoginPath(){
		return PropsUtil.getProperty(PropsKeys.MOBILE_QRCODE_LOGIN);
	}
}
