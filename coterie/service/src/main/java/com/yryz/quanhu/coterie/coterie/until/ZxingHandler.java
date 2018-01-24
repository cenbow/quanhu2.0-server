package com.yryz.quanhu.coterie.coterie.until;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Hashtable;

/**
 * 条形码和二维码编码解码
 * 
 * @author ThinkGem
 * @version 2014-02-28
 */
public class ZxingHandler {

	/**
	 * 条形码编码
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 * @param imgPath
	 */
	public static void encode(String contents, int width, int height, String imgPath) {
		int codeWidth = 3 + // start guard
				(7 * 6) + // left bars
				5 + // middle guard
				(7 * 6) + // right bars
				3; // end guard
		codeWidth = Math.max(codeWidth, width);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.EAN_13, codeWidth, height, null);

			
			MatrixToImageWriter
					.writeToFile(bitMatrix, "png", new File(imgPath));
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 条形码解码
	 * 
	 * @param imgPath
	 * @return String
	 */
	public static String decode(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				System.out.println("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			result = new MultiFormatReader().decode(bitmap, null);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 二维码编码
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 * @param filePath
	 * @param fineName
	 * @throws WriterException 
	 * @throws IOException 
	 */
	public static void encode2(String contents, int width, int height, String format, 
			String filePath, String fileName) 
		throws WriterException, IOException {
		
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "GBK");
		
		BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
				BarcodeFormat.QR_CODE, width, height, hints);
		if(filePath.endsWith("\\") || filePath.endsWith("/")){
			filePath=filePath.substring(0, filePath.length()-1);
		}
		Path path = FileSystems.getDefault().getPath(filePath, fileName);  
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像  
	}
	
	public static void encode2(String contents, int width, int height, OutputStream outputStream) {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.QR_CODE, width, height, hints);
			
			MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 二维码解码
	 * 
	 * @param imgPath
	 * @return String
	 */
	public static String decode2(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				System.out.println("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "GBK");

			result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// 条形码
//		String imgPath = "target\\zxing_EAN13.png";
//		String contents = "6923450657713";
//		int width = 105, height = 50;
//		
//		ZxingHandler.encode(contents, width, height, imgPath);
//		System.out.println("finished zxing EAN-13 encode.");
//
//		String decodeContent = ZxingHandler.decode(imgPath);
//		System.out.println("解码内容如下：" + decodeContent);
//		System.out.println("finished zxing EAN-13 decode.");
		
		// 二维码
		String filePath = "D://tmp";
		String fileName = "qr.png";
		String contents2 = "http://www.yryz.com/file/app/yryz.apk ";
		int width2 = 300, height2 = 300;

		try {
			ZxingHandler.encode2(contents2, width2, height2, "png", filePath, fileName);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("finished zxing encode.");

//		String imgPath = filePath + System.getProperty("file.separator") + fileName;
//		String decodeContent2 = ZxingHandler.decode2(imgPath);
//		System.out.println("解码内容如下：" + decodeContent2);
//		System.out.println("finished zxing decode.");
		
	}
    
}