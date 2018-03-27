import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

public class Task3 extends JFrame {
    Task3() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setMinimumSize(new Dimension(600, 600));
    }
    public static void main(String[] args) {
        Task3 task3 = new Task3();
        task3.go();
    }
    public void go() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.add(panel);
        JPanel legendPane = new JPanel();
        legendPane.setLayout(new BoxLayout(legendPane, BoxLayout.Y_AXIS));
        panel.add(legendPane, BorderLayout.WEST);
        List<DiagramEntry> data = new ArrayList<>();
        JPanel paintPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int
                        w = this.getWidth(),
                        h = this.getHeight(),
                        xCenter = w / 2,
                        yCenter = h / 2,
                        radius = Math.min(w, h) / 3,
                        xCorner = xCenter - radius,
                        yCorner = yCenter - radius;
                double sum = 0;
                for (DiagramEntry entry : data) {
                    sum += entry.getAngle();
                }
                double angle = 0, d = (360-sum)/data.size();
                for (DiagramEntry entry : data) {
                    double delta = entry.getAngle();
                    g.setColor(entry.getColor());
                    g.fillArc(xCorner, yCorner, 2*radius, 2*radius, (int)Math.round(angle), (int)Math.round(delta+d));
                    angle += (int)Math.round(delta+d);
                }
                g.fillArc(xCorner, yCorner, 2*radius, 2*radius, (int)Math.round(angle), 360-(int)Math.round(angle));
            }
        };
        panel.add(paintPane, BorderLayout.CENTER);
        JButton button = new JButton("Generate diagram");
        panel.add(button, BorderLayout.EAST);
        button.addActionListener(e-> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open File");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            if (fileChooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION) return;
            File file = fileChooser.getSelectedFile();
            legendPane.removeAll();
            data.clear();
            DiagramEntry.resetValuesSum();
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String key = scanner.next();
                    int value = scanner.nextInt();
                    Random random = new Random();
                    int r = Math.abs(random.nextInt()) % 256, g = Math.abs(random.nextInt()) % 256, b = Math.abs(random.nextInt()) % 256;
                    DiagramEntry entry = new DiagramEntry(key, value, new Color(r, g, b));
                    data.add(entry);
                    legendPane.add(entry.getLegendLabel());
                }
                Collections.sort(data);
            }
            catch (InputMismatchException ex) {
                JOptionPane.showMessageDialog(this, "Invalid file format", "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            panel.updateUI();
        });
    }
}
class DiagramEntry implements Comparable<DiagramEntry> {
    private static int valuesSum = 0;
    private String key;
    private int value;
    private double angle;
    private Color color;
    private JLabel legendLabel;
    DiagramEntry(String key, int value, Color color) throws WrongEntryException {
        if (value < 0) throw new WrongEntryException("Negative value in '" + key + "'");
        this.key = key;
        this.value = value;
        this.angle = 0;
        this.color = color;
        valuesSum += value;
    }

    public static void resetValuesSum() {
        DiagramEntry.valuesSum = 0;
    }

    String getKey() {
        return key;
    }
    int getValue() {
        return value;
    }
    double getAngle() {
        if (angle == 0) {
            angle = value * 360 / valuesSum;
        }
        return angle;
    }
    public Color getColor() {
        return color;
    }
    public JLabel getLegendLabel() {
        if (legendLabel == null) {
            BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.setColor(getColor());
            g.fillRect(0, 0, 24, 24);
            legendLabel = new JLabel(getKey(), new ImageIcon(image), JLabel.RIGHT);
        }
        return legendLabel;
    }
    @Override
    public int compareTo(DiagramEntry o) {
        return Comparator.comparingInt(DiagramEntry::getValue).compare(this, o);
    }
}
class WrongEntryException extends RuntimeException {
    WrongEntryException(String message) {
        super(message);
    }
}
