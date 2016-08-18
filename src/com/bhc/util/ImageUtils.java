package com.bhc.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片工具类， 图片水印，文字水印，缩放，补白等
 * 
 * @author Carl He
 */
public final class ImageUtils {
	/** 图片格式：JPG */
	private static final String PICTRUE_FORMATE_JPG = "jpg";

	private ImageUtils() {
	}

	/**
	 * 添加图片水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param waterImg
	 *            水印图片路径，如：C://myPictrue//logo.png
	 * @param x
	 *            水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 */
	public final static void pressImage(String targetImg, String waterImg, int x, int y, float alpha) {
		try {
			// File file = new File(targetImg);
			// Image image = ImageIO.read(file);
			BufferedImage image = ImageIO.read(new FileInputStream(targetImg));
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();

			g.drawImage(image, 0, 0, width, height, null);

			// Image waterImage = ImageIO.read(new File(waterImg)); // 水印文件
			BufferedImage waterImage = ImageIO.read(new FileInputStream(waterImg));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			waterImage = resize(waterImage, height, width, true);

			int width_1 = waterImage.getWidth(null);
			int height_1 = waterImage.getHeight(null);

			int widthDiff = width - width_1;
			int heightDiff = height - height_1;
			if (x < 0) {
				x = widthDiff / 2;
			} else if (x > widthDiff) {
				x = widthDiff;
			}
			if (y < 0) {
				y = heightDiff / 2;
			} else if (y > heightDiff) {
				y = heightDiff;
			}

			ImageFilter imgf = new MyFilter(255);
			FilteredImageSource fis = new FilteredImageSource(waterImage.getSource(), imgf);
			Image im = Toolkit.getDefaultToolkit().createImage(fis);

			g.drawImage(im, x, y, width_1, height_1, null); // 水印文件结束
			g.dispose();

			// 把以上原图和加上图标后的图像
			File f = new File(targetImg);
			ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加文字水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param pressText
	 *            水印文字， 如：中国证券网
	 * @param fontName
	 *            字体名称， 如：宋体
	 * @param fontStyle
	 *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
	 * @param fontSize
	 *            字体大小，单位为像素
	 * @param color
	 *            字体颜色
	 * @param x
	 *            水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 */
	public static void pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize,
			Color color, int x, int y, float alpha) {
		try {
			File file = new File(targetImg);

			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setColor(color);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

			int width_1 = fontSize * getLength(pressText);
			int height_1 = fontSize;
			int widthDiff = width - width_1;
			int heightDiff = height - height_1;
			if (x < 0) {
				x = widthDiff / 2;
			} else if (x > widthDiff) {
				x = widthDiff;
			}
			if (y < 0) {
				y = heightDiff / 2;
			} else if (y > heightDiff) {
				y = heightDiff;
			}

			g.drawString(pressText, x, y + height_1);
			g.dispose();
			ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
	 * 
	 * @param text
	 * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
	 */
	public static int getLength(String text) {
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
			if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
				length++;
			}
		}
		return (length % 2 == 0) ? length / 2 : length / 2 + 1;
	}

	/**
	 * 图片缩放
	 * 
	 * @param filePath
	 *            图片路径
	 * @param height
	 *            高度
	 * @param width
	 *            宽度
	 * @param bb
	 *            比例不对时是否需要补白
	 */
	public static BufferedImage resize(BufferedImage bi, int height, int width, boolean bb) {
		try {
			double ratio = 0; // 缩放比例
			// File f = new File(filePath);
			// BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue() / bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			BufferedImage image = null;
			if (bb) {
				image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				g.dispose();
				itemp = image;
			}
			return image;
			// ImageIO.write((BufferedImage) itemp, "jpg", f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		pressImage("F:/cwb/imgs/水印/3.jpg", "F:/cwb/imgs/水印/d.jpg", 0, 0, 1.0f);
		// pressText("C://pic//jpg", "旺仔之印", "宋体", Font.BOLD | Font.ITALIC, 20,
		// Color.BLACK, 0, 0, 8f);
		// resize("C://pic//4.jpg", 1000, 500, true);
	}

}

class MyFilter extends RGBImageFilter {// 抽象类RGBImageFilter是ImageFilter的子类，
	// 继承它实现图象ARGB的处理
	int alpha = 0;

	public MyFilter(int alpha) {// 构造器，用来接收需要过滤图象的尺寸，以及透明度
		this.canFilterIndexColorModel = true;
		// TransparentImageFilter类继承自RGBImageFilter，它的构造函数要求传入原始图象的宽度和高度。
		// 该类实现了filterRGB抽象函数
		// ，缺省的方式下，该函数将x，y所标识的象素的ARGB值传入，程序员按照一定的程序逻辑处理后返回该象素新的ARGB值
		this.alpha = alpha;
	}

	public int filterRGB(int x, int y, int rgb) {
		DirectColorModel dcm = (DirectColorModel) ColorModel.getRGBdefault();
		// DirectColorModel类用来将ARGB值独立分解出来
		int red = dcm.getRed(rgb);
		int green = dcm.getGreen(rgb);
		int blue = dcm.getBlue(rgb);
		int alp = dcm.getAlpha(rgb);

		if (red == 255 && blue == 255 && green == 255) {// 如果像素为白色，则让它透明
			alpha = 0;
		} else {
			alpha = 255;
		}
		// if (alp==0) {//png和gif格式图片透明部分仍然透明
		// alpha = 0;
		// }else{
		// alpha = 255;
		// }
		return alpha << 24 | red << 16 | green << 8 | blue;// 进行标准ARGB输出以实现图象过滤
	}

}
