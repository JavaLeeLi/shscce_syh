package com.visionet.syh_mall.service.fileManage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.SysConstants;
import com.visionet.syh_mall.common.file.FileUtil;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.image.DeleteUtil;
import com.visionet.syh_mall.common.utils.image.ImageUtil;
import com.visionet.syh_mall.common.utils.image.UploadUtil;
import com.visionet.syh_mall.entity.FileManage;
import com.visionet.syh_mall.entity.goods.EvaluationPicLink;
import com.visionet.syh_mall.entity.goods.GoodsPicLink;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.goods.EvaluationPicLinkRepository;
import com.visionet.syh_mall.repository.mobile.GoodsPicLinkRepository;
import com.visionet.syh_mall.service.BaseService;

@Service
public class FileManageService extends BaseService {
	
	@Autowired
	private FileManageRepostory fileManageRepo;
	@Autowired
	private GoodsPicLinkRepository goodsPicLinkDao;
	@Autowired
	private EvaluationPicLinkRepository evaluationPicLinkDao;
	
	/**
	 * 保存文件信息
	 * @param file
	 * @return
	 */
	public FileManage saveFile(FileManage file){
		file.setCreateTime(DateUtil.getCurrentDate());
		file = fileManageRepo.save(file);
		return file;
	}
	
	/**
	 * 图片上传
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> upLoadPic(Map<String,String> param) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sufix = param.get("sufix");
		int usage = Integer.valueOf(param.get("usage").toString());
		File originfile = new File(param.get("tempFilePath"));
		String fileName = URLDecoder.decode(param.get("originalFilename"), "UTF-8");
		String[] destPathArr = UploadUtil.GetCreatePathWithSuffix(fileName,UploadUtil.IMAGE);
		File destFile = new File(destPathArr[0]);	
		FileUtil.copyFile(originfile, destFile);
		BufferedImage  bsrc = ImageIO.read(destFile);	//原图
		String id = saveFile(bsrc,destFile,sufix,usage);
		//是否压缩
		if(SysConstants.YES == Integer.valueOf(param.get("zip"))){
			ImageUtil.zoom(destFile.getAbsolutePath(), destFile.getParent() + File.separatorChar + FileUtil.appendMark(destFile.getName(),"-mid") , null, FilePathUtils.UPLOAD_IMG_SIZE_MID_WIDTH, null);
			ImageUtil.zoom(destFile.getAbsolutePath(), destFile.getParent() + File.separatorChar + FileUtil.appendMark(destFile.getName(),"-min") , null, FilePathUtils.UPLOAD_IMG_SIZE_MIN_WIDTH, null);
			File midFile = new File(destFile.getParent() + File.separatorChar + FileUtil.appendMark(destFile.getName(),"-mid"));
			bsrc = ImageIO.read(midFile);
			String midId = saveFile(bsrc,midFile,sufix,usage);	//保存中图大小  400
			resultMap.put("fileMidId", midId);
			resultMap.put("fileMidUrl", FilePathUtils.fileUrl(midFile.getPath()));
			File minFile = new File(destFile.getParent() + File.separatorChar + FileUtil.appendMark(destFile.getName(),"-min"));
			bsrc = ImageIO.read(minFile);
			String minId = saveFile(bsrc,minFile,sufix,usage);	//保存小图大小  150
			resultMap.put("fileMinId", minId);
			resultMap.put("fileMinUrl", FilePathUtils.fileUrl(minFile.getPath()));
		}
		resultMap.put("fileId", id);
		resultMap.put("fileUrl", FilePathUtils.fileUrl(destPathArr[0]));
		return resultMap;
	}
	
	/**
	 * 数据库存储文件信息
	 * @param bsrc
	 * @param file
	 * @param Style
	 * @param usage
	 * @return
	 */
	private String saveFile(BufferedImage bsrc,File file,String Style,int usage){
		FileManage fileManage = new FileManage();
		fileManage.setFileName(file.getName());
		fileManage.setFilePath(file.getPath());
		fileManage.setFileSize((int)file.length());
		fileManage.setFileStyle(Style);
		fileManage.setFileUsage(usage);
		fileManage.setPixelHeight(bsrc.getHeight());
		fileManage.setPixelWidth(bsrc.getWidth());
		fileManage.setCreateTime(DateUtil.getCurrentDate());
		fileManage = fileManageRepo.save(fileManage);
		return fileManage.getId();
	}
	
	/**
	 * 通过id获取path
	 * @param id
	 * @return
	 */
	public String findPathById(String id){
		return fileManageRepo.findUrlById(id);
	}
	
	/**
	 * 删除文件
	 * @param 
	 * @return void
	 * @throws
	 */
	@Transactional
	public void delFile(List<String> fileIds,Object type) {
		List<String> imgs = new ArrayList<String>();
		if(!StringUtils.isEmpty(type)){
			//再编辑时，删除图片操作只会传递一个id过来
			if(fileIds.size()==1){
				List<GoodsPicLink> goodsPicLinks = goodsPicLinkDao.findByImg(fileIds.get(0));
				fileIds = new ArrayList<String>();
				for (GoodsPicLink goodsPicLink : goodsPicLinks) {
					fileIds.add(goodsPicLink.getMaxImgId());
					fileIds.add(goodsPicLink.getMidImgId());
					fileIds.add(goodsPicLink.getMinImgId());
				}
				List<EvaluationPicLink> evaImgs = evaluationPicLinkDao.findByImg(fileIds.get(0));
				for (EvaluationPicLink EvaluationPicLink : evaImgs) {
					fileIds.add(EvaluationPicLink.getMaxImgId());
					fileIds.add(EvaluationPicLink.getMidImgId());
					fileIds.add(EvaluationPicLink.getMinImgId());
				}
			}
			for (String imgId : fileIds) {
				List<GoodsPicLink> goodsPicLinks = goodsPicLinkDao.findByImg(imgId);
				if(goodsPicLinks.size()>0){
					for (GoodsPicLink goodsPicLink : goodsPicLinks) {						
						goodsPicLink.setIsDeleted(1);
						goodsPicLinkDao.save(goodsPicLink);
					}
				}
				List<EvaluationPicLink> EvaluationPicLinks = evaluationPicLinkDao.findByImg(imgId);
				if(EvaluationPicLinks.size()>0){
					for (EvaluationPicLink evaluationPicLink : EvaluationPicLinks) {
						evaluationPicLink.setIsDeleted(1);
						evaluationPicLinkDao.save(evaluationPicLink);
					}
				}
			}
		}
		for (String fileId: fileIds) {
			FileManage fileManage = fileManageRepo.findOne(fileId);
			imgs.add(fileManage.getFilePath());
			fileManageRepo.delete(fileManage);
		}
		DeleteUtil.deleteRun(imgs);
	}
}
