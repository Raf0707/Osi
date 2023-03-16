package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.IDN;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


public class Client extends JFrame implements HyperlinkListener {
    DataOutputStream domainname;
    DataInputStream ipaddress;
    DataOutputStream httpip;
    DataInputStream page;
    Socket Client;
    Socket httpclient;
    FileWriter datain;
    BufferedReader readip;
    BufferedReader writedomain;
    Scanner s;
    public final static int port = 5788;
    public final static int prt = 6999;
    private JTextField txtURL= new JTextField("");
    JEditorPane ep = new JEditorPane();
    private JLabel lblStatus= new JLabel(" ");
    public void Client(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);     
		JPanel pnlURL = new JPanel();     
		pnlURL.setLayout(new BorderLayout());     
		pnlURL.add(new JLabel("URL: "), BorderLayout.WEST);     
		pnlURL.add(txtURL, BorderLayout.CENTER);     
		getContentPane().add(pnlURL, BorderLayout.NORTH);     
		getContentPane().add( ep, BorderLayout.CENTER);      
		getContentPane().add(lblStatus, BorderLayout.SOUTH);      
		ActionListener al = new ActionListener() {       
					public void actionPerformed(ActionEvent ae) {         
							try {           
										String url = ae.getActionCommand().toLowerCase();           
										if (url.startsWith("http://"))             
												url = url.substring(7);           
										ep.setPage("http://" + IDN.toASCII(url));         
							} catch (Exception e) {           
										e.printStackTrace();
										JOptionPane.showMessageDialog(Client.this, "Browser problem: " + e.getMessage());         
							}       
					}     
		};     
		txtURL.addActionListener(al);      
		setSize(300, 300);     
		setVisible(true);   
}   
public void hyperlinkUpdate(HyperlinkEvent hle) { 
		 HyperlinkEvent.EventType evtype = hle.getEventType(); 
		 if (evtype == HyperlinkEvent.EventType.ENTERED)               
					lblStatus.setText(hle.getURL().toString());     
		 else if (evtype == HyperlinkEvent.EventType.EXITED)                
					lblStatus.setText(" ");   
		 }  



        public void setnet(String dome){
            Boolean random =true;
            while(random){
                String domain = dome;
                try{
                    Client =new Socket("127.0.0.1",port);
                    domainname = new DataOutputStream(Client.getOutputStream());
                    domainname.writeUTF(domain);
                    domainname.flush();
                    ipaddress = new DataInputStream(Client.getInputStream());
                    String ipaddres = ipaddress.readUTF();
                    System.out.println("DNS server Resolving......" +domain);
                    System.out.println("The IP address of " +domain+" is : "+ipaddres);
                    System.out.println(domain+"          1844	          IN        A	        "+ipaddres);
                    System.out.println("SERVER:   " +Client.getLocalAddress());
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Calendar cal = Calendar.getInstance();
                    System.out.println("WHEN:   "+dateFormat.format(cal.getTime()));
                    httpclient =new Socket("127.0.0.1",prt);
                    httpip = new DataOutputStream(httpclient.getOutputStream());
                    httpip.writeUTF(ipaddres);
                    httpip.flush();
                    System.out.println("Ip address sent to http server");
                    page = new DataInputStream(httpclient.getInputStream());
                    System.out.println("Buffuring data....");
                    String info = page.readUTF();
                    System.out.println("Requested data is: ");
                    for(int i=0;i<info.length();i++){
                        if(info.charAt(i)=='<'){
                            System.out.println("\n");
                        }
                        else
                            System.out.print(info.charAt(i));
                    }
                }
                catch(IOException u){}
                System.out.println("Data Buffered Completely.");
                System.out.println("Connection Terminated.");
            }
        }


        public static void main(String args[]){
            Client hii = new Client();
            hii.Client();
        }


    }
