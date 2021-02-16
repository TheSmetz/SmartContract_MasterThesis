package classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    
    private int port;
    private String ip;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public void runClient() {
        try {
            this.clientSocket = new Socket(this.ip, this.port);
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Error on running client");
        }
    }

    public String sendMessage(String msg) {
        String resp = "";
        try {
            resp = this.in.readLine();
        } catch (Exception e) {
            resp = "ERROR";
            System.out.println("Error on sending message to client");
        }
        return resp;
    }

    public void stopConnection() {
        try {
            this.in.close();
            this.out.close();
            this.clientSocket.close();
        } catch (Exception e) {
            System.out.println("Error on closing client connection");
        }
        
    }
}
