package sample;

import java.io.*;
import java.net.PasswordAuthentication;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleServer {
    public static void main(String[] args) throws IOException {
        ArrayList<EchoClientHandler> handlers;
        List<PasswordAuthentication> passwordAuthentications = new ArrayList<>();
        passwordAuthentications.add(new PasswordAuthentication("Yan", new char[0]));
        passwordAuthentications.add(new PasswordAuthentication("root", new char[]{'r', 'o', 'o', 't'}));
        passwordAuthentications.add(new PasswordAuthentication("Bot", new char[0]));
        StringBuffer messageHistory = new StringBuffer();
        ServerSocket serverSocket = new ServerSocket(6066);
        handlers = new ArrayList<>();
        while (true) {
            handlers.add(new EchoClientHandler(serverSocket.accept(), messageHistory, handlers, passwordAuthentications));
            handlers.get(handlers.size()-1).start();
        }
    }

    private static void sendMessages(List<EchoClientHandler> handlers) {
        handlers.forEach(EchoClientHandler::sendMessage);
    }


    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private DataOutputStream out;
        private DataInputStream in;
        private StringBuffer messageHistory;
        private List<EchoClientHandler> handlers;
        private List<PasswordAuthentication> passwordAuthentications;

        public EchoClientHandler(Socket socket, StringBuffer messageHistory, List<EchoClientHandler> handlers, List<PasswordAuthentication> passwordAuthentications) throws IOException{
            this.clientSocket = socket;
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
            this.messageHistory = messageHistory;
            this.handlers = handlers;
            this.passwordAuthentications = passwordAuthentications;
        }

        public void sendMessage() {
            try {
                out.writeUTF(messageHistory.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            try {
                System.out.println("hi");
                String inputLine;
                String login = in.readUTF();
                String password = in.readUTF();
                if (verifyLogin(passwordAuthentications, login, password)) {
                    out.writeUTF(messageHistory.toString());
                    while ((inputLine = in.readUTF()) != null) {
                        messageHistory = messageHistory.append(login + ": " + inputLine + "\n");
                        sendMessages(handlers);
                        System.out.println(inputLine);
                        if (".".equals(inputLine)) {
                            out.writeUTF("bye");
                            break;
                        }
                    }
                    System.out.println("It is over");
                } else {
                    out.writeUTF("authentication error");
                }
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    private static boolean verifyLogin(List<PasswordAuthentication> passwordAuthentications, String login, String password) {
        return passwordAuthentications.stream().anyMatch(p -> p.getUserName().equals(login) && Arrays.equals(password.toCharArray(), p.getPassword()));
    }
}