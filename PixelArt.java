import java.awt.*;
import java.awt.event.*;

public class PixelArt extends Frame {
    public PixelArt() {
        setLayout(new GridLayout(10, 10)); // 10x10 Grid
        for (int i = 0; i < 100; i++) {
            Panel p = new Panel();
            p.setBackground(Color.WHITE);
            p.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    // Cycle colors on click
                    if (p.getBackground() == Color.WHITE) p.setBackground(Color.BLACK);
                    else p.setBackground(Color.WHITE);
                }
            });
            add(p);
        }

        setSize(400, 400);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }
    public static void main(String[] args) { new PixelArt(); }
}
