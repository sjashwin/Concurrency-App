package app;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;

class ServerController extends Thread{
    private Socket client;
    ServerController(Socket client){
        this.client = client;
    }
    /*
        Recevies the incoming message from the client
    */
    void getMessage() {
        try {
            // Reading an incoming message.
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println(reader.readLine());
        } catch (IOException e) {
            System.err.println("Error reading incoming message: " + e.getMessage());
        }
    }

    /*
        Sends message to the respective client
        Params:
            msg - Message to be sent to the client
    */
    void sendMessage(String msg) {
        try {
            // Writing to the client.
            PrintWriter write = new PrintWriter(client.getOutputStream(), true);
            write.write(msg);
        } catch (IOException err) {
            System.out.println("Error reading sending message to client: " + err.getMessage());
        }
    }
}