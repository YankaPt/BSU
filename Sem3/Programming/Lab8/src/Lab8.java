import javax.swing.*;

/**
 * Created by jan on 10.12.17.
 */
public class Lab8 {
    public static void main(String[] args) {
        Lab8 lab8 = new Lab8();
        lab8.go();
    }
    public static void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(600, 600);
        JTabbedPane jTabbedPane = new JTabbedPane();
        frame.add(jTabbedPane);
        jTabbedPane.addTab("This is List Task", new ListsTask());
        jTabbedPane.addTab("This is Grid Task", new GridTask());
        jTabbedPane.addTab("This is Radio Buttons Task", new RadioButtonsTask());
    }
}
