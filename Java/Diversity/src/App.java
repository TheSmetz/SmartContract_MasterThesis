import classes.Server;

public class App {

    public static void main(String[] args) throws Exception {
        Server s = new Server(1111);
        s.setClient(5555, "10.0.2.208");
        s.runServer();
    }
}
