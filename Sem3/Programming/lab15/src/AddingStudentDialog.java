
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddingStudentDialog extends JDialog {
    private JTextField surname = new JTextField();
    private JTextField course = new JTextField();
    private JTextField group = new JTextField();
    private JTextField numberOfSRB = new JTextField();

    private boolean isAllDataEntered() {
        return !surname.getText().isEmpty() && !course.getText().isEmpty() &&
                !group.getText().isEmpty() && !numberOfSRB.getText().isEmpty();
    }

    AddingStudentDialog(MainFrame owner) {
        setTitle("Adding student");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(owner.getWidth()/2, owner.getHeight()/2);
        setLocation(owner.getLocation());
        setLayout(new GridLayout(5, 2));
        JButton addButton = new JButton("Add to database");
        JButton cancelButton = new JButton("Cancel");
        JDialog buffer = this;
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (isAllDataEntered()) {
                        ArrayList<Student> newStudents = owner.getStudents();
                        newStudents.add(new Student(surname.getText(),
                                Integer.parseInt(course.getText()),
                                Integer.parseInt(group.getText()),
                                Integer.parseInt(numberOfSRB.getText())));
                        owner.getInitialList().setModel(owner.createListModel(newStudents));
                        //dispose();
                    } else {
                        throw new InputDataException();
                    }
                } catch (NumberFormatException | InputDataException e1) {
                    JOptionPane.showMessageDialog(buffer, "Invalid input data");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(new JLabel("Course:"));
        this.add(course);
        this.add(new JLabel("Group"));
        this.add(group);
        this.add(new JLabel("Surname"));
        this.add(surname);
        this.add(new JLabel("Number of RSB"));
        this.add(numberOfSRB);
        this.add(addButton);
        this.add(cancelButton);
    }
}

class InputDataException extends Exception {
    InputDataException() {
        super("Input all data!");
    }
}
