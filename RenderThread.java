import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by skot on 10/15/16.
 */
public class RenderThread extends Thread {

    public static int sleepDuration = 1;
    List<Sprite> sprites;
    private boolean continueRendering = true;
    Canvas dp;
    private BufferStrategy buffer;


    public RenderThread(List<Sprite> sprites, Canvas dp, BufferStrategy buffer) {
        this.sprites = sprites;
        this.dp = dp;
        this.buffer = buffer;
    }

    public void run() {


        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();


        while (continueRendering) {

            Toolkit.getDefaultToolkit().sync();

            BufferedImage renderedImage = gc.createCompatibleImage(600, 600);
            Graphics g = buffer.getDrawGraphics();

//            g.setColor(Color.BLACK);
//            g.fillRect(0, 0, 600, 600);

            for (Sprite spr : sprites) {
                spr.update();
                g.drawImage(spr.image, spr.x, spr.y, null);
            }

            if (!buffer.contentsLost())
                buffer.show();

            try {
                Thread.sleep(sleepDuration);
            } catch (InterruptedException ie) {
                System.out.println(ie.toString());
            } finally {
                g.dispose();
            }
        }
    }

    public synchronized void quit() {
        continueRendering = false;
    }

}
