package com.yryz.quanhu.coterie.coterie.until;

import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
* @Title: ImageUtils.java
* @Description: 图片处理类
* @Package org.util
* @author wangheng
* @date 2017年9月7日 下午5:18:12
*/
public final class ImageUtils {
	private static String FONT_NAME = "宋体";

	/** 
	* 导入本地图片到缓冲区 
	*/
	public static final BufferedImage loadImageLocal(String imgName) throws IOException {
		return ImageIO.read(new File(imgName));
	}

	/** 
	 * 导入网络图片到缓冲区 
	 */
	public static final BufferedImage loadImageUrl(String imgName) throws IOException {
		URL url = new URL(imgName);
		return ImageIO.read(url);
	}

	/** 
	 * 生成新图片到本地 
	 */
	public static final void writeImageLocal(String newImage, BufferedImage img) throws IOException {
		File outputfile = new File(newImage);
		ImageIO.write(img, "jpg", outputfile);
	}

	/**  
	* 实现图像的等比缩放  
	* @param source  
	* @param targetW  
	* @param targetH  
	* @return  
	*/
	public static BufferedImage resize(BufferedImage source, int targetW, int targetH) throws Exception {
		// targetW，targetH分别表示目标长和宽   
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放   
		// 则将下面的if else语句注释即可   
		if (sx < sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade   
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		// smoother than exlax:   
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	/**  
	* @Title: overlapMiddleImage  
	* @Description: 小图片贴到大图片形成一张图(居中合成)
	* @author wangheng
	* @param @param big
	* @param @param small
	* @param @return
	* @param @throws Exception
	* @return BufferedImage
	* @throws  
	*/
	public static final void overlapMiddleImage(BufferedImage big, BufferedImage small) throws Exception {
		int x = (big.getWidth() - small.getWidth()) / 2;
		int y = (big.getHeight() - small.getHeight()) / 2;
		overlapImage(big, small, x, y);
	}

	/**  
	* @Title: overlapImage  
	* @Description: 小图片贴到大图片形成一张图
	* @author wangheng
	* @param @param big
	* @param @param small
	* @param @param x
	* @param @param y
	* @param @return
	* @param @throws Exception
	* @return BufferedImage
	* @throws  
	*/
	public static final void overlapImage(BufferedImage big, BufferedImage small, int x, int y) throws Exception {
		Graphics2D g = big.createGraphics();
		g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
		g.dispose();
	}

	/**  
	* @Title: addText  
	* @Description: 图片添加文字,返回修改后的图片缓冲区（只输出一行文本） 
	* @author wangheng
	* @param @param img
	* @param @param content
	* @param @param x
	* @param @param y
	* @param @param fontSize
	* @param @return
	* @param @throws Exception
	* @return BufferedImage
	* @throws  
	*/
	public static final BufferedImage addText(BufferedImage img, Object content, int x, int y, int fontSize) throws Exception {
		Graphics2D g = img.createGraphics();
		g.setBackground(Color.WHITE);
		g.setColor(Color.BLACK);//设置字体颜色  
		g.setFont(new Font(FONT_NAME, Font.PLAIN, fontSize));
		if (content != null) {
			g.drawString(content.toString(), x, y);
		}
		g.dispose();

		return img;
	}

	/**  
	* @Title: addText  
	* @Description: 图片添加文字,返回修改后的图片缓冲区（只输出一行文本） 
	* @author wangheng
	* @param @param img
	* @param @param content
	* @param @param x
	* @param @param y
	* @param @param fontSize
	* @param @return
	* @param @throws Exception
	* @return BufferedImage
	* @throws  
	*/
	public static final BufferedImage middleAddText(BufferedImage img, String content, int y, int fontSize) throws Exception {
		if (StringUtils.isEmpty(content)) {
			return img;
		}

		Graphics2D g = img.createGraphics();
		g.setBackground(Color.WHITE);
		g.setColor(Color.BLACK);//设置字体颜色  
		Font font = new Font(FONT_NAME, Font.PLAIN, fontSize);

		g.drawString(content, getMiddleAddTextX(g, font, content, img.getWidth()), y);
		g.dispose();

		return img;
	}

	/**  
	* @Title: getMiddleAddTextX  
	* @Description: 图片居中添加文字 获取文字在图片中添加的 x 像素位置
	* @author wangheng
	* @param @param g
	* @param @param font
	* @param @param content
	* @param @param imgWidth
	* @param @return
	* @return int
	* @throws  
	*/
	public static final int getMiddleAddTextX(Graphics2D g, Font font, String content, int imgWidth) throws Exception {

		// 获取字体的像素范围对象
		Rectangle2D stringBounds = font.getStringBounds(content, g.getFontRenderContext());
		double textFontWidth = stringBounds.getWidth();
		if (textFontWidth > imgWidth && font.getSize() > 1) {
			font = new Font(font.getName(), font.getStyle(), font.getSize() - 1);
			getMiddleAddTextX(g, font, content, imgWidth);
		}
		g.setFont(font);

		return (imgWidth - (int) textFontWidth) / 2;
	}

	public static final BufferedImage createBaseImage(int width, int height, Color cackgroundColor) throws Exception {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取Graphics2D  
		Graphics2D g2d = image.createGraphics();
		// 画图  
		g2d.setBackground(cackgroundColor);
		g2d.clearRect(0, 0, width, height);
		g2d.dispose();
		return image;
	}

	/**
	 * 校验图片大小
	 * 
	 * @param url,网络图片地址
	 * @param size,图片要限制的大小
	 * @return fasle验证不通过，true验证通过
	 */
	public boolean checkImageSize(String url, int size) {
		try {
			URL imageUrl = new URL(url);
			URLConnection connection = imageUrl.openConnection();
			int imageSize = connection.getContentLength() / 1024;
			if (imageSize <= size) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 校验图片宽高
	 * 
	 * @param url,网络图片地址
	 * @param width,图片要限制的宽度
	 * @param height,图片要限制的高度
	 * @return fasle验证不通过，true验证通过
	 */
	public boolean checkImageWidthHeight(String url, int width, int height) {
		try {
			URL imageUrl = new URL(url);
			URLConnection connection = imageUrl.openConnection();
			connection.setDoOutput(true);
			BufferedImage image = ImageIO.read(connection.getInputStream());
			int srcWidth = image.getWidth(); // 源图宽度
			int srcHeight = image.getHeight(); // 源图高度
			if (srcWidth <= width && srcHeight <= height) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			URL url = new URL("https://cdn.yryz.com/pic/yxwjq/360ba7b2-b843-4f76-b401-283084fa06c8.jpg");
			URLConnection connection = url.openConnection();
			System.out.println("文件大小：" + (connection.getContentLength() / 1024) + "KB");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
