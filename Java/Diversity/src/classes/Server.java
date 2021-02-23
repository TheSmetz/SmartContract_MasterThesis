package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.reflect.TypeToken;

import common.Ansi;
import encrypt.JSONConverter;
import encrypt.MSGpack;

public class Server {

    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private Client client;
    private Contract contract;
    private OutputStream out;
    private InputStream in;

    public Server(int port) {
        this.port = port;
    }

    public void setClient(int port, String ip) {
        this.client = new Client(port, ip);
    }

    public void runServer() {
        System.out.println("Try running server");
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println(Ansi.ANSI_GREEN+"Running server on "+this.serverSocket.getLocalSocketAddress()+Ansi.ANSI_RESET);
            System.out.println("Waiting for messages");
            while(true){
                this.socket = this.serverSocket.accept();
                this.out = this.socket.getOutputStream();
                this.in = this.socket.getInputStream();
                //Otteniamo il messaggio e lo convertiamo
                System.out.println("Converting message into object..");
                String msgString = MSGpack.deserialize(this.in.readAllBytes());
                validateMessage(msgString);
                this.socket.close();
            }       
        } catch (IOException e) {
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

        GenericMessage m = JSONConverter.toObject(message, GenericMessage.class);
        Type msgType;

        switch (m.getmessageType()) {
            case INIT:
                //Download contract
                msgType = new TypeToken<Message<InitMessage>>() {}.getType();
                Message<InitMessage> initMessage = JSONConverter.toObject(message, msgType);
                if(initMessage.getmessageContent() instanceof InitMessage){
                    InitMessage content = initMessage.getmessageContent();
                    this.contract = content.getContract();
                    POCMessage pocMsg = new POCMessage();
                    Integer[] w = {1,2,3};
                    pocMsg.generate(port, w, 3, this.contract);
                    sendMessage(new Message<POCMessage>(MessageType.PoC, pocMsg));
                }
                break;
        
            case PoC:
                msgType = new TypeToken<Message<POCMessage>>() {}.getType();
                Message<POCMessage> pocMessage = JSONConverter.toObject(message, msgType);
                if(pocMessage.getmessageContent() instanceof POCMessage){
                    POCMessage content = pocMessage.getmessageContent();
                    if (content.verify()){
                        if(content.getContent().getId() != this.port){
                            //TODO: firma
                            System.out.println("DA FIRMARE");
                        } else {
                            System.out.println("Mia firma");
                        }
                    }else{
                        System.err.println("Error on validate signature PoC");
                    }
                }
                break;

            case AC:
                msgType = new TypeToken<Message<ACMessage>>() {}.getType();
                Message<ACMessage> acMessage = JSONConverter.toObject(message, msgType);
                if(acMessage.getmessageContent() instanceof ACMessage){
                    ACMessage content = acMessage.getmessageContent();
                }
                break;

            case ScU:
                msgType = new TypeToken<Message<ScUMessage>>() {}.getType();
                Message<ScUMessage> scuMessage = JSONConverter.toObject(message, msgType);
                if(scuMessage.getmessageContent() instanceof ScUMessage){
                    ScUMessage content = scuMessage.getmessageContent();
                }
                break;
            default:
                System.out.println("Type not recognized");
                break;
        }
    }

    public int proofOfComputation(String message){
        return Integer.parseInt(message)+1;
    }

    private void sendMessage(Message<POCMessage> msg){
        if(this.client.runClient()){
            System.out.println("Trying to send message : ");
            System.out.println(JSONConverter.toJSON(msg));
            if(this.client.sendMessage(JSONConverter.toJSON(msg))){
                this.client.stopConnection();
            }
        }
    }

}
