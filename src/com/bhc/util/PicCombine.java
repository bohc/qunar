package com.bhc.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
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
import java.net.URL;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class PicCombine {

	private Font font = new Font("���Ĳ���", Font.PLAIN, 40);// ����������������

	private Graphics2D g = null;

	private int fontsize = 0;

	private int x = 0;

	private int y = 0;

	/**
	 * ���뱾��ͼƬ��������
	 */
	public BufferedImage loadImageLocal(String imgName) {
		try {
			return ImageIO.read(new File(imgName));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * ��������ͼƬ��������
	 */
	public BufferedImage loadImageUrl(String imgName) {
		try {
			URL url = new URL(imgName);
			return ImageIO.read(url);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * ������ͼƬ������
	 */
	public void writeImageLocal(String newImage, BufferedImage img) {
		if (newImage != null && img != null) {
			try {
				File outputfile = new File(newImage);
				ImageIO.write(img, "jpg", outputfile);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * �趨���ֵ������
	 */
	public void setFont(String fontStyle, int fontSize) {
		this.fontsize = fontSize;
		this.font = new Font(fontStyle, Font.PLAIN, fontSize);
	}

	/**
	 * �޸�ͼƬ,�����޸ĺ��ͼƬ��������ֻ���һ���ı���
	 */
	public BufferedImage modifyImage(BufferedImage img, Object content, int x,
			int y) {
		try {
			int w = img.getWidth();
			int h = img.getHeight();
			g = img.createGraphics();
			g.setBackground(Color.WHITE);
			g.setColor(Color.orange);// ����������ɫ
			if (this.font != null)
				g.setFont(this.font);
			// ��֤���λ�õ�������ͺ�����
			if (x >= h || y >= w) {
				this.x = h - this.fontsize + 2;
				this.y = w;
			} else {
				this.x = x;
				this.y = y;
			}
			if (content != null) {
				g.drawString(content.toString(), this.x, this.y);
			}
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return img;
	}

	/**
	 * �޸�ͼƬ,�����޸ĺ��ͼƬ���������������ı��Σ� xory��true��ʾ��������һ���������false��ʾ�����ݶ������
	 */
	public BufferedImage modifyImage(BufferedImage img, Object[] contentArr,
			int x, int y, boolean xory) {
		try {
			int w = img.getWidth();
			int h = img.getHeight();
			g = img.createGraphics();
			g.setBackground(Color.WHITE);
			g.setColor(Color.RED);
			if (this.font != null)
				g.setFont(this.font);
			// ��֤���λ�õ�������ͺ�����
			if (x >= h || y >= w) {
				this.x = h - this.fontsize + 2;
				this.y = w;
			} else {
				this.x = x;
				this.y = y;
			}
			if (contentArr != null) {
				int arrlen = contentArr.length;
				if (xory) {
					for (int i = 0; i < arrlen; i++) {
						g.drawString(contentArr[i].toString(), this.x, this.y);
						this.x += contentArr[i].toString().length()
								* this.fontsize / 2 + 5;// ���¼����ı����λ��
					}
				} else {
					for (int i = 0; i < arrlen; i++) {
						g.drawString(contentArr[i].toString(), this.x, this.y);
						this.y += this.fontsize + 2;// ���¼����ı����λ��
					}
				}
			}
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return img;
	}

	/**
	 * �޸�ͼƬ,�����޸ĺ��ͼƬ��������ֻ���һ���ı���
	 * 
	 * ʱ��:2007-10-8
	 * 
	 * @param img
	 * @return
	 */
	public BufferedImage modifyImageYe(BufferedImage img) {

		try {
			int w = img.getWidth();
			int h = img.getHeight();
			g = img.createGraphics();
			g.setBackground(Color.WHITE);
			g.setColor(Color.blue);// ����������ɫ
			if (this.font != null)
				g.setFont(this.font);
			g.drawString("www.hi.baidu.com?xia_mingjian", w - 85, h - 5);
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return img;
	}

	public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {

		try {
			int w = b.getWidth();
			int h = b.getHeight();

			g = d.createGraphics();
			g.drawImage(b, 100, 10, w, h, null);
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return d;
	}

	public static void main(String[] args) {

		PicCombine pc = new PicCombine();

		BufferedImage d = pc.loadImageLocal("F:\\cwb\\imgs\\ˮӡ\\2.jpg");
		BufferedImage b = pc.loadImageLocal("F:\\cwb\\imgs\\ˮӡ\\���ս.png");
		//tt.writeImageLocal("F:\\cwb\\imgs\\ˮӡ\\cc.jpg", tt.modifyImage(b, "��ԭ", 90, 90));
		// ��ͼƬ��д�ļ�

		pc.writeImageLocal("D:\\cc.jpg", pc.modifyImagetogeter(b, d));
		// ������ͼƬ����һ��
		
		//tt.translateImg();
		System.out.println("success");
	}

	private void translateImg(){
		try {
            BufferedImage imageBiao = ImageIO.read(new FileInputStream("f:\\1.jpg"));
            ImageFilter imgf = new MyFilter(Color.WHITE.getRGB()); //��ɫ
            FilteredImageSource fis = new FilteredImageSource(imageBiao.getSource(), imgf);
            Image im = Toolkit.getDefaultToolkit().createImage(fis);
            im.flush();
            BufferedImage newImage = new BufferedImage(imageBiao.getWidth(), imageBiao.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = newImage.createGraphics();
            g.drawImage(im, 0, 0, null);
            g.dispose();
            newImage.flush();
            ImageIO.write(newImage, "jpg", new File("f:\\2.jpg"));
            // ������ԭͼ�ͼ���ͼ����ͼ��
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	class MyFilter extends RGBImageFilter {// ������RGBImageFilter��ImageFilter�����࣬
	    // �̳���ʵ��ͼ��ARGB�Ĵ���
	    int rgb = 0;

	    public MyFilter(int rgb) {// ������������������Ҫ����ͼ��ĳߴ磬�Լ�͸����
	        this.canFilterIndexColorModel = true;
	        this.rgb = rgb;
	    }
	    DirectColorModel dcm = (DirectColorModel) ColorModel.getRGBdefault();

	    public int filterRGB(int x, int y, int rgb) {
	        int alp = dcm.getAlpha(rgb);
	        if (dcm.getRGB(rgb) == dcm.getRGB(this.rgb)/* || alp == 0*/) {// �������Ϊ��ɫ��������͸��
	            alp = 0;
//	            return Color.black.getRGB();//���ԣ�͸���ı�ɺ�ɫ
	        }
	        return alp << 24 | dcm.getRGB(rgb);// ���б�׼ARGB�����ʵ��ͼ�����
	    }
	    /*//���������ټ�
	    public int filterRGB(int x, int y, int rgb) {
	    if ((this.rgb & 0xffffff) == (rgb & 0xffffff)) {
	    return 0;
	    }
	    return rgb;
	    }
	     */
	}


}
