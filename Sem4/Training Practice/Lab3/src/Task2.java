import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Timer;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;

public class Task2 extends JFrame
{
    private DisplayWindowWidth frameSize;
    private double period;
    private JList list;
    private double speed;
    static protected MyComponent myComponent;
    Task2()
    {
        super();
        getContentPane().setLayout(new BorderLayout());
        myComponent = new MyComponent();
        setMinimumSize(new Dimension(200,200));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        DefaultListModel<String> listModel = new DefaultListModel();
        listModel.addElement("Clockwise");
        listModel.addElement("Counterclock-wise");
        list = new JList(listModel);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(list, BorderLayout.NORTH);
        add(rightPanel,BorderLayout.EAST);
        mainPanel.add(myComponent,BorderLayout.CENTER);
        add(mainPanel,BorderLayout.CENTER);
        Scrollbar redSlider=new Scrollbar(Scrollbar.HORIZONTAL, 500, 10, 0, 4000);
        redSlider.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                speed = e.getValue();
                if(speed != 0) {
                    myComponent.setFlagTimer(true);
                    myComponent.setPeriod((360 * myComponent.getRadius()) / speed);
                }
                else
                    myComponent.setFlagTimer(false);
                frameSize.setSpeed(speed);
            }
        });
        getContentPane().add(redSlider,BorderLayout.SOUTH);
        frameSize = new DisplayWindowWidth(mainPanel,myComponent,speed);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(list.getSelectedIndex()==0)
                    myComponent.setFlag(true);
                else
                    myComponent.setFlag(false);
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public MyComponent getMyComponent() {
        return myComponent;
    }

    public static void main(String []args)
    {
        Task2 task2 = new Task2();
        Timer timer = new Timer();
        timer.schedule(myComponent.getTaskTimer(),100,100);
    }
}