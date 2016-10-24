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


public class Asteroid implements KeyListener, WindowListener {

    Canvas canvas;
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

        BufferedImage asteroicanvasic = loadImage("ship01.png");

        for (int i = 0; i < 35; i++) {
            sprites.add(new Tumbler(asteroicanvasic, PANEL_WIDTH, PANEL_HEIGHT));
        }

        canvas = new Canvas();
        canvas.setBackground(Color.BLACK);
        canvas.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        canvas.setIgnoreRepaint(true);

        jf = new JFrame();
        jf.addKeyListener(this);
        jf.requestFocus();
        jf.setBackground(Color.BLACK);
        jf.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        jf.setPreferredSize(new Dimension(600, 600));
        jf.add(canvas);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.addWindowListener(this);

        jf.pack();
        jf.setVisible(true);

        canvas.createBufferStrategy(2);
        BufferStrategy buffer = canvas.getBufferStrategy();

        renderer = new RenderThread(sprites, canvas, buffer);
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

                break;
            case KeyEvent.VK_DOWN:
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
