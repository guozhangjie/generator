package com.max.framework.util.zxing;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具类
 * @author 马青松
 */
public class QrCodeUtil {
    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(QrCodeUtil.class);

    /**
     * 二维码编码格式
     */
    private static final String CHARSET = "utf-8";
    /**
     * 图片类型
     */
    private static final String FORMAT_NAME = "JPG";
    /**
     * 二维码尺寸
     */
    private static final int QRCODE_SIZE = 300;
    /**
     * LOGO宽度
     */
    private static final int WIDTH = 60;
    /**
     * LOGO高度
     */
    private static final int HEIGHT = 60;

    /**
     * createImage
     * @param content content
     * @param logoImgPath logo图片路径
     * @param needCompress needCompress
     * @return BufferedImage
     * @throws WriterException WriterException
     * @throws IOException IOException
     */
    private static BufferedImage createImage(String content, String logoImgPath, boolean needCompress) throws WriterException, IOException {
        @SuppressWarnings("rawtypes")
        Hashtable<EncodeHintType, Comparable> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoImgPath == null || "".equals(logoImgPath)) {
            return image;
        }
        // 插入图片
        QrCodeUtil.insertImage(image, logoImgPath, needCompress);
        return image;
    }

    /**
     * 插入logo
     * @param source source
     * @param logoImgPath logoImgPath
     * @param needCompress needCompress
     * @throws IOException IOException
     */
    private static void insertImage(BufferedImage source, String logoImgPath, boolean needCompress) throws IOException {
        File file = new File(logoImgPath);
        if (!file.exists()) {
            return;
        }
        Image src = ImageIO.read(new File(logoImgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = tag.getGraphics();
            graphics.drawImage(image, 0, 0, null); // 绘制缩小后的图
            graphics.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int xpoint = (QRCODE_SIZE - width) / 2;
        int ypoint = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, xpoint, ypoint, width, height, null);
        Shape shape = new RoundRectangle2D.Float(xpoint, ypoint, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码信息带logo
     * @param content 二位码内容
     * @param logoImgPath logo图片路径
     * @param needCompress 是否压缩
     * @return 二维码
     */
    public static byte[] encode(String content, String logoImgPath, boolean needCompress) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            BufferedImage image = QrCodeUtil.createImage(content, logoImgPath, needCompress);
            ImageIO.write(image, FORMAT_NAME, out);
            return out.toByteArray();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } catch (WriterException ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    /**
     * 生成二维码信息不带logo
     * @param content 二维码内容
     * @param needCompress 是否压缩
     * @return 二维码
     */
    public static byte[] encode(String content, boolean needCompress) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            BufferedImage image = QrCodeUtil.createImage(content, null, needCompress);
            ImageIO.write(image, FORMAT_NAME, out);
            return out.toByteArray();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } catch (WriterException ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }
    
    public static void main(String[] args) {
        byte[] imgs = encode("http://www.baidu.com", false);
        File file = new File("D://test.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(imgs);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析二维码内容
     * @param file file
     * @return 二维码内容
     */
    public static String decode(File file) {
        BufferedImage image;
        try {
            image = ImageIO.read(file);
            if (image == null) {
                return null;
            }
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result;
            Hashtable<DecodeHintType, String> hints = new Hashtable<>();
            hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
            result = new MultiFormatReader().decode(bitmap, hints);
            String resultStr = result.getText();
            return resultStr;
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } catch (NotFoundException ex) {
            logger.error(ex.getMessage());
        }
        return "";
    }

    /**
     * 解析二维码内容
     * @param path path
     * @return 二维码内容
     */
    public static String decode(String path) {
        return QrCodeUtil.decode(new File(path));
    }
}