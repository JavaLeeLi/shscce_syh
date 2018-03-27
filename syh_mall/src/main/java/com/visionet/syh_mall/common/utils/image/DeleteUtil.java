package com.visionet.syh_mall.common.utils.image;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;


public class DeleteUtil {
	private static final Logger logger = Logger.getLogger(DeleteUtil.class);
//	private static String PATH = PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_ROOT_PATH);
	private static boolean isRunning = false;
	
	public static void deleteRun(List<String> filesNames) {
		if (!isRunning) {
			logger.debug("删除目录下图片执行开始...");
			isRunning = true;
			logger.debug("开始执行删除文件夹img文件任务");
			for (String imgFile : filesNames) {						
				File file = new File(imgFile);
				if (true==file.isFile()) {
					boolean flag = false;
					flag = file.delete();
					if (flag) {
						logger.debug("成功删除图片文件:" + file.getName());
					}
				}
			}
			/*File fileTemp = new File(PATH);
			//判断文件是否存在
			boolean falg = false;
			falg = fileTemp.exists();
			if (falg) {
				logger.debug("文件存在");
				if (true == fileTemp.isDirectory()) {
					logger.debug("temp文件是个目录");
				}
			} else {
				logger.debug("未找到文件夹，执行失败");
			}*/
			isRunning = false;
			logger.debug("删除目录下图片执行结束...");
		}
	}
}
