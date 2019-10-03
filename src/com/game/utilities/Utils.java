package com.game.utilities;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {

    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int TOP = 3;
    public static final int BOTTOM = 4;

    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line + "\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public static void rotateImage(Graphics graphics, BufferedImage image, int x, int y, int degrees) {
        AffineTransform transform = AffineTransform.getTranslateInstance(100, 100);
        transform.rotate(Math.toRadians(degrees), image.getWidth()/2, image.getHeight()/2);
        BufferedImage image1 = new BufferedImage(image.getHeight(), image.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.drawImage(image1, transform, null);
    }




}
