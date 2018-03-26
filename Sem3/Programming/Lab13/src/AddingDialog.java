import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.zip.DataFormatException;

public class AddingDialog extends JDialog {
    private JTextField surnameField;
    private JTextField studentRecordBookField;
    private JTextField group;
    private JTextField course;

    public AddingDialog(MainFrame owner){
        setTitle("Add student");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        pack();
        setLayout(new GridLayout(5, 2));
        setBounds(550, 200, 400, 250);

        JLabel surnameLabel = new JLabel("Surname");
        JLabel studentRecordBookLabel = new JLabel("Student Record Book");
        JLabel groupLabel = new JLabel("Group");
        JLabel courseLabel = new JLabel("Course");
        surnameField = new JTextField();
        studentRecordBookField = new JTextField();
        group = new JTextField();
        course = new JTextField();
        JButton add = new JButton("Add");
        JButton cancel = new JButton("Cancel");

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!surnameField.getText().isEmpty() && !studentRecordBookField.getText().isEmpty() && !group.getText().isEmpty()){
                    try {
                        Student student = new Student(studentRecordBookField.getText() + " " + surnameField.getText() + " " + group.getText() + " " + course.getText());

                        if(owner.getModelLeft().contains(student)/*&& !owner.getModelRight().contains(student)*/){
                            owner.getModelLeft().removeElement(student);
                            if(!owner.getModelLeft().contains(student)) {
                                owner.getModelLeft().addElement(student);
                                owner.getModelRight().addElement(student);
                            }
                            owner.getModelRight().addElement(student);
                            owner.getModelRight().sort();
                        }
                        owner.getModelLeft().addElement(student);
                        owner.getStudentsCount().setText("Students " + owner.getModelLeft().getSize());
                        owner.getHighAchieversCount().setText("Sorted List " + owner.getModelRight().getSize());
                        surnameField.setText("");
                        studentRecordBookField.setText("");
                        group.setText("");
                        course.setText("");
                    } catch (DataFormatException | NumberFormatException fe) {
                        JOptionPane.showMessageDialog(null, "Incorrect data");
                    }
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.add(studentRecordBookLabel);
        this.add(studentRecordBookField);
        this.add(surnameLabel);
        this.add(surnameField);
        this.add(groupLabel);
        this.add(group);
        this.add(courseLabel);
        this.add(course);
        this.add(add);
        this.add(cancel);
    }
}
