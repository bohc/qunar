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

	private Font font = new Font("华文彩云", Font.PLAIN, 40);// 添加字体的属性设置

	private Graphics2D g = null;

	private int fontsize = 0;

	private int x = 0;

	private int y = 0;

	/**
	 * 导入本地图片到缓冲区
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
	 * 导入网络图片到缓冲区
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
	 * 生成新图片到本地
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
	 * 设定文字的字体等
	 */
	public void setFont(String fontStyle, int fontSize) {
		this.fontsize = fontSize;
		this.font = new Font(fontStyle, Font.PLAIN, fontSize);
	}

	/**
	 * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
	 */
	public BufferedImage modifyImage(BufferedImage img, Object content, int x,
			int y) {
		try {
			int w = img.getWidth();
			int h = img.getHeight();
			g = img.createGraphics();
			g.setBackground(Color.WHITE);
			g.setColor(Color.orange);// 设置字体颜色
			if (this.font != null)
				g.setFont(this.font);
			// 验证输出位置的纵坐标和横坐标
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
	 * 修改图片,返回修改后的图片缓冲区（输出多个文本段） xory：true表示将内容在一行中输出；false表示将内容多行输出
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
			// 验证输出位置的纵坐标和横坐标
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
								* this.fontsize / 2 + 5;// 重新计算文本输出位置
					}
				} else {
					for (int i = 0; i < arrlen; i++) {
						g.drawString(contentArr[i].toString(), this.x, this.y);
						this.y += this.fontsize + 2;// 重新计算文本输出位置
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
	 * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
	 * 
	 * 时间:2007-10-8
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
			g.setColor(Color.blue);// 设置字体颜色
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

		BufferedImage d = pc.loadImageLocal("F:\\cwb\\imgs\\水印\\2.jpg");
		BufferedImage b = pc.loadImageLocal("F:\\cwb\\imgs\\水印\\大惠战.png");
		//tt.writeImageLocal("F:\\cwb\\imgs\\水印\\cc.jpg", tt.modifyImage(b, "曹原", 90, 90));
		// 往图片上写文件

		pc.writeImageLocal("D:\\cc.jpg", pc.modifyImagetogeter(b, d));
		// 将多张图片合在一起
		
		//tt.translateImg();
		System.out.println("success");
	}

	private void translateImg(){
		try {
            BufferedImage imageBiao = ImageIO.read(new FileInputStream("f:\\1.jpg"));
            ImageFilter imgf = new MyFilter(Color.WHITE.getRGB()); //白色
            FilteredImageSource fis = new FilteredImageSource(imageBiao.getSource(), imgf);
            Image im = Toolkit.getDefaultToolkit().createImage(fis);
            im.flush();
            BufferedImage newImage = new BufferedImage(imageBiao.getWidth(), imageBiao.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = newImage.createGraphics();
            g.drawImage(im, 0, 0, null);
            g.dispose();
            newImage.flush();
            ImageIO.write(newImage, "jpg", new File("f:\\2.jpg"));
            // 把以上原图和加上图标后的图像
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	class MyFilter extends RGBImageFilter {// 抽象类RGBImageFilter是ImageFilter的子类，
	    // 继承它实现图象ARGB的处理
	    int rgb = 0;

	    public MyFilter(int rgb) {// 构造器，用来接收需要过滤图象的尺寸，以及透明度
	        this.canFilterIndexColorModel = true;
	        this.rgb = rgb;
	    }
	    DirectColorModel dcm = (DirectColorModel) ColorModel.getRGBdefault();

	    public int filterRGB(int x, int y, int rgb) {
	        int alp = dcm.getAlpha(rgb);
	        if (dcm.getRGB(rgb) == dcm.getRGB(this.rgb)/* || alp == 0*/) {// 如果像素为白色，则让它透明
	            alp = 0;
//	            return Color.black.getRGB();//测试，透明的变成黑色
	        }
	        return alp << 24 | dcm.getRGB(rgb);// 进行标准ARGB输出以实现图象过滤
	    }
	    /*//这样不是再简单
	    public int filterRGB(int x, int y, int rgb) {
	    if ((this.rgb & 0xffffff) == (rgb & 0xffffff)) {
	    return 0;
	    }
	    return rgb;
	    }
	     */
	}


}
