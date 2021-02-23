package classes;

import java.io.PrintWriter;
import java.net.Socket;

import common.Ansi;

public class Client {
    
    private int port;
    private String ip;
    private Socket clientSocket;
    private PrintWriter out;

    public Client(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public boolean runClient() {
        try {
            this.clientSocket = new Socket(this.ip, this.port);
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            return true;
        } catch (Exception e) {
            System.err.println(Ansi.ANSI_RED+"Error on running client"+Ansi.ANSI_RESET);
            return false;
        }
    }

    public boolean sendMessage(String msg) {
        try {
            this.out.write(msg);
            this.out.flush();
            return true;
        } catch (Exception e) {
            System.err.println(Ansi.ANSI_RED+"Error on sending message to client"+Ansi.ANSI_RESET);
            return false;
        }
    }

    public void stopConnection() {
        try {
            this.out.close();
            this.clientSocket.close();
        } catch (Exception e) {
            System.err.println(Ansi.ANSI_RED+"Error on closing client connection"+Ansi.ANSI_RESET);
        }
    }
}
