package classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import classes.MessageContents.*;
import common.Ansi;
import encrypt.ECC;
import encrypt.JSONConverter;
import encrypt.MSGpack;
import storage.LocalStorage;

public class Server {

    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private Client client;
    private Contract contract;
    private OutputStream out;
    private InputStream in;
    private List<POCSigned> pocSigneds = new ArrayList<POCSigned>();
    private int numberOfNodes;

    public Server(int port) {
        this.port = port;
        this.numberOfNodes = 2;
        //TODO ottenere numero nodi backend
    }

    public void setClient(int port, String ip) {
        this.client = new Client(port, ip);
    }

    public void runServer() {
        System.out.println(Ansi.ANSI_BLUE+"Try running server"+Ansi.ANSI_RESET);
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println(Ansi.ANSI_GREEN + "Running server on " + this.serverSocket.getLocalSocketAddress()
                    + Ansi.ANSI_RESET);
            System.out.println("Waiting for messages");
            while (true) {
                this.socket = this.serverSocket.accept();
                this.out = this.socket.getOutputStream();
                this.in = this.socket.getInputStream();
                String msgString = MSGpack.deserialize(this.in.readAllBytes());
                validateMessage(msgString);
                this.socket.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void stop() {
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
            this.serverSocket.close();
        } catch (Exception e) {
            System.out.println(Ansi.ANSI_RED+"Error on stopping server"+Ansi.ANSI_RESET);
        }
    }

    public void validateMessage(String message) {
        GenericMessage m = JSONConverter.toObject(message, GenericMessage.class);
        Type msgType;
        System.out.println("Received message " + m.getmessageType());
        switch (m.getmessageType()) {

            case INIT:
                // Download contract
                msgType = new TypeToken<Message<InitMessage>>() {
                }.getType();
                Message<InitMessage> initMessage = JSONConverter.toObject(message, msgType);
                if (initMessage.getmessageContent() instanceof InitMessage) {
                    InitMessage content = initMessage.getmessageContent();
                    this.contract = content.getContract();
                    POCMessage pocMsg = new POCMessage();
                    Integer[] w = { 1, 2, 3 };
                    LocalStorage.writeFile("window", JSONConverter.toJSON(w));
                    pocMsg.generate(port, 3, this.contract);
                    sendMessage(new Message<POCMessage>(MessageType.PoC, pocMsg));
                }
                break;

            case PoC:
                msgType = new TypeToken<Message<POCMessage>>() {
                }.getType();
                Message<POCMessage> pocMessage = JSONConverter.toObject(message, msgType);
                if (pocMessage.getmessageContent() instanceof POCMessage) {
                    POCMessage content = pocMessage.getmessageContent();
                    if (content.verify()) {
                        if (content.getContent().getId() != this.port) {
                            content.setPublicKeySender(ECC.getPublicKey().getEncoded());
                            content.setPrevContent(content.getSignedMessage());
                            content.setSignedMessage(ECC.encrypt(JSONConverter.toJSON(content.getSignedMessage())));
                            sendMessage(new Message<POCMessage>(MessageType.PoC, content));
                        } else {
                            POCSigned pocs = new POCSigned();
                            pocs.setPocContent(content.getContent());
                            pocs.setSignedMessage(content.getSignedMessage());
                            this.pocSigneds.add(pocs);
                            sendMessage(new Message<POCSigned>(MessageType.PoCSigned, pocs));
                        }
                    } else {
                        System.err.println("Error on validate signature PoC");
                    }
                }
                break;

            case PoCSigned:
                msgType = new TypeToken<Message<POCSigned>>() {
                }.getType();
                Message<POCSigned> pocSignedMessage = JSONConverter.toObject(message, msgType);
                if (pocSignedMessage.getmessageContent() instanceof POCSigned) {
                    POCSigned pocSigned = pocSignedMessage.getmessageContent();
                    if(pocSigned.getPocContent().getId()!=this.port){
                        this.pocSigneds.add(pocSigned);
                        sendMessage(pocSignedMessage);
                    }
                    if (this.pocSigneds.size() == this.numberOfNodes){
                        System.out.println(Ansi.ANSI_BLUE+"Checking Consensus"+Ansi.ANSI_RESET);
                        int res = POCSigned.consensus(this.pocSigneds);
                        if(res != -99){
                            System.out.println(Ansi.ANSI_YELLOW+"Opening Dispute against" + res + Ansi.ANSI_RESET);
                        }
                    }
                }
                break;

            case AC:
                //TODO
                msgType = new TypeToken<Message<ACMessage>>() {
                }.getType();
                Message<ACMessage> acMessage = JSONConverter.toObject(message, msgType);
                if (acMessage.getmessageContent() instanceof ACMessage) {
                    ACMessage content = acMessage.getmessageContent();
                }
                break;

            case ScU:
                //TODO
                msgType = new TypeToken<Message<ScUMessage>>() {
                }.getType();
                Message<ScUMessage> scuMessage = JSONConverter.toObject(message, msgType);
                if (scuMessage.getmessageContent() instanceof ScUMessage) {
                    ScUMessage content = scuMessage.getmessageContent();
                }
                break;

            default:
                System.out.println(Ansi.ANSI_YELLOW+"Type not recognized"+Ansi.ANSI_RESET);
                break;
        }
    }

    private <T extends MessageContent> void sendMessage(Message<T> msg) {
        if (this.client.runClient()) {
            System.out.println(Ansi.ANSI_BLUE + "Trying to send " + msg.getmessageType() + Ansi.ANSI_RESET);
            if (this.client.sendMessage(JSONConverter.toJSON(msg))) {
                this.client.stopConnection();
            }
        }
    }

}
