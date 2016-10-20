import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by skot on 10/15/16.
 */
public class DrawPanel extends JPanel { //implements Runnable {

    BufferedImage drawMe = null;

    public DrawPanel() {
        super();

    }
    private boolean continueDrawing = true;

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if (drawMe != null)
            g.drawImage(drawMe,0,0,null);
    }

    public void quit() {
        continueDrawing = false;
    }
}
