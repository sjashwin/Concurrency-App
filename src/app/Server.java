package app;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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
            try{
                Socket client = server.accept();
                System.out.println("New Client has been connected");
                clientBucket.add(client);
            }catch(IOException err){
                System.out.println(err.getMessage());
            }
            
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

    /*
        Recevies the incoming message from the client
        Params:
            soc - client soc read from
    */
    void getMessage(Socket soc) {
        try {
            // Reading an incoming message.
            BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            System.out.println(reader.readLine());
        } catch (IOException e) {
            System.err.println("Error reading incoming message: " + e.getMessage());
        }
    }

    /*
        Sends message to the respective client
        Params:
            msg - Message to be sent to the client
            soc - The client socket to which the message is sent.
    */
    void sendMessage(String msg, Socket soc) {
        try {
            // Writing to the client.
            PrintWriter write = new PrintWriter(soc.getOutputStream(), true);
            write.write(msg);
        } catch (IOException err) {
            System.out.println("Error reading sending message to client: " + err.getMessage());
        }
    }

    public static void main(String args[]) {
        new Server();
    }
}