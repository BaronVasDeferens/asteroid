import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Sprite {

    public BufferedImage image;
    public int x, y;

    public abstract void update(Graphics g);

}


