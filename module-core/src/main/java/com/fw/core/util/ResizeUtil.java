package com.fw.core.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class ResizeUtil {

    private BufferedImage buffer;

    public ResizeUtil(File file) throws IOException {
        buffer = ImageIO.read(file);
    }

    public ResizeUtil(URL url) throws IOException {
        buffer = ImageIO.read(url);
    }

    public ResizeUtil(InputStream stream) throws IOException {
        buffer = ImageIO.read(stream);
    }

    public ResizeUtil(BufferedImage buffer) {
        this.buffer = buffer;
    }

    public int getWidth() {
        return buffer.getWidth();
    }

    public int getHeight() {
        return buffer.getHeight();
    }

    public ResizeUtil resize(int width, int height) {
        BufferedImage dest = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_RGB);
        Graphics2D g = dest.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(buffer, 0, 0, width, height, null);
        g.dispose();
        return new ResizeUtil(dest);
    }

    public ResizeUtil resize(int width) {
        int resizedHeight = (width * buffer.getHeight()) / buffer.getWidth();
        return resize(width, resizedHeight);
    }

    public ResizeUtil crop(int x, int y, int width, int height) {
        BufferedImage dest = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_RGB);
        Graphics2D g = dest.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(buffer, 0, 0, width, height, x, y, x + width, y + height,
            null);
        g.dispose();
        return new ResizeUtil(dest);
    }

    public void writeTo(OutputStream stream, String formatName) throws IOException {
        ImageIO.write(buffer, formatName, stream);
    }

    public boolean isSuppoprtedImageFormat() {
        return buffer != null;
    }

}
