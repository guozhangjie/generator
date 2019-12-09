package com.max.framework.util.barcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.max.framework.core.exception.SystemException;

/**
 * 条形码工具类
 * @author 马青松
 */
public class BarcodeUtil {
    /**
     * 生成条形码字节
     * @param msg 条形码内容
     * @return 条形码二进制数组
     */
    public static byte[] generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous);
        return ous.toByteArray();
    }

    /**
     * 生成条形码到流
     * @param msg 条形码内容
     * @param ous 输出流
     */
    public static void generate(String msg, OutputStream ous) {
        if (StringUtils.isEmpty(msg) || ous == null) {
            throw new SystemException("msg is empty");
        }

        // 使用Code39编码
        Code39Bean bean = new Code39Bean();

        // 精细度
        final int dpi = 150;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(1.0f / dpi);

        // 配置对象
        bean.setModuleWidth(moduleWidth);
        bean.setWideFactor(3);
        bean.doQuietZone(false);

        String format = "image/png";
        try {
            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            // 生成条形码
            bean.generateBarcode(canvas, msg);
            // 结束绘制
            canvas.finish();
        } catch (IOException ex) {
            throw new SystemException(ex.getMessage());
        }
    }
}
