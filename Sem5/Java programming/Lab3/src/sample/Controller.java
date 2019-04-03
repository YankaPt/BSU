package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller {
    private static final Locale EN_LOCALE = new Locale("en");
    private static final Locale BY_LOCALE = new Locale("by");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy  HH:mm:ss", EN_LOCALE);
    private Currency currency = Currency.getInstance(Locale.US);
    private ReflectionDriver reflectionDriver;
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("sample.MyResources", EN_LOCALE);
    @FXML
    private TextField textField;
    @FXML
    private ListView<Method> listView;
    @FXML
    private Button button;
    @FXML
    private Label label;
    @FXML
    private TextField argsField;
    @FXML
    private Label classLabel;
    @FXML
    private Label argsLabel;
    @FXML
    private Button invokeButton;
    @FXML
    private RadioButton enButton;
    @FXML
    private RadioButton byButton;
    @FXML
    private Label dateLabel;
    @FXML
    private Label currencyLabel;

    @FXML
    public void initialize() {
        classLabel.setText(resourceBundle.getString("textClass"));
        argsLabel.setText(resourceBundle.getString("textArgs"));
        button.setText(resourceBundle.getString("buttonName1"));
        invokeButton.setText(resourceBundle.getString("buttonName2"));
        dateLabel.setText(dateFormat.format(Calendar.getInstance().getTime()));
        currencyLabel.setText(currency.getCurrencyCode());
    }

    @FXML public void getMethods() {
        String className = textField.getText();
        try {
            reflectionDriver = new ReflectionDriver(className);
            listView.setItems(FXCollections.observableArrayList(reflectionDriver.getMethods()));
            listView.refresh();
            argsField.setDisable(false);
            invokeButton.setDisable(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML public void invokeMethod() {
        Method selectedMethod = listView.getSelectionModel().getSelectedItem();
        String[] args = argsField.getText().split(", ");
        if (args[0].isEmpty())
            args = new String[0];
        try {
            Object[] argsAsObjects = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                try {
                    argsAsObjects[i] = Integer.parseInt(args[i]);
                } catch (NumberFormatException ex) {
                    argsAsObjects[i] = args[i];
                }
            }
            label.setText(reflectionDriver.invokeMethod(selectedMethod, argsAsObjects).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML public void setBundle() {
        if (enButton.isSelected()) {
            resourceBundle = ResourceBundle.getBundle("sample.MyResources", EN_LOCALE);
            dateFormat = new SimpleDateFormat("dd MMMM yyyy  HH:mm:ss", EN_LOCALE);
            currency = Currency.getInstance(Locale.US);
            initialize();
        } else {
            resourceBundle = ResourceBundle.getBundle("sample.MyResources", BY_LOCALE);
            dateFormat = new SimpleDateFormat("dd MMMM yyyy  HH:mm:ss", new Locale("be"));
            currency = Currency.getInstance(new Locale("be", "BY"));
            initialize();
        }
    }
}
