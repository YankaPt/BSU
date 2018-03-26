import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.TimerTask;

public class MyComponent extends JComponent {
    private BufferedImage icon;
    private double x, y;
    private double centerX, centerY;
    private double radius;
    private double x1, y1;
    private double time;
    private double period;
    private double INCREMENT;
    private Boolean flag = false;
    private MyTaskTimer taskTimer;
    private int counter = 0;
    private double old;
    private Boolean flagTimer=true;

    public MyComponent() {
        try {
            icon = ImageIO.read(new File("tree-group-logo.png"));
        } catch (Exception ex) {

        }
        taskTimer = new MyTaskTimer(this);
    }

    public MyTaskTimer getTaskTimer() {
        return taskTimer;
    }

    public void initCircle(double x, double y, double radius, double centerX, double centerY) {
        this.x = x;
        this.centerX = centerX;
        this.y = y;
        this.centerY = centerY;
        this.radius = radius;
        repaint();
    }

    public double getPeriod() {
        return period;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        x1 = centerX + (radius * Math.sin((time) * INCREMENT));
        y1 = centerY - (radius * Math.cos((time) * INCREMENT));
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.blue);
        Shape theCircle = new Ellipse2D.Double(this.x, this.y, 2 * radius, 2 * radius);
        graphics2D.draw(theCircle);
        g.drawImage(icon, (int) x1 - 10, (int) y1 - 10, 20, 20, null);
    }

    public void setPeriod(double periodiNSecond) {
        old = time * INCREMENT;
        INCREMENT = ((360 / periodiNSecond) * (Math.PI / 180));
        time = old / INCREMENT;
        counter = (int) time;
        this.period = periodiNSecond;
    }

    public double getRadius() {
        return radius;
    }

    public void setTime(int counter) {
        this.time = counter;
        repaint();
    }

    public void setFlagTimer(Boolean flagTimer) {
        this.flagTimer = flagTimer;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public class MyTaskTimer extends TimerTask {
        private MyComponent component;
        public MyTaskTimer(MyComponent component) {
            this.component = component;
        }
        @Override
        public void run() {
            if (flagTimer) {
                if(flag)
                    counter++;
                else
                    counter--;
                if (Math.abs(counter) >= period)
                    counter = 0;
                component.setTime(counter);
            }
        }
    }
}