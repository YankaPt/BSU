import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteOrder;

/**
 * Created by jan on 10.12.17.
 */
public class ListsTask extends JPanel {
    public ListsTask() {
        setBackground(new Color(195, 200, 47));
        setLayout(new BorderLayout());
        DefaultListModel<String> listModel1 = new DefaultListModel<>();
        listModel1.addElement("10000000000");
        listModel1.addElement("20000000000");
        listModel1.addElement("30000000000");
        listModel1.addElement("40000000000");
        DefaultListModel<String> listModel2 = new DefaultListModel<>();
        listModel2.addElement("50000000000");
        listModel2.addElement("60000000000");
        listModel2.addElement("70000000000");
        listModel2.addElement("80000000000");
        JList<String> list1 = new JList<>(listModel1);
        JList<String> list2 = new JList<>(listModel2);
        JPanel panel = new JPanel(new BorderLayout());
        add(list1, BorderLayout.WEST);
        add(list2, BorderLayout.EAST);
        add(panel, BorderLayout.CENTER);
        JButton buttonRight = new JButton(">");
        JButton buttonLeft = new JButton("<");
        panel.add(buttonRight, BorderLayout.NORTH);
        panel.add(buttonLeft, BorderLayout.SOUTH);
        buttonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int[] indices = list1.getSelectedIndices();
                for(int i=indices.length-1; i>=0;i--){
                    listModel2.addElement(listModel1.get(indices[i]));
                    listModel1.remove(indices[i]);
                }
            }
        });
        buttonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indices = list2.getSelectedIndices();
                for(int i=indices.length-1; i>=0;i--){
                    listModel1.addElement(listModel2.get(indices[i]));
                    listModel2.remove(indices[i]);
                }
            }
        });
    }
}
