import sun.awt.image.BufferedImageDevice;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Sprite {
    final int MAX_X, MAX_Y;
    public int x, y;
    int directionX, directionY;
    int angle = 0;
    BufferedImage image;
    BufferedImage originalImage;
    float scale = 1.0f;
    Random rando;

    public Sprite(BufferedImage image, final int MAX_X, final int MAX_Y) {

        this.MAX_X = MAX_X;
        this.MAX_Y = MAX_Y;

        rando = new Random();
        x = rando.nextInt(MAX_X);
        y = rando.nextInt(MAX_Y);
        scale = rando.nextFloat() + .15f;

        angle = rando.nextInt(360);

        BufferedImage scaledImage = new BufferedImage(
                (int)(image.getWidth() * scale),
                (int)(image.getHeight() * scale),
                BufferedImage.TYPE_INT_ARGB
        );


        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(image, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);
        //g.dispose();

        this.originalImage = scaledImage;

        BufferedImage rotatedImage = new BufferedImage(scaledImage.getWidth(), scaledImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        g = rotatedImage.createGraphics();
        AffineTransform at = g.getTransform();
        at.rotate(Math.toRadians(angle), scaledImage.getWidth()/2, scaledImage.getHeight()/2);
        g.setTransform(at);
        g.drawImage(scaledImage, 0,0, scaledImage.getWidth(), scaledImage.getHeight(), null);

        g.dispose();

        this.image = rotatedImage;

        while (directionX == 0) {
            directionX = rando.nextInt(3) - 1;
        }
        while (directionY == 0) {
            directionY = rando.nextInt(3) - 1;
        }
    }

    public synchronized void update() {
        x += directionX;
        y += directionY;

        rotateImage();

        if ((x > MAX_X) || (x < -MAX_X)) {
            x = rando.nextInt(MAX_X);

            while (directionX == 0)
                directionX = rando.nextInt(3) - 1;
            while (directionY == 0)
                directionY = rando.nextInt(3) - 1;
        }

        if ((y > MAX_Y) || (y < -MAX_Y)) {
            y = rando.nextInt(MAX_Y);

            while (directionX == 0)
                directionX = rando.nextInt(2) - 1;
            while (directionY == 0)
                directionY = rando.nextInt(2) - 1;
        }

    }

    private void rotateImage() {

        angle+=10;
        if (angle > 360)
            angle = 0;

        BufferedImage rotatedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = rotatedImage.createGraphics();

        AffineTransform at = g.getTransform();
        at.rotate(Math.toRadians(angle), originalImage.getWidth()/2, originalImage.getHeight()/2);
        g.setTransform(at);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .50f));
        g.drawImage(originalImage, 0, 0, originalImage.getWidth(), originalImage.getHeight(), null);

        image = rotatedImage;
        g.dispose();
    }
}
