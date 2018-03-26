import com.jankothebest.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by jan on 27.10.17.
 */
public class Lab4 {
    JFrame frame;
    public static void main(String[] args) {
        Lab4 gui = new Lab4();
        gui.go();
    }
    public void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        BorderLayout borderLayout = new BorderLayout();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("The only menu in this programm has menu items");
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("A text-only menu item", KeyEvent.VK_T);
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menu.add(menuItem);

        JTabbedPane tabbedPane1 = new JTabbedPane();
        JLabel label1 = new JLabel("Progression 1");
        JLabel label2 = new JLabel("Progression 2");
        tabbedPane1.addTab("1", label1);
        tabbedPane1.addTab("2", label2);

        JTabbedPane tabbedPane2 = new JTabbedPane();
        JLabel label3 = new JLabel("Progression 1");
        JLabel label4 = new JLabel("Progression 2");
        tabbedPane2.addTab("1", label3);
        tabbedPane2.addTab("2", label4);

        JTabbedPane tabbedPane0 = new JTabbedPane();
        tabbedPane0.addTab("File A", tabbedPane1);
        tabbedPane0.addTab("File B", tabbedPane2);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton saveProgressionButton = new JButton("Save progression");
        saveProgressionButton.addActionListener(new SaveProgressionListener());
        JButton calculateButton = new JButton("Calculate progression");
        calculateButton.addActionListener(new CalculateListener());
        JButton estimateButton = new JButton("Estimate type of progression");
        //estimateButton.addActionListener(new EstimateListener());
        buttonPanel.add(saveProgressionButton);
        buttonPanel.add(calculateButton);
        buttonPanel.add(estimateButton);

        JPanel messagePanel = new JPanel();
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText("Everything is OK...For now.");
        messagePanel.add(textArea);

        frame.add(BorderLayout.NORTH, menuBar);
        frame.add(BorderLayout.SOUTH, messagePanel);
        frame.add(BorderLayout.EAST, buttonPanel);
        frame.add(BorderLayout.CENTER, tabbedPane0);
        //frame.pack();
        frame.setVisible(true);

    }
    class SaveProgressionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }
    class CalculateListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }
}
