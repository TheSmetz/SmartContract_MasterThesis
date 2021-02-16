package classes;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.*;

public class Server {

    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private Client client;
    private PrintWriter out;
    private BufferedReader in;

    public Server(int port) {
        this.port = port;
    }

    public void setClient(int port, String ip) {
        this.client = new Client(port, ip);
    }

    public void runServer() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            this.socket = this.serverSocket.accept();
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            validateMessage(this.in.readLine());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void stop(){
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
            this.serverSocket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void validateMessage(String message){
        System.out.println(message);
        this.client.runClient();
        this.client.sendMessage(message);
        this.client.stopConnection();
    }
}
