import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
public class DisplayWindowWidth extends JPanel{
    JPanel frame;
    MyComponent component;
    JPanel panel = new JPanel();
    private double speed;
    public DisplayWindowWidth(JPanel frame,MyComponent comp,double speed){
        this.frame = frame;
        this.component = comp;
        this.speed = speed;
        frame.addComponentListener(new FrameListen());
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private class FrameListen implements ComponentListener{
        public void componentHidden(ComponentEvent arg0) {
        }
        public void componentMoved(ComponentEvent arg0) {
        }
        public void componentResized(ComponentEvent arg0) {
            double radius;
            if(frame.getWidth()<frame.getHeight())
                radius = frame.getWidth()/2;
            else
                radius = frame.getHeight()/2;
            double x1 = frame.getWidth()/2-radius;
            double y1 = frame.getHeight()/2-radius;
            component.initCircle(x1,y1,radius,x1+radius,y1+radius);
            if(speed!=0)
                component.setPeriod((360 * radius)/speed);
        }
        public void componentShown(ComponentEvent arg0) {

        }
    }
}