package com.YankaPt;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    String valid;

    private class MyHandler extends DefaultHandler {
        private double average, total;
        int branchNumber;
        private boolean isQuantity;

        @Override
        public void startDocument() {
            average = 0;
            total = 0;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equals("quantity")) {
                isQuantity = true;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (isQuantity) {
                total += Integer.valueOf(new String(ch, start, length));
                ++branchNumber;
                isQuantity = false;
            }
        }

        @Override
        public void endDocument() {
            average = total / branchNumber;
        }

        double getAverage() {
            return average;
        }

        double getTotal() {
            return total;
        }

        int getBranchNumber() {
            return branchNumber;
        }
    }

    private boolean validateXMLByXSD(File xml) throws SAXException, IOException {
        try {
            InputStream in=Controller.class.getResourceAsStream("branches.xsd");
            File file = File.createTempFile("temp", ".tmp");
            FileOutputStream outputStream = new FileOutputStream(file);
            in.transferTo(outputStream);
            outputStream.close();
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(file)
                    .newValidator()
                    .validate(new StreamSource(xml));
        } catch (Exception e) {
            valid=e.getMessage();
            return false;
        }
        return true;
    }

    /*private boolean validateXMLByXSD(String XML, String path, String XSD) throws IOException
    {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schemaFactory.setResourceResolver(new ResourcesResolver(path));
            Validator xmlValidator = schemaFactory.newSchema(new StreamSource(getClass().getClassLoader().getResourceAsStream(path + XSD))).newValidator();
            xmlValidator.validate(new StreamSource(new StringReader(XML)));
        } catch (SAXException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }*/

    @FXML private Button saveButton;
    @FXML private RadioButton rbBinary;
    @FXML private RadioButton rbDOM;
    @FXML private RadioButton rbSAX;
    @FXML private TableView<Leaf> tableView;
    @FXML private TableColumn<Leaf, String> leafColorColumn;
    @FXML private TableColumn<Leaf, Integer> quantityColumn;
    @FXML private TextField tfID;
    @FXML private TextField tfColor;
    @FXML private TextField tfTree;
    private FileChooser fileChooser;
    private Branch branch;
    static final File xsdFile = new File("com/YankaPt/branches.xsd");

    @FXML private void initialize() {
        branch = new Branch();
        fileChooser = new FileChooser();
        leafColorColumn.setCellValueFactory(new PropertyValueFactory<>("leafColor"));
        leafColorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        leafColorColumn.setOnEditCommit(t-> {
            Leaf leaf = tableView.getSelectionModel().getSelectedItem();
            leaf.setLeafColor(t.getNewValue());
        });
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.valueOf(string);
            }
        }));
        quantityColumn.setOnEditCommit(t-> {
            Leaf leaf = tableView.getSelectionModel().getSelectedItem();
            leaf.setQuantity(t.getNewValue());
        });
    }

    @FXML private void add() {
        Leaf leaf = new Leaf("", 0);
        branch.getList().add(leaf);
        tableView.getItems().add(leaf);
    }
    @FXML private void remove() {
        Leaf leaf = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(leaf);
        branch.getList().remove(leaf);
    }

    @FXML private void toggleSave() {
        if (rbSAX.isSelected()) {
            saveButton.setDisable(true);

        }
        else {
            saveButton.setDisable(false);
        }
    }

    @FXML private void setID() {
        branch.setId(Integer.valueOf(tfID.getText()));
    }
    @FXML private void setColor() {
        branch.setColor(tfColor.getText());
    }
    @FXML private void setTree() {
        branch.setTree(tfTree.getText());
    }

    @FXML private void open() {
        File file = fileChooser.showOpenDialog(tableView.getScene().getWindow());
        if (file == null) return;
        if (rbDOM.isSelected())
            openDOM(file);
        else if (rbSAX.isSelected())
            openSAX(file);
        else if (rbBinary.isSelected())
            openBinary(file);
    }

    @FXML private void save() {
        File file = fileChooser.showSaveDialog(tableView.getScene().getWindow());
        if (file == null) return;
        if (rbDOM.isSelected())
            saveDOM(file);
        else if (rbBinary.isSelected())
            saveBinary(file);
    }

    private void updateView() {
        tableView.getItems().clear();
        for (Leaf leaf : branch.getList()) {
            tableView.getItems().add(leaf);
        }
        String
                id = String.valueOf(branch.getId()),
                Color = branch.getColor(),
                tree = branch.getTree();
        tfID.setText(id);
        tfColor.setText(Color);
        tfTree.setText(tree);
    }

    private void openDOM(File file) {
        try {
            if(validateXMLByXSD(file)) {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                docFactory.setIgnoringElementContentWhitespace(true);
                DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                Element root = doc.getDocumentElement();
                root.normalize();
                List<Leaf> list = new ArrayList<>();
                String
                        id = root.getAttribute("branchid"),
                        color = root.getAttribute("color"),
                        tree = root.getAttribute("tree");
                branch = new Branch(list, Integer.valueOf(id), color, tree);
                NodeList nodeList = doc.getElementsByTagName("leaf");
                for (int i = 0; i < nodeList.getLength(); ++i) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String
                                leafColor = element.getElementsByTagName("leafColor").item(0).getTextContent(),
                                quantity = element.getElementsByTagName("quantity").item(0).getTextContent();
                        list.add(new Leaf(leafColor, Integer.valueOf(quantity)));
                    }
                }
                updateView();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                     valid
                );
                alert.setTitle("Info");
                alert.setResizable(true);
                alert.setHeaderText("Doc isn't valid");
                alert.showAndWait();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void saveDOM(File file) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = branch.createElement(doc);
            for (Leaf leaf : branch.getList()) {
                Element element = leaf.createElement(doc);
                root.appendChild(element);
            }
            doc.appendChild(root);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result;
            result = new StreamResult(file);
            transformer.transform(source, result);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openBinary(File file) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            branch = (Branch) ois.readObject();
            updateView();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void saveBinary(File file) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(branch);
            outputStream.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openSAX(File file) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            parser.parse(file, handler);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Number of branches: "+handler.getBranchNumber()+
                            "\nTotal leaves: "+handler.getTotal()+
                            "\nAverage: "+handler.getAverage(), ButtonType.OK);
            alert.setTitle("Info");
            alert.setHeaderText("Some Information");
            alert.showAndWait();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}