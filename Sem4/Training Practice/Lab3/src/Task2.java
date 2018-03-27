import javax.swing.*;
import java.awt.*;

public class Task2 {
    private int direction;
    private double degree;
    private double speedParam;
    private int sliderValue;
    private int radius;
    private int scale;
    private Image spiral;
    private int spiralWidth, spiralHeight, spiralWidthScaled, spiralHeightScaled;
    public static void main(String[] args) {
        Task2 task2 = new Task2();
        task2.go();
    }
    public void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(600, 600));
        JPanel panel = new JPanel(), controlPanel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BorderLayout());
        JSlider slider = new JSlider(1, 20);
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Clockwise");
        comboBox.addItem("Counterclockwise");
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.LINE_AXIS));
        controlPanel.add(comboBox);
        controlPanel.add(slider);
        ImageIcon someThingIcon = new ImageIcon("Spiral.png");
        spiral = someThingIcon.getImage();
        spiralWidth = someThingIcon.getIconWidth();
        spiralHeight = someThingIcon.getIconHeight();
        panel.add(controlPanel, BorderLayout.NORTH);
        JPanel paintPane = new JPanel() {
            boolean isInit = false;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int w = this.getWidth(), h = this.getHeight();
                int xCenter = w / 2, yCenter = h / 2;
                int newRadius = Math.min(w, h) / 2;
                if (!isInit) {
                    radius = newRadius;
                    isInit = true;
                }
                int newScale = Toolkit.getDefaultToolkit().getScreenSize().height / newRadius;
                double angle = Math.toRadians(degree);
                boolean isScaleChanged = newScale != scale;
                if (isScaleChanged) {
                    speedParam *= (1.0 * radius / newRadius);
                    scale = newScale;
                    radius = newRadius;
                    spiralWidthScaled = spiralWidth / scale;
                    spiralHeightScaled = spiralHeight / scale;
                }
                int x = (int) (xCenter - radius * Math.cos(angle)) - spiralWidthScaled / 2;
                int y = (int) (yCenter - radius * Math.sin(angle)) - spiralHeightScaled / 2;


                g.drawImage(spiral,
                        x, y,
                        spiralWidthScaled, spiralHeightScaled,
                        null);
            }
        };
        paintPane.setBackground(new Color(0, 0, 0));
        panel.add(paintPane, BorderLayout.CENTER);
        scale = 0;
        direction = 1;
        speedParam = 1.5;
        sliderValue = 10;
        slider.addChangeListener(e -> {
            speedParam *= 1.0 * slider.getValue() / sliderValue;
            sliderValue = slider.getValue();
        });
        comboBox.addActionListener(e -> {
            direction = (comboBox.getSelectedIndex() == 0) ? 1 : -1;
        });
        Timer timer = new Timer(10, e -> {
            degree += speedParam * direction;
            degree %= 360;
            paintPane.repaint();
        });
        timer.start();
    }

}
