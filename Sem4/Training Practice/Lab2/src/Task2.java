import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;

public class Task2 extends JPanel {
    private boolean DEBUG = false;
    private JTable table;
    private Integer cost;
    private String[] columnNames = {"Flag",
            "Description",
            "Price",
            "Is check"};
    private ImageIcon icon1 = new ImageIcon("/home/jan/IdeaProjects/Sem4/Lab2/plain/flag_belarus.png");
    private ImageIcon icon2 = new ImageIcon("/home/jan/IdeaProjects/Sem4/Lab2/plain/flag_canada.png");
    private ImageIcon icon3 = new ImageIcon("/home/jan/IdeaProjects/Sem4/Lab2/plain/flag_poland.png");
    private ImageIcon icon4 = new ImageIcon("/home/jan/IdeaProjects/Sem4/Lab2/plain/flag_france.png");
    private ImageIcon icon5 = new ImageIcon("/home/jan/IdeaProjects/Sem4/Lab2/plain/flag_norway.png");

    private Object[][] data = {
            {icon1, "Smith", 5, false},
            {icon2, "Doe", 3, true},
            {icon3, "Black", 2, true},
            {icon4, "White", 20, true},
            {icon5, "Brown", 10, false},
            {null, "Total cost", 25, null}
    };
    public Task2() {
        super(new GridLayout(1,0));
        table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
        /*table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                repaint();
            }
        });
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                sup2er.keyPressed(e);
                repaint();
            }
        });*/
    }

    @Override
    public void repaint() {
        super.repaint();
        if(data!=null)
        data[data.length-1][2] = cost();
    }

    class MyTableModel extends AbstractTableModel {
        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            if (col < 0) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
            }

            data[row][col] = value;
            fireTableCellUpdated(row, col);
            repaint();

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }
    int cost() {
        cost=0;
        for(int i=0; i<data.length-1;i++) {
            if((boolean)data[i][3]==true)
            cost+=(Integer) data[i][2];
        }
        return cost;
    }
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Task2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*String newFlagPath;
        String newDescription;
        int newPrice;*/
        class CustomDialog extends JDialog {
            public void dispose() {
                frame.repaint();
                super.dispose();
            }
            CustomDialog(){
                setVisible(true);
                setSize(300, 150);
                JButton createButton = new JButton("Create");
                JButton cancelButton = new JButton("Cancel");
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(createButton);
                buttonPanel.add(cancelButton);
                add(buttonPanel, BorderLayout.SOUTH);
                JPanel fieldsPanel = new JPanel();
                fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
                JTextField flagPathField = new JTextField("/home/jan/IdeaProjects/Sem4/Lab2/plain/flag_");
                flagPathField.setToolTipText("Flag's path");
                JTextField descriptionField = new JTextField();
                descriptionField.setToolTipText("Description");
                JTextField priceField = new JTextField();
                priceField.setToolTipText("Price");
                fieldsPanel.add(flagPathField);
                fieldsPanel.add(descriptionField);
                fieldsPanel.add(priceField);
                add(fieldsPanel);
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                createButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String newFlagPath = flagPathField.getText();
                        String newDescription = descriptionField.getText();
                        int newPrice = Integer.parseInt(priceField.getText());
                        Object[][] tempArray = new Object[data.length+1][columnNames.length];
                        int i=0;
                        for(; i<data.length-1; i++) {
                            tempArray[i][0] = data[i][0];
                            tempArray[i][1] = data[i][1];
                            tempArray[i][2] = data[i][2];
                            tempArray[i][3] = data[i][3];
                        }
                        tempArray[i][0] =new ImageIcon(newFlagPath);
                        tempArray[i][1] =newDescription;
                        tempArray[i][2] =newPrice;
                        tempArray[i][3] =false;
                        i++;
                        tempArray[i][0] = null;
                        tempArray[i][1] = "Total cost";
                        tempArray[i][2] = cost;
                        tempArray[i][3] = null;
                        data = tempArray;
                        dispose();
                    }
                });
            }

        }

        JButton newTourButton = new JButton("Create new tour");
        newTourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new CustomDialog();
                frame.repaint();
            }
        });
        frame.add(newTourButton, BorderLayout.NORTH);
        Task2 task2 = new Task2();
        frame.add(task2, BorderLayout.SOUTH);
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                task2.repaint();
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //javax.swing.SwingUtilities.invokeLater(Task2::createAndShowGUI);
        Task2 task2 = new Task2();
        task2.createAndShowGUI();
    }
}