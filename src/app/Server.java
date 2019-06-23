package app;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

class Server {
    private int PORT = 7771;
    private ServerSocket server;
    private ArrayList<Socket> clientBucket;
    
    // Server constructor
    Server() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server has started at port: " + Integer.toString(server.getLocalPort()));
        } catch (IOException err) {
            System.err.println("Error stating server: " + err.getMessage());
            System.exit(0);
        }finally{
            /*
                Adds a shutdown hook to make sure that server socket has been closed.
                This runs a separate thread.
            */
            clientBucket = new ArrayList<Socket>();
            Runtime.getRuntime().addShutdownHook(new Thread(){
                public void run(){
                    try{
                        server.close();
                        System.out.println("Server has stopped successfully");
                    }catch(IOException err){
                        System.err.println("Server could not be stopped"+err.getMessage());
                    }
                }
            });
        }
        while(true){
                Socket client = listenPort();
                System.out.println("New Client has been connected");
                clientBucket.add(client);
                ServerController controller = new ServerController(client);
                // Running as a deamon so we close the controller threads.
                controller.setDaemon(true);
                controller.start();
        }
    }

    // Listens to a port. Use this function to accept incoming connections
    Socket listenPort() {
        try {
            return server.accept();
        } catch (IOException err) {
            System.err.println("Error accepting incoming connection: " + err.getMessage());
            return null;
        }
    }

    public static void main(String args[]) {
        new Server();
    }
}