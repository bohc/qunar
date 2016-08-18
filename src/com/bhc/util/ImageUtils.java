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
 * ͼƬ�����࣬ ͼƬˮӡ������ˮӡ�����ţ����׵�
 * 
 * @author Carl He
 */
public final class ImageUtils {
	/** ͼƬ��ʽ��JPG */
	private static final String PICTRUE_FORMATE_JPG = "jpg";

	private ImageUtils() {
	}

	/**
	 * ���ͼƬˮӡ
	 * 
	 * @param targetImg
	 *            Ŀ��ͼƬ·�����磺C://myPictrue//1.jpg
	 * @param waterImg
	 *            ˮӡͼƬ·�����磺C://myPictrue//logo.png
	 * @param x
	 *            ˮӡͼƬ����Ŀ��ͼƬ����ƫ���������x<0, �������м�
	 * @param y
	 *            ˮӡͼƬ����Ŀ��ͼƬ�ϲ��ƫ���������y<0, �������м�
	 * @param alpha
	 *            ͸����(0.0 -- 1.0, 0.0Ϊ��ȫ͸����1.0Ϊ��ȫ��͸��)
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

			// Image waterImage = ImageIO.read(new File(waterImg)); // ˮӡ�ļ�
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

			g.drawImage(im, x, y, width_1, height_1, null); // ˮӡ�ļ�����
			g.dispose();

			// ������ԭͼ�ͼ���ͼ����ͼ��
			File f = new File(targetImg);
			ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������ˮӡ
	 * 
	 * @param targetImg
	 *            Ŀ��ͼƬ·�����磺C://myPictrue//1.jpg
	 * @param pressText
	 *            ˮӡ���֣� �磺�й�֤ȯ��
	 * @param fontName
	 *            �������ƣ� �磺����
	 * @param fontStyle
	 *            ������ʽ���磺�����б��(Font.BOLD|Font.ITALIC)
	 * @param fontSize
	 *            �����С����λΪ����
	 * @param color
	 *            ������ɫ
	 * @param x
	 *            ˮӡ���־���Ŀ��ͼƬ����ƫ���������x<0, �������м�
	 * @param y
	 *            ˮӡ���־���Ŀ��ͼƬ�ϲ��ƫ���������y<0, �������м�
	 * @param alpha
	 *            ͸����(0.0 -- 1.0, 0.0Ϊ��ȫ͸����1.0Ϊ��ȫ��͸��)
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
	 * ��ȡ�ַ����ȣ�һ��������Ϊ 1 ���ַ�, һ��Ӣ����ĸ��Ϊ 0.5 ���ַ�
	 * 
	 * @param text
	 * @return �ַ����ȣ��磺text="�й�",���� 2��text="test",���� 2��text="�й�ABC",���� 4.
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
	 * ͼƬ����
	 * 
	 * @param filePath
	 *            ͼƬ·��
	 * @param height
	 *            �߶�
	 * @param width
	 *            ���
	 * @param bb
	 *            ��������ʱ�Ƿ���Ҫ����
	 */
	public static BufferedImage resize(BufferedImage bi, int height, int width, boolean bb) {
		try {
			double ratio = 0; // ���ű���
			// File f = new File(filePath);
			// BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			// �������
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
		pressImage("F:/cwb/imgs/ˮӡ/3.jpg", "F:/cwb/imgs/ˮӡ/d.jpg", 0, 0, 1.0f);
		// pressText("C://pic//jpg", "����֮ӡ", "����", Font.BOLD | Font.ITALIC, 20,
		// Color.BLACK, 0, 0, 8f);
		// resize("C://pic//4.jpg", 1000, 500, true);
	}

}

class MyFilter extends RGBImageFilter {// ������RGBImageFilter��ImageFilter�����࣬
	// �̳���ʵ��ͼ��ARGB�Ĵ���
	int alpha = 0;

	public MyFilter(int alpha) {// ������������������Ҫ����ͼ��ĳߴ磬�Լ�͸����
		this.canFilterIndexColorModel = true;
		// TransparentImageFilter��̳���RGBImageFilter�����Ĺ��캯��Ҫ����ԭʼͼ��Ŀ�Ⱥ͸߶ȡ�
		// ����ʵ����filterRGB������
		// ��ȱʡ�ķ�ʽ�£��ú�����x��y����ʶ�����ص�ARGBֵ���룬����Ա����һ���ĳ����߼�����󷵻ظ������µ�ARGBֵ
		this.alpha = alpha;
	}

	public int filterRGB(int x, int y, int rgb) {
		DirectColorModel dcm = (DirectColorModel) ColorModel.getRGBdefault();
		// DirectColorModel��������ARGBֵ�����ֽ����
		int red = dcm.getRed(rgb);
		int green = dcm.getGreen(rgb);
		int blue = dcm.getBlue(rgb);
		int alp = dcm.getAlpha(rgb);

		if (red == 255 && blue == 255 && green == 255) {// �������Ϊ��ɫ��������͸��
			alpha = 0;
		} else {
			alpha = 255;
		}
		// if (alp==0) {//png��gif��ʽͼƬ͸��������Ȼ͸��
		// alpha = 0;
		// }else{
		// alpha = 255;
		// }
		return alpha << 24 | red << 16 | green << 8 | blue;// ���б�׼ARGB�����ʵ��ͼ�����
	}

}
