import sun.awt.image.BufferedImageDevice;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by skot on 10/28/16.
 */
public class Ship extends Sprite {

    BufferedImage originalImage;

    int angle = 90;
    int thrust = 0;
    boolean requiresUpdate = true;

    public Ship(BufferedImage image) {

        int newSize = (int)(image.getHeight() * 1.412);

        originalImage = new BufferedImage(newSize, newSize, BufferedImage.TYPE_INT_ARGB);
        Graphics g = originalImage.createGraphics();
        g.drawImage(image, newSize / 2 - image.getWidth()/2, newSize/2 - image.getHeight()/2 , null);
        g.dispose();
    }

    public void thrust (int amount) {
        y = y-5;
    }

    public void rotateLeft () {
        angle -= 5;
        requiresUpdate = true;
    }

    public void rotateRight () {
        angle += 5;
        requiresUpdate = true;
    }

    public void update(Graphics graphics) {

        if (requiresUpdate) {
            BufferedImage temp = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = temp.createGraphics();
            AffineTransform at = g.getTransform();
            at.rotate(Math.toRadians(angle), originalImage.getWidth() / 2, originalImage.getHeight() / 2);
            g.setTransform(at);
            g.drawImage(originalImage,0,0,null);
            image = temp;

            g.dispose();

        }


    }
}
