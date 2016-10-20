import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;


public class Asteroid implements KeyListener {

    Canvas dp;
    JFrame jf;
    
    public static final int PANEL_WIDTH = 600;
    public static final int PANEL_HEIGHT = 600;

    RenderThread renderer;
    public ArrayList<Sprite> sprites  = new ArrayList<>();

    public static void main (String ... args) {
        Asteroid asteroid = new Asteroid();
        asteroid.start();
    }
    
    private void start() {

        BufferedImage asteroidPic = loadImage("bob.png");

        for (int i = 0; i < 20; i++) {
            sprites.add(new Sprite(asteroidPic, PANEL_WIDTH, PANEL_HEIGHT));
        }

        dp = new Canvas();
        dp.setBackground(Color.BLACK);
        dp.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        dp.setIgnoreRepaint(true);


        jf = new JFrame();
        jf.addKeyListener(this);
        jf.requestFocus();
        jf.setBackground(Color.BLACK);
        jf.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        jf.setPreferredSize(new Dimension(600, 600));
        jf.add(dp);
        jf.pack();
        jf.setVisible(true);

        dp.createBufferStrategy(2);
        BufferStrategy buffer = dp.getBufferStrategy();

        renderer = new RenderThread(sprites, dp, buffer);
        renderer.setPriority(Thread.MAX_PRIORITY);
        renderer.start();
    }

    private BufferedImage loadImage(String imageName) {

        BufferedImage loaded = null;
        try (InputStream fin = getClass().getResourceAsStream("images/" + imageName)) {
            loaded = ImageIO.read(fin);
            fin.close();
            System.out.println("loaded " + imageName + ": " + loaded.getWidth() + "x" + loaded.getHeight());
            return loaded;
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        }
    }

    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                renderer.quit();
                jf.dispose();
                System.exit(0);
                break;
            case KeyEvent.VK_UP:
                renderer.sleepDuration++;
                System.out.println("sleep : " + renderer.sleepDuration);
                break;
            case KeyEvent.VK_DOWN:
                if (renderer.sleepDuration > 1)
                    renderer.sleepDuration--;
                System.out.println("sleep : " + renderer.sleepDuration);
                break;
            default:
                break;
        }


    }

    public void keyReleased(KeyEvent e) {    }

    public void keyTyped(KeyEvent e) {    }


}
