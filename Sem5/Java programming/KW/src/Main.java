import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main extends JFrame {

    private CopyOnWriteArrayList<Color> colorList;

    public static void main(String[] args) {
        new Main();
    }

    private Main() {

        super("MainFrame");
        colorList = new CopyOnWriteArrayList<>();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        Box b = new Box(1);

        Draw jPanel = new Draw();
        b.add(jPanel);

        container.add(b);
        setBounds(100, 100, 450, 300);

        Thread t1 = new Thread(new BlueThread());
        Thread t2 = new Thread(new RedThread());

        t1.start();
        t2.start();

        setVisible(true);
        try {
            while (true) {
                repaint();
                Thread.sleep(50);
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    class BlueThread implements Runnable {

        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                colorList.add(Color.BLUE);
            }
        }
    }

    class RedThread implements Runnable {

        public void run() {
            while (true) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                colorList.add(Color.RED);
            }
        }
    }

    class Draw extends JPanel {

        public synchronized void paint(Graphics g) {
            super.paint(g);
            int i = 0;
            for (Color color : colorList) {
                g.setColor(color);
                g.fillOval(20 * i, 20 * i, 2 * 10, 2 * 10);
                i++;
            }
        }
    }
}
