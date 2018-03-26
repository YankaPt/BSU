/**
 * Created by jan on 14.12.17.
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Lab7 extends JFrame {
    private class PaintLabel extends JLabel{
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
    }

    private JPanel drawPanel;
    private JScrollPane scrollPane;
    private PaintLabel label;
    private Graphics2D graphics;
    private BufferedImage image;
    private Point prevPoint;
    private Point currPoint;

    private JPanel savePanel;
    private JButton save;
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

    public Lab7() {
        super("Paint");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLayout(new BorderLayout());
        setBounds(500, 200, 500, 500);

        image = new BufferedImage(2000, 1000, BufferedImage.TYPE_INT_RGB);
        prevPoint = new Point();
        currPoint = new Point();

        drawPanel = new JPanel(new BorderLayout());
        drawPanel.setPreferredSize(new Dimension(1000, 1000));
        graphics = (Graphics2D)image.getGraphics();
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

        savePanel = new JPanel();
        save = new JButton("Save");
        open = new JButton("Open");
        withBackground = new JRadioButton("With background");
        withoutBackground = new JRadioButton("Without background");
        backgroundGroup = new ButtonGroup();
        backgroundGroup.add(withBackground);
        backgroundGroup.add(withoutBackground);
        withBackground.setSelected(true);
        savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.LINE_AXIS));
        save.setBounds(50, 50, 80, 20);
        open.setBounds(50, 50, 80, 20);
        savePanel.add(withBackground);
        savePanel.add(Box.createHorizontalGlue());
        savePanel.add(save);
        savePanel.add(Box.createHorizontalGlue());
        savePanel.add(withoutBackground);
        savePanel.setBounds(50, 50, 80, 20);
        savePanel.setVisible(true);
        this.add(savePanel, BorderLayout.SOUTH);

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

        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                currPoint = e.getPoint();
                updateImage();
            }
        });

        drawPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                prevPoint = currPoint;
                currPoint = e.getPoint();
                updateImage();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                prevPoint = e.getPoint();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showDialog(null, "Save") == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                        BufferedImage image;
                        if (withBackground.isSelected()) {
                            image = (BufferedImage) label.createImage(label.getWidth(), label.getHeight());
                        } else {
                            image = new BufferedImage(label.getWidth(), label.getHeight(), BufferedImage.TYPE_INT_RGB);
                        }
                        Graphics2D g2d = image.createGraphics();
                        label.printAll(g2d);
                        g2d.dispose();
                        ImageIO.write(image, "jpeg", file);
                    } catch (FileNotFoundException fnfe) {
                        JOptionPane.showMessageDialog(null, "File not found");
                    } catch (IOException ioe) {
                        JOptionPane.showMessageDialog(null, "Writing error");
                    }
                }
            }
        });

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showDialog(null, "Open") == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                        image = ImageIO.read(file);
                        ImageIcon imageIcon = new ImageIcon(image);
                        Graphics2D g = (Graphics2D)label.getGraphics();
                        label.setIcon(imageIcon);
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "File not found");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Opening error");
                    }
                }
            }
        });
    }

    public void updateImage(){
        Graphics g = image.createGraphics();
        g.setColor(color);
        g.drawLine(prevPoint.x, prevPoint.y, currPoint.x, currPoint.y);
        //g.dispose();
        repaint();
    }

    public static void main(String[] args){
        Lab7 lab7 = new Lab7();
        lab7.setVisible(true);
    }
}
