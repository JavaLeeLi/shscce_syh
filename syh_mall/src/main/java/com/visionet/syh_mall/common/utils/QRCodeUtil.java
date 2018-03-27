package com.visionet.syh_mall.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;

public class QRCodeUtil {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String QRcode(String collectEvaluationCode,String QRcode) throws IOException{
		String PATH = FilePathUtils.FILE_PATH+collectEvaluationCode+".png";//图片保存路径
		String text = QRcode;
		int width=150;
		int height=150;
		String format="png";
		Hashtable hints=new Hashtable();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);
		try {
			BitMatrix bitMatrix=new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
			Path file=new File(PATH).toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, format, file);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return PATH;
	}
	
	public static void QRinsertExcel(HSSFWorkbook wb,HSSFSheet sheet1,String PATH, int j, int k){
	    BufferedImage bufferImg =null;
	    try{
	    	//先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
	    	ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
	    	bufferImg = ImageIO.read(new File(PATH));
	    	ImageIO.write(bufferImg,"png",byteArrayOut);
	    	
	    	//获得一个工作薄
	    	HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
	    	HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,0,0,(short) k,j+1,(short)(k+1),(j+2));   
	    	anchor.setAnchorType(AnchorType.MOVE_DONT_RESIZE);  
	    	//插入图片
	    	patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_PNG));  
	    }catch(IOException io){
            io.printStackTrace();
            System.out.println("io erorr : "+ io.getMessage());
	    }
    }
}
