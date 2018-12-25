package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class Controller {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button connectButton;
    @FXML
    private Button sendButton;

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private InputListener inputListener;
    private String login;

    @FXML
    private void connectToServer() throws IOException {
        if (socket != null) {
            inputStream.close();
            outputStream.close();
            socket.close();
        }
        socket = new Socket("localhost", 6066);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        login = loginField.getText();
        outputStream.writeUTF(login);
        outputStream.writeUTF(passwordField.getText());
        if (inputStream.readUTF().equals("authentication error")) {
            chatArea.setText("Connection failed");
        } else {
            sendButton.setDisable(false);
            inputListener = new InputListener(inputStream, chatArea);
            inputListener.start();
        }
    }

    @FXML
    private void sendMessage() throws IOException {
        outputStream.writeUTF(messageField.getText());
        messageField.clear();
    }

    private static class InputListener extends Thread {
        private DataInputStream inputStream;
        private TextArea chatArea;

        public InputListener(DataInputStream inputStream, TextArea chatArea) {
            this.inputStream = inputStream;
            this.chatArea = chatArea;
        }

        @Override
        public void run() {
            try {
                System.out.println("hi");
                String inputLine;
                while ((inputLine = inputStream.readUTF()) != null) {
                    chatArea.setText(inputLine);
                }
                System.out.println("It is over");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
