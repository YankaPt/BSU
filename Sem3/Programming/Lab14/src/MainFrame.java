import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sun.applet.Main;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.zip.DataFormatException;

public class MainFrame extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private JList<Student> list1;
    private JList<Student> list2;
    private JButton openFileButton;
    private JButton addStudentButton;
    private JButton doRequestButton;
    private JPanel rootPanel;
    private JButton saveToFileButton;

    private TreeSet<Student> doTask(ArrayList<Student> students) {
        TreeSet<Student> required = new TreeSet<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                int cmp = Integer.compare(o1.getCourse(), o2.getCourse());
                if (cmp == 0) {
                    cmp = Integer.compare(o1.getGroup(), o2.getGroup());
                    if (cmp == 0) {
                        cmp = o1.getSurname().compareTo(o2.getSurname());
                    }
                }
                return cmp;
            }
        });
        for (Student aList : students) {
            int counter = 0;
            for (Student aList1 : students) {
                if (aList.getSurname().equals(aList1.getSurname())) {
                    counter++;
                }
            }
            if (counter > 1) {
                required.add(aList);
            }
        }
        return required;
    }

    //!!!!!
    public DefaultListModel<Student> createListModel(ArrayList<Student> students) {
        DefaultListModel<Student> listModel = new DefaultListModel<>();
        for (Student student : students) {
            listModel.addElement(student);
        }
        return listModel;
    }

    //!!!!!
    private DefaultListModel<Student> createListModel(TreeSet<Student> students) {
        DefaultListModel<Student> listModel = new DefaultListModel<>();
        for (Student student : students) {
            listModel.addElement(student);
        }
        return listModel;
    }

    private void writeXML(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.xml"));
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.append("<students>\n");
        for (Student student : students) {
            writer.append("<student>\n");
            writer.append("<name>");
            writer.append(student.getSurname());
            writer.append("</name>\n");
            writer.append("<group>");
            writer.append(String.valueOf(student.getSurname()));
            writer.append("</group>\n");
            writer.append("<course>");
            writer.append(String.valueOf(student.getCourse()));
            writer.append("</course>\n");
            writer.append("<gradebook>");
            writer.append(String.valueOf(student.getNumberOfSRB()));
            writer.append("</gradebook>\n");
            writer.append("</student>\n");
        }
        writer.append("</students>");
        writer.close();
    }

    private void readXML(File file) throws ParserConfigurationException, IOException, SAXException {
        students.clear();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("student");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String
                        name = element.getElementsByTagName("name").item(0).getTextContent(),
                        group = element.getElementsByTagName("course").item(0).getTextContent(),
                        course = element.getElementsByTagName("group").item(0).getTextContent(),
                        numberOfRSB = element.getElementsByTagName("numberOfSRB").item(0).getTextContent();
                students.add(new Student(name, Integer.parseInt(course), Integer.parseInt(group), Integer.parseInt(numberOfRSB)));
            }
        }
    }

    private void readTXT(File file) throws FileNotFoundException {
        students.clear();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String surname = scanner.next();
            int course = scanner.nextInt();
            int group = scanner.nextInt();
            int numberOfSRB = scanner.nextInt();
            students.add(new Student(surname, course, group, numberOfSRB));
        }
    }

    //!!!!!
    MainFrame() {
        super("Student data base");
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 4,
                Toolkit.getDefaultToolkit().getScreenSize().height / 4,
                Toolkit.getDefaultToolkit().getScreenSize().width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        AddingStudentDialog adding = new AddingStudentDialog(this);

        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adding.setVisible(true);
            }
        });

        doRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list2.setModel(createListModel(doTask(students)));
            }
        });
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser filewrite = new JFileChooser(new File("."));
                int ret = filewrite.showDialog(null, "Open");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = filewrite.getSelectedFile();
                    String filename = file.getName();
                    try {
                        switch (filename.substring(filename.length() - 3)) {
                            case "txt":
                                readTXT(file);
                                list1.setModel(createListModel(students));
                                break;
                            case "xml":
                                readXML(file);
                                list1.setModel(createListModel(students));
                                break;
                        }
                    } catch (IOException | SAXException | ParserConfigurationException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser filewrite = new JFileChooser(new File("."));
                int ret = filewrite.showDialog(null, "Open");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = filewrite.getSelectedFile();
                    try {
                        writeXML(file);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });
        list1.setLayoutOrientation(JList.VERTICAL);
        list2.setLayoutOrientation(JList.VERTICAL);
        add(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    ArrayList<Student> getStudents() {
        return students;
    }

    JList<Student> getInitialList() {
        return list1;
    }

    public static void main(String[] args) {
        new MainFrame();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Initial list:");
        rootPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Requested list:");
        rootPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openFileButton = new JButton();
        openFileButton.setText("Open file");
        rootPanel.add(openFileButton, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addStudentButton = new JButton();
        addStudentButton.setText("Add Student");
        rootPanel.add(addStudentButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        rootPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        list1 = new JList();
        scrollPane1.setViewportView(list1);
        final JScrollPane scrollPane2 = new JScrollPane();
        rootPanel.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        list2 = new JList();
        scrollPane2.setViewportView(list2);
        doRequestButton = new JButton();
        doRequestButton.setText("Do request");
        rootPanel.add(doRequestButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveToFileButton = new JButton();
        saveToFileButton.setText("Save to file");
        rootPanel.add(saveToFileButton, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
