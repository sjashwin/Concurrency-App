package app;

import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;

class ServerController extends Thread{
    private Socket client;

    /*
        ServerController constructor
    */
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
            reader.close();
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
            write.close();
        } catch (IOException err) {
            System.out.println("Error reading sending message to client: " + err.getMessage());
        }
    }

    /*
        Writing to a file. Transfers file from client to server.
        Params:
            path - File path
            data - Data to be written to the file
    */
    void WriteToFile(String path, String data){
        Path file = Paths.get(path);
        try{
            OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(
                    file, 
                    java.nio.file.StandardOpenOption.CREATE, 
                    java.nio.file.StandardOpenOption.APPEND
                )
            );
            out.write(data.getBytes(), 0, data.length());
            out.flush();
            out.close();
        }catch(IOException err){
            System.err.println("Error writing to file:"+err.getMessage());
        }
    }

    /*
        Returns the details of last modified
    */
    long GetLastModified(String path){
        Path file = Paths.get(path);
        return (Files.exists(file))?file.toFile().lastModified():null;
    }
}