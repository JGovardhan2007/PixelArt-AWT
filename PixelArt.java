import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class PixelArt extends Frame {
    private Panel gridPanel;
    private int gridSize = 16; // Default resolution
    private Color selectedColor = Color.BLACK;

    public PixelArt() {
        setTitle("AWT Pixel Art Pro");
        setLayout(new BorderLayout());

        // --- 1. TOP MENU (Resolution) ---
        Panel topPanel = new Panel();
        topPanel.add(new Label("Resolution:"));
        Choice resChoice = new Choice();
        resChoice.add("10x10");
        resChoice.add("16x16");
        resChoice.add("32x32");
        resChoice.select("16x16");
        resChoice.addItemListener(e -> {
            String selected = resChoice.getSelectedItem();
            gridSize = Integer.parseInt(selected.split("x")[0]);
            createGrid();
        });
        topPanel.add(resChoice);
        add(topPanel, BorderLayout.NORTH);

        // --- 2. THE GRID ---
        gridPanel = new Panel();
        createGrid();
        add(gridPanel, BorderLayout.CENTER);

        // --- 3. BOTTOM CONTROLS (Colors & Actions) ---
        Panel bottomPanel = new Panel(new GridLayout(2, 1));
        
        // Color Palette Row
        Panel colorPalette = new Panel();
        Color[] colors = {Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.WHITE};
        for (Color c : colors) {
            Button cb = new Button();
            cb.setBackground(c);
            cb.setPreferredSize(new Dimension(30, 30));
            cb.addActionListener(e -> selectedColor = c);
            colorPalette.add(cb);
        }
        
        // Action Buttons Row
        Panel actions = new Panel();
        Button clearBtn = new Button("Clear");
        clearBtn.addActionListener(e -> createGrid());
        
        Button saveBtn = new Button("Save as PNG");
        saveBtn.addActionListener(e -> saveImage());

        actions.add(clearBtn);
        actions.add(saveBtn);
        
        bottomPanel.add(colorPalette);
        bottomPanel.add(actions);
        add(bottomPanel, BorderLayout.SOUTH);

        // Window Setup
        setSize(600, 700);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }

    private void createGrid() {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(gridSize, gridSize, 1, 1));
        gridPanel.setBackground(Color.LIGHT_GRAY);

        for (int i = 0; i < gridSize * gridSize; i++) {
            Panel p = new Panel();
            p.setBackground(Color.WHITE);
            
            MouseAdapter ma = new MouseAdapter() {
                public void mousePressed(MouseEvent e) { p.setBackground(selectedColor); }
                public void mouseEntered(MouseEvent e) {
                    if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
                        p.setBackground(selectedColor);
                    }
                }
            };
            p.addMouseListener(ma);
            p.addMouseMotionListener(ma);
            gridPanel.add(p);
        }
        gridPanel.validate(); // Refresh layout
    }

    private void saveImage() {
        BufferedImage img = new BufferedImage(gridPanel.getWidth(), gridPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        gridPanel.paintAll(g);
        g.dispose();

        FileDialog fd = new FileDialog(this, "Save Art", FileDialog.SAVE);
        fd.setFile("my_art.png");
        fd.setVisible(true);
        if (fd.getFile() != null) {
            try {
                File file = new File(fd.getDirectory(), fd.getFile());
                ImageIO.write(img, "png", file);
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    public static void main(String[] args) { new PixelArt(); }
}
