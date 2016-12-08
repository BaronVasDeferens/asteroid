import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;


public class Asteroid implements KeyListener, WindowListener {

    Canvas canvas;
    JFrame jf;
    
    public static int PANEL_WIDTH = 600;
    public static int PANEL_HEIGHT = 600;

    RenderThread renderer;
    public ArrayList<Sprite> sprites  = new ArrayList<>();

    Ship ship;

    public static void main (String ... args) {
        Asteroid asteroid = new Asteroid();
        asteroid.start();
    }
    
    private void start() {

        jf = new JFrame();
        goFullscreen(jf);
        jf.addKeyListener(this);
        jf.requestFocus();
        jf.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        jf.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        canvas = new Canvas();
        canvas.setBackground(Color.BLACK);
        canvas.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        canvas.setIgnoreRepaint(true);

        jf.add(canvas);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.addWindowListener(this);
        jf.pack();
        jf.setVisible(true);

        BufferedImage image = loadImage("bob.png");
        for (int i = 0; i < 100; i++) {
            sprites.add(new Tumbler(image, PANEL_WIDTH, PANEL_HEIGHT));
        }

        ship = new Ship(loadImage("ship01.png"));
        ship.x = PANEL_WIDTH / 2;
        ship.y = PANEL_HEIGHT / 2;
        sprites.add(ship);

        canvas.createBufferStrategy(2);
        BufferStrategy buffer = canvas.getBufferStrategy();
        renderer = new RenderThread(sprites, canvas, buffer);
        renderer.setPriority(Thread.MAX_PRIORITY);
        renderer.start();
    }

    private void goFullscreen(JFrame frame) {


        for (GraphicsDevice graphicsDev: java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {

            System.out.println(graphicsDev.toString());
            System.out.println(graphicsDev.getDisplayMode().getWidth() + "x" + graphicsDev.getDisplayMode().getHeight());
            System.out.println("bit depth: " + graphicsDev.getDisplayMode().getBitDepth());
            System.out.println("refresh: " + graphicsDev.getDisplayMode().getRefreshRate());
            System.out.println("configs: " + Arrays.toString(graphicsDev.getConfigurations()));
            System.out.println("memory avail: " + graphicsDev.getAvailableAcceleratedMemory());


            if (graphicsDev.isFullScreenSupported()) {
                System.out.println("Fullscreen: yes");
                graphicsDev.setFullScreenWindow(frame);
                PANEL_WIDTH = graphicsDev.getDisplayMode().getWidth();
                PANEL_HEIGHT = graphicsDev.getDisplayMode().getHeight();
                return;
            }
        }
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
            case KeyEvent.VK_W:
                ship.thrust(1);
                System.out.println("U");
                break;
            case KeyEvent.VK_DOWN:
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                ship.rotateRight();
                System.out.println("R");
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                ship.rotateLeft();
                System.out.println("L");
                break;
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {    }

    public void keyTyped(KeyEvent e) {    }

    @Override
    public void windowIconified (WindowEvent we) { }

    @Override
    public void windowDeiconified(WindowEvent e) {    }

    @Override
    public void windowOpened (WindowEvent we) { }

    @Override
    public void windowClosing(WindowEvent e) {
        renderer.quit();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        renderer.quit();
    }

    @Override
    public void windowActivated (WindowEvent we) { }

    @Override
    public void windowDeactivated(WindowEvent e) {  }

}
