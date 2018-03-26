import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by jan on 10.12.17.
 */
public class GridTask extends JPanel{
    final int SIZE = 4;
    final Color BUTTON_EXITED_COLOR = new Color(193, 238, 222);
    final Color BUTTON_ENTERED_COLOR = new Color(93, 146, 171);

    public GridTask() {
        setLayout(new GridLayout(SIZE, SIZE));
        MyMouseListener myMouseListener = new MyMouseListener();
        for(int i=0; i<SIZE*SIZE; i++) {
        add(new JButton(i+1+""));
            getComponent(i).addMouseListener(myMouseListener);
            getComponent(i).setBackground(BUTTON_EXITED_COLOR);
        }
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
