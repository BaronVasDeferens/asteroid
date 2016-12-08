import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

/**
 * Created by skot on 10/15/16.
 */
public class RenderThread extends Thread {

    private int SLEEP_DURATION = 25;

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
            g.fillRect(0, 0, Asteroid.PANEL_WIDTH, Asteroid.PANEL_HEIGHT);

            for (Sprite spr : sprites) {
                spr.update(g);
            }

            if (!buffer.contentsLost()) {
                buffer.show();
                Toolkit.getDefaultToolkit().sync();
            }

            g.dispose();
            try {
                Thread.sleep(SLEEP_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void quit() {
        continueRendering = false;
    }

}
