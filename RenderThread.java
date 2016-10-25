import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

/**
 * Created by skot on 10/15/16.
 */
public class RenderThread extends Thread {

    List<Sprite> sprites;
    private boolean continueRendering = true;
    Canvas canvas;
    private BufferStrategy buffer;

    GraphicsEnvironment ge;
    GraphicsDevice gd;
    GraphicsConfiguration gc;

    public RenderThread(List<Sprite> sprites, Canvas canvas, BufferStrategy buffer) {
        this.sprites = sprites;
        this.canvas = canvas;
        this.buffer = buffer;

        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gd = ge.getDefaultScreenDevice();
        gc = gd.getDefaultConfiguration();

        for (GraphicsConfiguration gcfg: gd.getConfigurations())  {
            if (gcfg.getBufferCapabilities().isPageFlipping() && gcfg.isTranslucencyCapable())
                gc = gcfg;
        }
    }

    public void run() {

        while (continueRendering) {

            Graphics g = buffer.getDrawGraphics();

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 600, 600);

            for (Sprite spr : sprites) {
                spr.update(g);
                g.drawImage(spr.image, spr.x, spr.y, null);
            }

            if (!buffer.contentsLost()) {
                buffer.show();
                Toolkit.getDefaultToolkit().sync();
            }

            g.dispose();
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void quit() {
        continueRendering = false;
    }

}
