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
    private Contract contract;
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
            while(true){
                this.socket = this.serverSocket.accept();
                this.out = new PrintWriter(this.socket.getOutputStream(), true);
                this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                System.out.println("INET ADDRESS: "+this.socket.getInetAddress().toString());
                System.out.println("Local Socket ADDRESS: "+this.socket.getLocalSocketAddress().toString());
                //Otteniamo il messaggio e lo convertiamo
                Message m = JSONConverter.toObject(this.in.readLine(), Message.class);
                validateMessage(m);
                this.socket.close();
            }       
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

    public void validateMessage(Message message){
        switch (message.getmessageType()) {
            case INIT:
                //Download contract
                if(message.getPayload() instanceof InitMessage){
                    InitMessage content = (InitMessage) message.getPayload();
                    this.contract = content.getContract();
                }
                break;
        
            case PoC:
                if(message.getPayload() instanceof pocMessage){
                    pocMessage content = (pocMessage) message.getPayload();
                    content.verify();
                }
                break;

            case AC:
                if(message.getPayload() instanceof ACMessage){
                    ACMessage content = (ACMessage) message.getPayload();
                }
                break;

            case ScU:
                if(message.getPayload() instanceof ScUMessage){
                    ScUMessage content = (ScUMessage) message.getPayload();
                }
                break;
            default:
                System.out.println("Type not recognized");
                break;
        }

        this.client.runClient();
        System.out.println("Message sent : " + message);
        //System.out.println("Message GSON sent : " message.toJson());
        this.client.sendMessage(String.valueOf(message));
        this.client.stopConnection();
    }

    public int proofOfComputation(String message){
        return Integer.parseInt(message)+1;
    }
}
