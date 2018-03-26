import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Task1 extends JFrame {
    double angle = Math.PI/2;
    double delta = Math.PI/12;
    int deltaX=0;
    int deltaY=0;
    public Task1() {
        super("simpleApp");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D gr2d = (Graphics2D) g;
        gr2d.clearRect(50, 50, 400, 400);
        gr2d.setPaint(Color.BLUE);
        gr2d.drawOval(50, 50, 400, 400);
        gr2d.drawLine(250, 250, 250+deltaX, 250+deltaY);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle +=delta;
                deltaX = (int)Math.round(200*Math.cos(angle));
                deltaY = (int)Math.round(200*Math.sin(angle));
                repaint();
            }
        };
        Timer timer = new Timer(100, actionListener);
        timer.start();


    }

    public static void main(String args[]) {
        Task1 app = new Task1();

    }
}
