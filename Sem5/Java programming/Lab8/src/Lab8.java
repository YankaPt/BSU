import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

public class Lab8 extends JFrame {
    private class PaintLabel extends JLabel{
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
    }

    private MyComponent drawPanel;
    private JScrollPane scrollPane;
    private PaintLabel label;
    private Graphics2D graphics;
    private BufferedImage image;
    private BufferedImage initialImage;
    private Point initialPoint;
    private Point finalPoint;

    private JButton open;
    private JRadioButton withBackground;
    private JRadioButton withoutBackground;
    private ButtonGroup backgroundGroup;

    private JPanel colorPanel;
    private JRadioButton red;
    private JRadioButton green;
    private JRadioButton blue;
    private ButtonGroup buttonGroup;
    private Color color;

    private JLabel coorLabel;

    public Lab8() {
        super("Paint");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLayout(new BorderLayout());
        setBounds(500, 200, 500, 500);


        initialImage = new BufferedImage(2000, 1000, BufferedImage.TYPE_INT_RGB);
        image = deepCopy(initialImage);
        initialPoint = new Point();
        finalPoint = new Point();

        drawPanel = new MyComponent(new BorderLayout());
        drawPanel.setPreferredSize(new Dimension(1000, 1000));
        graphics = image.createGraphics();
        graphics.setColor(new Color(255, 255, 255));
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        //graphics.dispose();
        scrollPane = new JScrollPane(drawPanel);
        scrollPane.setPreferredSize(this.getPreferredSize());
        label = new PaintLabel();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
        drawPanel.add(label, BorderLayout.CENTER);

        coorLabel = new JLabel();
        this.add(coorLabel, BorderLayout.SOUTH);

        open = new JButton("Open");
        withBackground = new JRadioButton("With background");
        withoutBackground = new JRadioButton("Without background");
        backgroundGroup = new ButtonGroup();
        backgroundGroup.add(withBackground);
        backgroundGroup.add(withoutBackground);
        withBackground.setSelected(true);
        open.setBounds(50, 50, 80, 20);

        colorPanel = new JPanel();
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.LINE_AXIS));
        red = new JRadioButton("Red");
        green = new JRadioButton("Green");
        blue = new JRadioButton("Blue");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(red);
        buttonGroup.add(green);
        buttonGroup.add(blue);
        red.setSelected(true);
        colorPanel.add(open);
        colorPanel.add(Box.createHorizontalGlue());
        colorPanel.add(red);
        colorPanel.add(green);
        colorPanel.add(blue);
        colorPanel.add(Box.createHorizontalGlue());
        this.add(colorPanel, BorderLayout.NORTH);
        color = new Color(255, 0, 0);

        red.addActionListener(e -> color = new Color(255, 0, 0));
        green.addActionListener(e -> color = new Color(0, 255, 0));
        blue.addActionListener(e -> color = new Color(0, 0, 255));

        drawPanel.addMyListener(event -> coorLabel.setText("x: "+ event.getPoint().x + "y: " + event.getPoint().y));
        drawPanel.addMyListener(event -> paintPoint(event.getPoint()));
        open.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showDialog(null, "Open") == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    initialImage = ImageIO.read(file);
                    ImageIcon imageIcon = new ImageIcon(image);
                    Graphics2D g = (Graphics2D)label.getGraphics();
                    updateImage();
                    label.setIcon(imageIcon);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "File not found");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Opening error");
                }
            }
        });
    }

    public void paintPoint(Point point) {
        Graphics g = image.createGraphics();
        Color randColor = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
        g.setColor(randColor);
        g.drawRect(point.x, point.y, 10, 10);
        repaint();
    }

    public void updateImage(){
        image = deepCopy(initialImage);
        Graphics g = image.createGraphics();
        g.setColor(color);
        Point temp1 = new Point(initialPoint);
        Point temp2 = new Point(finalPoint);
        if (temp1.x > temp2.x) {
            temp1.x = temp2.x;
            temp2.x = initialPoint.x;
        }
        if (temp1.y > temp2.y) {
            temp1.y = temp2.y;
            temp2.y = initialPoint.y;
        }
        g.drawRect(temp1.x, temp1.y, temp2.x-temp1.x, temp2.y-temp1.y);
        try {
            invertColors(image.getSubimage(temp1.x, temp1.y, temp2.x - temp1.x, temp2.y - temp1.y));
        } catch (Exception ex) {
            System.out.println("Гы");
        }
        g.dispose();
        repaint();
    }

    public static void main(String[] args){
        Lab8 lab8 = new Lab8();
        lab8.setVisible(true);
    }

    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    private static void invertColors(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgba = image.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                        255 - col.getGreen(),
                        255 - col.getBlue());
                image.setRGB(x, y, col.getRGB());
            }
        }
    }
}
class MyComponent extends JPanel {
    private ArrayList<MyEventListener> listenerList = new ArrayList<>();

    MyComponent(BorderLayout borderLayout) {
        super(borderLayout);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ((e.getModifiersEx()==0 || e.getModifiersEx()==256) && e.getButton()==MouseEvent.BUTTON1) {
                    for (MyEventListener listener : listenerList) {
                        listener.actionPerformed(e);
                    }
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    void addMyListener(MyEventListener myEventListener) {
        //rwww.win2.cn/g9
        // enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        listenerList.add(myEventListener);
    }

    /*@Override
    protected void processEvent(AWTEvent e) {
        if (e instanceof MouseEvent && (((MouseEvent) e).getModifiersEx()==0 || ((MouseEvent) e).getModifiersEx()==256)) {
            if (((MouseEvent) e).getButton()==MouseEvent.BUTTON1)
            for (MyEventListener listener : listenerList) {
                listener.actionPerformed((MouseEvent) e);
            }
        }
        super.processEvent(e);
    }*/
}

interface MyEventListener extends EventListener {
    void actionPerformed(MouseEvent event);
}