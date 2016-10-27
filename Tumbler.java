import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Tumbler extends Sprite {
    final int MAX_X, MAX_Y;

    int directionX, directionY;
    int angle = 0;
    int rotationSpeed = 1;
    BufferedImage originalImage;
    float scale = 1.0f;
    Random rando;

    public Tumbler (BufferedImage image, final int MAX_X, final int MAX_Y) {

        this.MAX_X = MAX_X;
        this.MAX_Y = MAX_Y;

        rando = new Random();
        x = rando.nextInt(MAX_X);
        y = rando.nextInt(MAX_Y);
        scale = rando.nextFloat() + .15f;

        angle = rando.nextInt(360);
        rotationSpeed += rando.nextInt(5) - 3;

        BufferedImage scaledImage = new BufferedImage(
                (int) (image.getWidth() * scale),
                (int) (image.getHeight() * scale),
                BufferedImage.TYPE_INT_ARGB
        );


        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(image, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);


        this.originalImage = scaledImage;

        BufferedImage rotatedImage = new BufferedImage(scaledImage.getWidth(), scaledImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        g = rotatedImage.createGraphics();
        AffineTransform at = g.getTransform();
        at.rotate(Math.toRadians(angle), scaledImage.getWidth() / 2, scaledImage.getHeight() / 2);
        g.setTransform(at);
        g.drawImage(scaledImage, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);

        g.dispose();
        this.image = rotatedImage;
        rotatedImage.flush();

        while (directionX == 0) {
            directionX = rando.nextInt(3) - 1;
        }
        while (directionY == 0) {
            directionY = rando.nextInt(3) - 1;
        }
    }

    public synchronized void update(Graphics g) {
        x += directionX;
        y += directionY;

        rotateImage(g);

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
                directionX = rando.nextInt(3) - 1;
            while (directionY == 0)
                directionY = rando.nextInt(3) - 1;
        }
    }

    private void rotateImage(Graphics graphics) {

        angle += rotationSpeed;
        if (angle > 360)
            angle = 0;
        else if (angle < 0)
            angle = 360;

        graphics.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);

    }
}
