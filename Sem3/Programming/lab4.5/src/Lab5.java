import com.jankothebest.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by jan on 27.10.17.
 */
public class Lab5 {
    public static void main(String[] args) {
        MyGui gui = new MyGui();
        gui.setVisible(true);
        gui.pack();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
    class MyGui extends JFrame
    {
        private Series series;
        private JPanel mainPanel;
        private JPanel leftPanel;
        private JPanel southPanel;
        private JPanel middlePanel;
        private JPanel middleSouthPanel;
        private ButtonGroup radioGroup;
        private JTextArea textArea;
        private JTextField textField;

        public MyGui()
        {
            series = new Liner(10,2,15);
            textField = new JTextField(10);
            textField.setText(Integer.toString(series.getLength()));
            mainPanel = new JPanel();
            southPanel = new JPanel();
            leftPanel = new JPanel();
            middlePanel = new JPanel();
            middleSouthPanel = new JPanel();

            mainPanel.setLayout(new BorderLayout());
            leftPanel.setLayout(new GridLayout(3,1));
            southPanel.setLayout(new FlowLayout());
            middlePanel.setLayout(new BorderLayout());
            middleSouthPanel.setLayout(new FlowLayout());

            JButton fileButton = new JButton("Save in File");
            fileButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        series.saveToFile("java_test");
                        JOptionPane.showMessageDialog(null,"Data have saved");
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null,"Data have not saved");
                        System.err.println(ee);
                        ee.printStackTrace();
                    }
                }
            });
            JButton updateButton = new JButton("update");
            updateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    update();
                }
            });
            JButton increaseDiffButton = new JButton("Increase on 1 difference");
            increaseDiffButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    series.increaseQ(1);
                    update();
                }
            });


            JLabel labelTop = new JLabel("First n elements in series",JLabel.CENTER);
            JLabel labelBottom = new JLabel("Input amount elements for their print",JLabel.LEFT);

            mainPanel.add(labelTop,BorderLayout.NORTH);
            southPanel.add(labelBottom);
            southPanel.add(textField);
            southPanel.add(updateButton);

            mainPanel.add(leftPanel,BorderLayout.WEST);
            mainPanel.add(southPanel,BorderLayout.SOUTH);

            radioGroup = new ButtonGroup();
            JRadioButton linerButton = new JRadioButton("Liner", true);
            linerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    series = new Liner(series.getStartElement(),series.getQ(),series.getLength());
                    update();
                }
            });
            JRadioButton exponentialButton = new JRadioButton("Exponential", false);
            exponentialButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    series = new Exponential(series.getStartElement(),series.getQ(),series.getLength());
                    update();
                }
            });
            radioGroup.add(linerButton);
            radioGroup.add(exponentialButton);
            JPanel radioButtons = new JPanel();
            radioButtons.setLayout(new BoxLayout(radioButtons,BoxLayout.Y_AXIS));
            JLabel output2 = new JLabel("Choise progression",JLabel.CENTER);
            radioButtons.add(output2);
            radioButtons.add(linerButton);
            radioButtons.add(exponentialButton);
            leftPanel.add(radioButtons);
            leftPanel.add(fileButton);
            leftPanel.add(increaseDiffButton);

            textArea = new JTextArea(10, 15);
            textArea.setBackground(Color.LIGHT_GRAY);
            textArea.setText(series.toString());
            textArea.setCaretPosition(0);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(false);

            middlePanel = new JPanel();
            middlePanel.setLayout(new BorderLayout());

            final JScrollPane scrollPane = new JScrollPane(textArea);
            final JCheckBox checkBox = new JCheckBox("Show vertcial scrollbar");
            checkBox.setSelected(false);
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (checkBox.isSelected() )
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    else
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                }
            });
            middlePanel.add(scrollPane,BorderLayout.CENTER);
            middleSouthPanel.add(checkBox);
            middlePanel.add(middleSouthPanel,BorderLayout.SOUTH);
            mainPanel.add(middlePanel,BorderLayout.CENTER);

            add(mainPanel);
        }
        void update()
        {
            String str = textField.getText();
            try {
                int length = Integer.parseInt(str);
                series.setLength(length);
            }
            catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Input please number");
                textField.setText(Integer.toString(series.getLength()));
            }
            textArea.setText(series.toString());
        }
    }
