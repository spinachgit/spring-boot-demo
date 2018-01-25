package com.spinach.example.util.qcode.qrcode;

/**  
 * @作者   Relieved 
 * @创建日期   2014年11月7日 
 * @描述  （利用QrCode生成二维码）  
 * @版本 V 1.0 
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

/** 
 * 二维码生成器 
 * mvn install:install-file -Dfile=../lib/QRCode.jar -DgroupId=net.qrcode -DartifactId=qrcode -Dversion=1.0 -Dpackaging=jar
 * 和：TwoDimensionCode使用的加密和解密是一样的。
 */
public class QRCodeUtil {
	static String content = "http://www.baidu.com";
	static String imgPath = "/users/apple/logs/qcode/spinach_QRCode.png";
	
	/** 
	 * @param args the command line arguments 
	 */
	public static void main(String[] args) {
		/**
		 * 加密：生成二维码
		 */
		QRCodeUtil handler = new QRCodeUtil();
		handler.encoderQRCode(content, imgPath, 8);
		System.out.println("encoder QRcode success");
		
		/**
		 * 解码:解析二维码
		 */
        String imgPath = QRCodeUtil.imgPath;  
        String decoderContent = handler.decoderQRCode(imgPath);  
        System.out.println("解析结果如下：");  
        System.out.println(decoderContent);  
        System.out.println("========decoder success!!!");  
	}
	
	/** 
	 * 生成二维码(QRCode)图片 
	 * @param content 
	 * @param imgPath 
	 */
	public void encoderQRCode(String content, String imgPath, int version) {
		try {
			Qrcode qrcodeHandler = new Qrcode();
			//设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小    
			qrcodeHandler.setQrcodeErrorCorrect('M');
			//N代表数字,A代表字符a-Z,B代表其他字符  
			qrcodeHandler.setQrcodeEncodeMode('B');
			//版本1为21*21矩阵，版本每增1，二维码的两个边长都增4；所以版本7为45*45的矩阵；最高版本为是40，是177*177的矩阵  
			qrcodeHandler.setQrcodeVersion(version);
			int imgSize = 67 + 12 * (version - 1);
			System.out.println(content);
			byte[] contentBytes = content.getBytes("gb2312");

			BufferedImage bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();

			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);

			// 设定图像颜色 > BLACK  
			gs.setColor(Color.BLACK);

			// 设置偏移量 不设置可能导致解析出错  
			int pixoff = 2;
			// 输出内容 > 二维码  
			System.out.println(contentBytes.length);
			if (contentBytes.length > 0 && contentBytes.length < 130) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,130 ]. ");
			}

			gs.dispose();
			bufImg.flush();

			File imgFile = new File(imgPath);

			// 生成二维码QRCode图片  
			ImageIO.write(bufImg, "png", imgFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
     * 解码二维码 
     * @param imgPath 
     * @return String 
     */  
    public String decoderQRCode(String imgPath) {  
  
        // QRCode 二维码图片的文件  
        File imageFile = new File(imgPath);  
  
        BufferedImage bufImg = null;  
        String decodedData = null;  
        try {  
            bufImg = ImageIO.read(imageFile);  
  
            QRCodeDecoder decoder = new QRCodeDecoder();  
            decodedData = new String(decoder.decode(new J2SEImage(bufImg)),"gb2312");  
  
        } catch (IOException e) {  
            System.out.println("Error: " + e.getMessage());  
            e.printStackTrace();  
        } catch (DecodingFailedException dfe) {  
            System.out.println("Error: " + dfe.getMessage());  
            dfe.printStackTrace();  
        }  
        return decodedData;  
    }  
    
    /**
     * 解码用到的图片
     * @date:2018年1月25日下午3:48:52
     */
	class J2SEImage implements QRCodeImage {  
        BufferedImage bufImg;  
  
        public J2SEImage(BufferedImage bufImg) {  
            this.bufImg = bufImg;  
        }  
  
        public int getWidth() {  
            return bufImg.getWidth();  
        }  
  
        public int getHeight() {  
            return bufImg.getHeight();  
        }  
  
        public int getPixel(int x, int y) {  
            return bufImg.getRGB(x, y);  
        }  
  
    } 
}