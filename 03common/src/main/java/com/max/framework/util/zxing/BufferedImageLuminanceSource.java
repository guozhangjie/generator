package com.max.framework.util.zxing;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.google.zxing.LuminanceSource;

/**
 * 二维码辅助类
 * @author 马青松
 */
public class BufferedImageLuminanceSource extends LuminanceSource {
    /**
     * BufferedImage
     */
    private final BufferedImage image;
    /**
     * left
     */
    private final int left;
    /**
     * top
     */
    private final int top;

    /**
     * BufferedImageLuminanceSource
     * @param image image
     */
    public BufferedImageLuminanceSource(BufferedImage image) {
        this(image, 0, 0, image.getWidth(), image.getHeight());
    }

    /**
     * BufferedImageLuminanceSource
     * @param image image
     * @param left left
     * @param top top
     * @param width width
     * @param height height
     */
    public BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width, int height) {
        super(width, height);

        int sourceWidth = image.getWidth();
        int sourceHeight = image.getHeight();
        if (left + width > sourceWidth || top + height > sourceHeight) {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }

        for (int y = top; y < top + height; y++) {
            for (int x = left; x < left + width; x++) {
                if ((image.getRGB(x, y) & 0xFF000000) == 0) {
                    image.setRGB(x, y, 0xFFFFFFFF); // = white
                }
            }
        }

        this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
        this.image.getGraphics().drawImage(image, 0, 0, null);
        this.left = left;
        this.top = top;
    }

    /**
     * {@inheritDoc}
     */
    public byte[] getRow(int yporint, byte[] row) {
        if (yporint < 0 || yporint >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: " + yporint);
        }
        int width = getWidth();
        if (row == null || row.length < width) {
            row = new byte[width];
        }
        image.getRaster().getDataElements(left, top + yporint, width, 1, row);
        return row;
    }

    /**
     * {@inheritDoc}
     */
    public byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        int area = width * height;
        byte[] matrix = new byte[area];
        image.getRaster().getDataElements(left, top, width, height, matrix);
        return matrix;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isCropSupported() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public LuminanceSource crop(int left, int top, int width, int height) {
        return new BufferedImageLuminanceSource(image, this.left + left, this.top + top, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isRotateSupported() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public LuminanceSource rotateCounterClockwise() {
        int sourceWidth = image.getWidth();
        int sourceHeight = image.getHeight();
        AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);
        BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics2D = rotatedImage.createGraphics();
        graphics2D.drawImage(image, transform, null);
        graphics2D.dispose();
        int width = getWidth();
        return new BufferedImageLuminanceSource(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
    }
}
