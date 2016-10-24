import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Sprite {

    public BufferedImage image;
    public int x, y;

    public abstract void update();

}


