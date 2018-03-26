import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by jan on 22.12.17.
 */
public class MyButton extends JButton {
    final Color BUTTON_EXITED_COLOR = new Color(193, 238, 222);
    final Color BUTTON_ENTERED_COLOR = new Color(93, 146, 171);
    MyButton() {super();}
    MyButton(String a) {super(a);}

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(new MyMouseListener());
    }

    class MyMouseListener implements MouseListener {
        JButton button;
        String buttonName;
        int mouseButton;
        public void mouseEntered(MouseEvent e) {
            button = (JButton) e.getComponent();
            button.setBackground(BUTTON_ENTERED_COLOR);
        }
        public void mouseExited(MouseEvent e) {
            button = (JButton) e.getComponent();
            button.setBackground(BUTTON_EXITED_COLOR);
        }
        public void mousePressed(MouseEvent e) {
            if(mouseButton==MouseEvent.NOBUTTON) {
                mouseButton = e.getButton();
                button = (JButton) e.getComponent();
                buttonName = button.getText();
                button.setText("Clicked!");
            }
        }
        public void mouseReleased(MouseEvent e) {
            if(e.getButton()==mouseButton) {
                button = (JButton) e.getComponent();
                button.setText(buttonName);
                mouseButton = MouseEvent.NOBUTTON;
            }
        }
        public void mouseClicked(MouseEvent e) {
        }

    }
}
