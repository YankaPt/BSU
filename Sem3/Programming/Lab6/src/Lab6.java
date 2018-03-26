import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by jan on 5.12.17.
 */
public class Lab6 {
    JFrame pane;
    String coordinates;
    JLabel label;
    JButton button;
    Point MousePressedLocation;
    public static void main(String[] args) {
        Lab6 lab6 = new Lab6();
        lab6.go();
    }
    public void go() {
        pane = new JFrame();
        pane.setLayout(null);
        Dimension dimension = new Dimension(600, 600);
        button = new JButton("Button");
        pane.add(button);
        pane.addMouseListener(new MyMouseListener());
        pane.addMouseMotionListener(new MyMouseListener());
        Insets insets = pane.getInsets();
        button.addMouseListener(new MyMouseListener());
        button.addMouseMotionListener(new MyMouseListener());
        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    if (!button.getText().isEmpty()) {
                        button.setText(button.getText().substring(0, button.getText().length() - 1));
                    }
                } else
                    button.setText(button.getText() + e.getKeyChar());
            }
            });
        button.setPreferredSize(new Dimension(300, 50));
        button.setSize(200, 60);
        button.setLocation(20, 20);
        label = new JLabel("Coordinates : X=" + button.getLocation().getX()+ ", Y=" +button.getLocation().getY());
        pane.add(label);
        label.setBounds(200, 550,label.getPreferredSize().width, label.getPreferredSize().height );
        coordinates = "Coordinates : X=" + button.getLocation().getX()+ ", Y=" +button.getLocation().getY();
        label.setText(coordinates);
        pane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane.setSize(dimension);
        pane.setVisible(true);

    }
    class MyMouseListener extends MouseInputAdapter {
        public void mouseClicked(MouseEvent event) {
            coordinates = "Coordinates : X=" + event.getX()+ ", Y=" +event.getY();
            label.setText(coordinates);
            if(event.getComponent().equals(pane))
                button.setLocation(this.getRelativelyLocation(event));
        }
        public void mousePressed(MouseEvent event) {
            if(event.getComponent().equals(button)&&event.isControlDown()) {
                MousePressedLocation = event.getPoint();
            }
        }
        public void mouseMoved(MouseEvent event) {
            coordinates = "Coordinates : X=" +this.getRelativelyLocation(event).x + ", Y=" +this.getRelativelyLocation(event).y;
            label.setText(coordinates);
        }
        public void mouseDragged(MouseEvent event) {
            coordinates = "Coordinates : X=" +this.getRelativelyLocation(event).x + ", Y=" +this.getRelativelyLocation(event).y;
            label.setText(coordinates);
            if(event.getComponent().equals(button)&&event.isControlDown()) {
                button.setSelected(false);
                button.setLocation(this.getRelativelyLocation(event).x-MousePressedLocation.x, this.getRelativelyLocation(event).y-MousePressedLocation.y);
            }
        }
        Point getRelativelyLocation(MouseEvent mouseEvent) {
            return new Point(mouseEvent.getXOnScreen()-pane.getBounds().x, mouseEvent.getYOnScreen()-pane.getBounds().y);
        }
    }
}
