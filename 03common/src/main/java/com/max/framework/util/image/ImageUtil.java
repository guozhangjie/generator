package com.max.framework.util.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.max.framework.util.stream.ByteArrayUtil;
//import com.sun.jimi.core.Jimi;
//import com.sun.jimi.core.JimiException;
//import com.sun.jimi.core.JimiWriter;
//import com.sun.jimi.core.options.JPGOptions;

/**
 * 图片工具类
 * @author 马青松
 */
public class ImageUtil {
    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(ImageUtil.class);

    /**
     * 透明度
     */
    private static final float ALPHA = 0.9f;

    /**
     * 图片格式
     */
    private static final String IMG_FORMAT_JPG = "jpg";

    /**
     * 图片加水印
     * @param imageData 原始图片
     * @param logoRealPath logo图片
     * @param logoFileName 文件名
     * @return 加水印图片
     */
    public static byte[] waterMark(byte[] imageData, String logoRealPath, String logoFileName) {
        InputStream ips = ByteArrayUtil.toInputStream(imageData);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Image image = ImageIO.read(ips);

            int width = image.getWidth(null);
            int heigth = image.getHeight(null);

            BufferedImage bufferImage = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics2D = bufferImage.createGraphics();
            graphics2D.drawImage(image, 0, 0, width, heigth, null);

            String logoPath = logoRealPath + File.separator + logoFileName;
            File logoFile = new File(logoPath);

            Image logo = ImageIO.read(logoFile);
            int widthReal = logo.getWidth(null);
            int heigthReal = logo.getHeight(null);

            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));

            int positionX = -width / 2;
            int positionY = -heigth / 2;

            while (positionX < width * 1.5) {
                positionY = -heigth / 2;

                while (positionY < heigth * 1.5) {
                    graphics2D.drawImage(logo, positionX, positionY, null);

                    positionY += heigthReal + 200;
                }

                positionX += widthReal + 200;
            }

            graphics2D.dispose();

            ImageIO.write(bufferImage, IMG_FORMAT_JPG, out);
            return out.toByteArray();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }

        return null;
    }

    /**
     * GIF转JPG
     * @param source GIF路径
     * @param dest JPG路径
     * @param quality 转化质量
     */
    public static void createGifToJpg(String source, String dest, int quality) {
//        if (quality < 0 || quality > 100) {
//            quality = 75;
//        }
//
//        try {
//            JPGOptions options = new JPGOptions();
//            options.setQuality(quality);
//            ImageProducer image = Jimi.getImageProducer(source);
//            JimiWriter writer = Jimi.createJimiWriter(dest);
//            writer.setSource(image);
//            writer.setOptions(options);
//            writer.putImage(dest);
//        } catch (JimiException je) {
//            logger.error(je.getMessage());
//        }
    }

    /**
     * 将图片缩放到指定的高度或者宽度
     * @param imageData 图片源内容
     * @param scale 缩放比例
     * @return 压缩后的图片大小
     */
    public static byte[] scaleImageWithRate(byte[] imageData, double scale) {
        InputStream ips = ByteArrayUtil.toInputStream(imageData);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedImage srcImage = null;
        try {
            srcImage = ImageIO.read(ips);

            int width = srcImage.getWidth();
            int height = srcImage.getHeight();

            width = (int) (width * scale);
            height = (int) (height * scale);

            BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Image newImage = srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            Graphics graphics = targetImage.getGraphics();
            graphics.drawImage(newImage, 0, 0, null);
            graphics.dispose();
            ImageIO.write(targetImage, IMG_FORMAT_JPG, out);
            return out.toByteArray();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }

        return null;
    }

    /**
     * 图片添加水印
     * @param srcImgData 需要添加水印的图片的路径
     * @param waterMarkContent 水印的文字
     * @return 图片
     */
    public static byte[] waterMarkByString(byte[] srcImgData, String waterMarkContent) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            // 读取原图片信息
            InputStream ips = ByteArrayUtil.toInputStream(srcImgData);
            Image srcImg = ImageIO.read(ips);
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D gps = bufImg.createGraphics();
            gps.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            Font font = new Font("宋体", Font.PLAIN, 70);
            gps.setColor(Color.blue); // 根据图片的背景设置水印颜色

            gps.setFont(font);
            int xpoint = srcImgWidth - getWatermarkLength(waterMarkContent, gps) - 10;
            int ypoint = srcImgHeight / 2;
            gps.drawString(waterMarkContent, xpoint, ypoint);
            gps.dispose();
            // 输出图片
            ImageIO.write(bufImg, IMG_FORMAT_JPG, out);
            return out.toByteArray();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }
        return null;
    }

    /**
     * 获取水印文字总长度
     * @param waterMarkContent 水印的文字
     * @param gps gps
     * @return 水印文字总长度
     */
    private static int getWatermarkLength(String waterMarkContent, Graphics2D gps) {
        return gps.getFontMetrics(gps.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }
}
