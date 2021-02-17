package classes;

import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    
    private int port;
    private String ip;
    private Socket clientSocket;
    private PrintWriter out;

    public Client(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public void runClient() {
        try {
            this.clientSocket = new Socket(this.ip, this.port);
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println("Error on running client");
        }
    }

    public void sendMessage(String msg) {
        try {
            this.out.write(msg);
            this.out.flush();
        } catch (Exception e) {
            System.out.println("Error on sending message to client");
        }
    }

    public void stopConnection() {
        try {
            this.out.close();
            this.clientSocket.close();
        } catch (Exception e) {
            System.out.println("Error on closing client connection");
        }
    }
}
