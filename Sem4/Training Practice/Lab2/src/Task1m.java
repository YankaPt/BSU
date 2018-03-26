import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Task1m {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Task1m::go);
    }
    public static void go() {
        JFrame frame = new JFrame("Task1m");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(600, 600));
        JScrollPane scrollPane = new JScrollPane();
        JPanel mainPanel = new JPanel();
        scrollPane.setViewportView(mainPanel);
        JLabel label = new JLabel("lalala");
        frame.add(label, BorderLayout.NORTH);
        frame.add(scrollPane);

        DefaultListModel<Country> defaultListModel = new DefaultListModel<>();
        Stream.of(
                new Country("Canada","/home/jan/IdeaProjects/Sem4/Lab2/plain/flag_canada.png"),
                new Country("Norway", "/home/jan/IdeaProjects/Sem4/Lab2/plain/flag_norway.png"),
                new Country("Poland", "/home/jan/IdeaProjects/Sem4/Lab2/plain/flag_poland.png"),
                new Country("Belarus", "/home/jan/IdeaProjects/Sem4/Lab2/plain/flag_belarus.png"),
                new Country("France", "/home/jan/IdeaProjects/Sem4/Lab2/plain/flag_france.png")
        ).forEach(defaultListModel::addElement);

        Map<Country, String> map = new HashMap<>();
        DefaultListModel<Map.Entry<Country, String>> entryDefaultListModel = new DefaultListModel<>();
        map.put(defaultListModel.get(0), "Ottava");
        map.put(defaultListModel.get(1), "Osla");
        map.put(defaultListModel.get(2), "Warshawa");
        map.put(defaultListModel.get(3), "Miensk");
        map.put(defaultListModel.get(4), "Paris");

        for(Map.Entry entry : map.entrySet())
            entryDefaultListModel.addElement(entry);

        JList<Map.Entry<Country,String >> list = new JList<>(entryDefaultListModel);
        /*MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount()==1) {
                    int index = list.locationToIndex(e.getPoint());
                    label.setIcon(new ImageIcon(entryDefaultListModel.get(index).getKey().getIconPath()));
                    label.setText(entryDefaultListModel.get(index).getKey().getName()+": "+entryDefaultListModel.get(index).getValue());
                }
            }
        };*/
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index =e.getFirstIndex();
                label.setIcon(new ImageIcon(entryDefaultListModel.get(index).getKey().getIconPath()));
                label.setText(entryDefaultListModel.get(index).getKey().getName()+": "+entryDefaultListModel.get(index).getValue());
            }
        });
        list.setCellRenderer((listOfPinta, value, index, isSelected, cellHasFocus)-> {
            JLabel pinta = new JLabel(value.getKey().getName(), new ImageIcon(value.getKey().getIconPath()), JLabel.LEFT);
            pinta.setIconTextGap(10);
            pinta.setOpaque(true);
            if (isSelected) {
                pinta.setBackground(listOfPinta.getSelectionBackground());
                pinta.setForeground(listOfPinta.getSelectionForeground());
                pinta.setText(((Map.Entry<Country, String>)value).getKey().getName() + " " + map.get(value));
               /* label.setIcon(new ImageIcon(entryDefaultListModel.get(index).getKey().getIconPath()));
                label.setText(entryDefaultListModel.get(index).getKey().getName()+": "+entryDefaultListModel.get(index).getValue());*/
            }
            else {
                pinta.setBackground(listOfPinta.getBackground());
                pinta.setForeground(listOfPinta.getForeground());
            }
            pinta.setFont(listOfPinta.getFont());
            pinta.setEnabled(listOfPinta.isEnabled());
            return pinta;
        });
        //list.addMouseListener(mouseListener);
        //list.setCellRenderer(new CustomJListRendererM());
        mainPanel.add(list);
    }
}
