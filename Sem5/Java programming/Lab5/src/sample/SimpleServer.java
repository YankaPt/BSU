package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SimpleServer {
    private static ServerSocket serverSocket;
    private static String messageHistory="";
    private static ArrayList<EchoClientHandler> handlers;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(6066);
        handlers = new ArrayList<>();
        while (true) {
            handlers.add(new EchoClientHandler(serverSocket.accept()));
            handlers.get(handlers.size()-1).start();
        }
    }

    private static void sendMessages() {
        handlers.forEach(EchoClientHandler::sendMessage);
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private DataOutputStream out;
        private DataInputStream in;

        public EchoClientHandler(Socket socket) throws IOException{
            this.clientSocket = socket;
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
        }

        public void sendMessage() {
            try {
                out.writeUTF(messageHistory);
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
                if (login.equals("user1") || login.equals("user2")) {
                    out.writeUTF("Good");
                    while ((inputLine = in.readUTF()) != null) {
                        messageHistory = messageHistory.concat(login + ": " + inputLine + "\n");
                        sendMessages();
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
}