package http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HttpServer {
    DataOutputStream webpage;
    DataInputStream ipaddres;
    Socket Client;
    FileReader page;
    BufferedReader writedomain;
    Scanner s;
    static ServerSocket sock;
    public final static int port = 6999;

    public void net(){
        try{
            System.out.println("Connecting....");

            Socket httpclient = sock.accept();
            System.out.println("Connected to client");
            ipaddres = new DataInputStream(httpclient.getInputStream());
            String ip = ipaddres.readUTF();

            switch(ip){
                case "32.37.14.143":
                    webpage = new DataOutputStream(httpclient.getOutputStream());
                    writedomain = new BufferedReader(new FileReader("Client.txt"));
                    String paagedata = writedomain.readLine();
                    System.out.println("the data in Client.txt file is : "+paagedata);
                    webpage.writeUTF(paagedata);
                    webpage.flush();
                    System.out.print("Send data.");
                    break;
            }
        }catch(IOException f){}
    }
    public static void main(String args[]){
        HttpServer jx = new HttpServer();
        try {
            sock = new ServerSocket(port);
            System.out.println("HTTP SERVER STARTED");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while(true){
            jx.net();
        }

    }
}