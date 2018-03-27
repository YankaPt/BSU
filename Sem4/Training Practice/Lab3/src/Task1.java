import javax.swing.*;
import java.awt.*;

public class Task1 {
    private static double clockDegree;
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Task1::go);
    }
    public static void go() {
        clockDegree = 0;
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(600, 600));
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int w = this.getWidth(), h = this.getHeight(), xCenter = w / 2, yCenter = h / 2;
                int radius = Math.min(w, h) / 3, xCorner = xCenter - radius, yCorner = yCenter - radius;
                double clockAngle = Math.toRadians(clockDegree);
                g.drawOval(xCorner, yCorner, 2 * radius, 2 * radius);
                g.drawLine(xCenter, yCenter, (int) (xCenter + radius * Math.cos(clockAngle)), (int) (yCenter + radius * Math.sin(clockAngle)));
            }
        };
        frame.add(panel);
        Timer timer = new Timer(10, e -> {
            clockDegree += 0.06;
            clockDegree %= 360;
            panel.repaint();
        });
        timer.start();
    }
}
