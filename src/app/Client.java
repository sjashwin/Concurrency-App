package app;

import java.net.Socket;
import java.io.IOException;

class Client extends Thread{
    static int port = 7771;
    static String hostname = "127.0.0.1";
    private Socket socket;
    /*
     This is a sample client constructor 
    */
    Client(){
        try{
            socket = new Socket(hostname, port);
        }catch(IOException err){
            System.err.println("Could not connect to server"+ err.getMessage());
            System.exit(0);
        }finally{
            /*
                Adds a runtime shoutdown hook to make sure the connection socket
                is closed and not left open. If left open can cause error when re-connecting.
            */
            Runtime.getRuntime().addShutdownHook(
                new Thread(()->{
                    try{
                        socket.close();
                        System.out.println("Client has closed");
                    }catch(IOException err){
                        System.out.println("Could not close client:"+ err.getMessage());
                    }
                })
            );
        }
    }

    public void run(){
        /*
            Business logic for client goes here
        */
        System.out.println("Client is running: "+Double.toString(Thread.currentThread().getId()));
    }

    public static void main(String args[]){
        Client client = new Client();
        client.start();
    }
}