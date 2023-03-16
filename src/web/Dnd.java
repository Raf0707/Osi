package web;

import java.util.Scanner;

public class Dnd {
    public String mainurl;
    public String ipv4;
    String dns(String url,String ip){
        this.mainurl = url;
        this.ipv4 = ip;
        switch(mainurl)
        {
            case "www.google.com":
                System.out.println("172.217.11.174");
                ip = "172.217.11.174";
                break;
            case "www.youtube.com":
                System.out.println("98.137.149.56");
                ip ="98.137.149.56";
                break;
        }
        return ip;
    }
    public static void main(String args[]){

        Scanner dns = new Scanner(System.in);
        String dominename = dns.nextLine();
        Dnd findip = new Dnd();
        findip.dns(dominename, null);
    }

}