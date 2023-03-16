package web;

import client.Client;

import java.util.*;
import java.lang.*;




class MySwitch
{
    node1 n1,n2,n3,n4;
    String portno,portaddress,ar,br,cr,dr;
    static int p=0;
    int mass,mass1,dmac;

    public MySwitch(String s,int m)
    {
        node1.count=2;
        n1=new node1(s,m);
        n2=new node1(s,m);
        n3=new node1(s,m);
        n4=new node1(s,m);
        portno="0"+"/"+Integer.toString(p);
        p++;
        mass=m;
        Dhcp DHCP1=new Dhcp(s,m);
        ar=Integer.toBinaryString(Integer.parseInt(DHCP1.A));
        for(int i=0;i<8-(Integer.toBinaryString(Integer.parseInt(DHCP1.A))).length();i++){ ar='0'+ar;}
        br=Integer.toBinaryString(Integer.parseInt(DHCP1.B));
        for(int i=0;i<8-(Integer.toBinaryString(Integer.parseInt(DHCP1.B))).length();i++){ br='0'+br;}
        cr=Integer.toBinaryString(Integer.parseInt(DHCP1.C));
        for(int i=0;i<8-(Integer.toBinaryString(Integer.parseInt(DHCP1.C))).length();i++){ cr='0'+cr;}
        dr=Integer.toBinaryString(Integer.parseInt(DHCP1.D)+1);
        for(int i=0;i<8-(Integer.toBinaryString(Integer.parseInt(DHCP1.D))).length();i++){ dr='0'+dr;}
        portaddress=ar+br+cr+dr;
    }



    int ack(String s)
    {
        if(Integer.parseInt(n1.d)==Integer.parseInt(s.substring(72,80),2))
        {return n1.mac;}
        else if(Integer.parseInt(n2.d)==Integer.parseInt(s.substring(72,80),2))
        {return n2.mac;}
        else if(Integer.parseInt(n3.d)==Integer.parseInt(s.substring(72,80),2))
        {return n3.mac;}
        else
        {return n4.mac;}
    }

    void send (int dmac,String s)
    {
        if(dmac==1)
        {n1.message=s;}
        if(dmac==2)
        {n2.message=s;}
        if(dmac==3)
        {n3.message=s;}
        if(dmac==4)
        {n4.message=s;}
        if(dmac==5)
        {n1.message=s;}
        if(dmac==6)
        {n2.message=s;}
        if(dmac==7)
        {n3.message=s;}
        if(dmac==8)
        {n4.message=s;}
    }

    void route(String s, MySwitch r)
    {
        if(s.substring(48,72).equals(portaddress.substring(0,mass)))
        {   dmac=ack(s);
            System.out.println(dmac);
            send(dmac ,s);
        }
        else
        {
            dmac=r.ack(s);
            System.out.println(r.dmac);
            r.send(dmac ,s);

        }
    }
}

class node1
{
    int mac,x=0,subnetmass;
    char[] r=new char[73];
    String ipaddress,message,frame,temp,a,b,c,d;
    static int count=2,count1=1;
    public node1(String s,int m)
    {
        mac=count1;
        subnetmass=m;
        Dhcp DHCP = new Dhcp(s, m);
        ipaddress=DHCP.A+"."+DHCP.B+"."+DHCP.C+"."+(Integer.parseInt(DHCP.D)+count);
        a = DHCP.A;
        b = DHCP.B;
        c = DHCP.C;
        d = Integer.toString(Integer.parseInt(DHCP.D)+count);
        count++;
        count1++;
    }
    void networklayer(node1 nod1,String Data)
    {
        frame=Data;
        temp=Integer.toBinaryString(Integer.parseInt(nod1.d));
        for(int i=0;i<8-(Integer.toBinaryString(Integer.parseInt(nod1.d))).length();i++){ temp='0'+temp;}
        frame=temp+frame;
        temp=Integer.toBinaryString(Integer.parseInt(nod1.c));
        for(int i=0;i<8-(Integer.toBinaryString(Integer.parseInt(nod1.c))).length();i++){ temp='0'+temp;}
        frame=temp+frame;
        temp=Integer.toBinaryString(Integer.parseInt(nod1.b));
        for(int i=0;i<8-(Integer.toBinaryString(Integer.parseInt(nod1.b))).length();i++){ temp='0'+temp;}
        frame=temp+frame;
        temp=Integer.toBinaryString(Integer.parseInt(nod1.a));
        for(int i=0;i<8-(Integer.toBinaryString(Integer.parseInt(nod1.a))).length();i++){ temp='0'+temp;}
        frame=temp+frame;

        temp=Integer.toBinaryString(Integer.parseInt(d));
        for(int i=0;i<(8-(Integer.toBinaryString(Integer.parseInt(d))).length());i++){ temp='0'+temp;}
        frame=temp+frame;
        temp=Integer.toBinaryString(Integer.parseInt(c));
        for(int i=0;i<(8-(Integer.toBinaryString(Integer.parseInt(c))).length());i++){ temp='0'+temp;}
        frame=temp+frame;
        temp=Integer.toBinaryString(Integer.parseInt(b));
        for(int i=0;i<(8-(Integer.toBinaryString(Integer.parseInt(b))).length());i++){ temp='0'+temp;}
        frame=temp+frame;
        temp=Integer.toBinaryString(Integer.parseInt(a));
        for(int i=0;i<(8-(Integer.toBinaryString(Integer.parseInt(a))).length());i++){ temp='0'+temp;}
        frame=temp+frame;
        datalinklayer(frame,nod1);
    }
    void datalinklayer(String s,node1 nod1)
    {

        for(int i=0;i<72;i++)
        {  r[i]=s.charAt(i);}
        for(int i=0;i<9;i++)
        {temp=new String(r,(8*i),8);
            x=x+Integer.parseInt(temp,2);
        }

        if (x>255)
        {
            temp=Integer.toBinaryString(x);
            for(int i=0;i<8;i++)
            {r[i]=temp.charAt(i+1);}
            temp =new String(r,0,8);
            x=Integer.parseInt(temp,2);
            x=x+1;
        }

        x=255-x;
        temp=Integer.toBinaryString(nod1.mac);
        for(int i=0;i<8-Integer.toBinaryString(nod1.mac).length();i++){ temp='0'+temp;}
        frame=temp+frame;
        temp=Integer.toBinaryString(mac);
        for(int i=0;i<8-Integer.toBinaryString(mac).length();i++){ temp='0'+temp;}
        frame=temp+frame;
        temp=Integer.toBinaryString(x);
        for(int i=0;i<8-Integer.toBinaryString(x).length();i++){ temp='0'+temp;}
        frame=frame+temp;

    }


}

class net
{
    public static void main(String avg[])
    {
        String S;
        int M;
        Scanner data=new Scanner(System.in);
        System.out.println("Enter net id and subnetmask for first port:");
        S=data.nextLine();
        M=data.nextInt();
        MySwitch s1=new MySwitch(S,M);
        System.out.println("Portno for first :"+s1.portno);
        System.out.println("ip address for first port :"+s1.portaddress);
        System.out.println("ip address of computer in port one");
        System.out.println(s1.n1.ipaddress+" "+s1.n1.mac);
        System.out.println(s1.n2.ipaddress+" "+s1.n2.mac);
        System.out.println(s1.n3.ipaddress+" "+s1.n3.mac);
        System.out.println(s1.n4.ipaddress+" "+s1.n4.mac);
        System.out.println("enter net id and subnetmass for second port");
        Scanner data2=new Scanner(System.in);
        S=data2.nextLine();
        M=data2.nextInt();
        MySwitch s2=new MySwitch(S,M);
        System.out.println("portno for first :"+s2.portno);
        System.out.println("ip address for first port :"+s2.portaddress);
        System.out.println("ip address of computer in port one");
        System.out.println(s2.n1.ipaddress+" "+s2.n1.mac);
        System.out.println(s2.n2.ipaddress+" "+s2.n2.mac);
        System.out.println(s2.n3.ipaddress+" "+s2.n3.mac);
        System.out.println(s2.n4.ipaddress+" "+s2.n4.mac);
        System.out.println("Select any node");
        Scanner data1=new Scanner(System.in);
        int source=data1.nextInt();

        System.out.println("enter 1 for message transfer");
        System.out.println("enter 2 for browser");
        int destination=data1.nextInt();
        if(destination ==1)
        {
            System.out.println("enter 8-bit data");
            S=data1.next();
            System.out.println("enter destination port");
            destination=data1.nextInt();
            switch (source)
            {
                case 1:
                    switch (destination)
                    {
                        case 2:
                            s1.n1.networklayer(s1.n2,S);
                            s1.route((s1.n1.frame),s2);
                            break;
                        case 3:
                            s1.n1.networklayer(s1.n3,S);
                            s1.route((s1.n1.frame),s2);
                            break;
                        case 4:
                            s1.n1.networklayer(s1.n4,S);
                            s1.route((s1.n1.frame),s2);
                            break;
                        case 5:
                            s1.n1.networklayer(s2.n1,S);
                            s1.route((s1.n1.frame),s2);
                            break;
                        case 6:
                            s1.n1.networklayer(s2.n2,S);
                            s1.route((s1.n1.frame),s2);
                            break;
                        case 7:
                            s1.n1.networklayer(s2.n3,S);
                            s1.route((s1.n1.frame),s2);
                            break;
                        case 8:
                            s1.n1.networklayer(s2.n4,S);
                            s1.route((s1.n1.frame),s2);
                            break;
                        default :
                            System.out.println("Invalid destination or source node");
                    }
                    break;
                case 2:
                    switch (destination)
                    {
                        case 1:
                            s1.n2.networklayer(s1.n1,S);
                            s1.route((s1.n2.frame),s2);
                            break;
                        case 3:
                            s1.n2.networklayer(s1.n3,S);
                            s1.route((s1.n2.frame),s2);
                            break;
                        case 4:
                            s1.n2.networklayer(s1.n4,S);
                            s1.route((s1.n2.frame),s2);
                            break;
                        case 5:
                            s1.n2.networklayer(s2.n1,S);
                            s1.route((s1.n2.frame),s2);
                            break;
                        case 6:
                            s1.n2.networklayer(s2.n2,S);
                            s1.route((s1.n2.frame),s2);
                            break;
                        case 7:
                            s1.n2.networklayer(s2.n3,S);
                            s1.route((s1.n2.frame),s2);
                            break;
                        case 8:
                            s1.n2.networklayer(s2.n4,S);
                            s1.route((s1.n2.frame),s2);
                            break;
                        default :
                            System.out.println("Invalid destination or source node");
                    }
                    break;
                case 3:
                    switch (destination)
                    {
                        case 1:
                            s1.n3.networklayer(s1.n1,S);
                            s1.route((s1.n3.frame),s2);
                            break;
                        case 2:
                            s1.n3.networklayer(s1.n2,S);
                            s1.route((s1.n3.frame),s2);
                            break;
                        case 4:
                            s1.n3.networklayer(s1.n4,S);
                            s1.route((s1.n3.frame),s2);
                            break;
                        case 5:
                            s1.n3.networklayer(s2.n1,S);
                            s1.route((s1.n3.frame),s2);
                            break;
                        case 6:
                            s1.n3.networklayer(s2.n2,S);
                            s1.route((s1.n3.frame),s2);
                            break;
                        case 7:
                            s1.n3.networklayer(s2.n3,S);
                            s1.route((s1.n3.frame),s2);
                            break;
                        case 8:
                            s1.n3.networklayer(s2.n4,S);
                            s1.route((s1.n3.frame),s2);
                            break;
                        default :
                            System.out.println("Invalid destination or source node");
                    }
                    break;
                case 4:
                    switch (destination)
                    {
                        case 1:
                            s1.n4.networklayer(s1.n1,S);
                            s1.route((s1.n4.frame),s2);
                            break;
                        case 2:
                            s1.n4.networklayer(s1.n2,S);
                            s1.route((s1.n4.frame),s2);
                            break;
                        case 3:
                            s1.n4.networklayer(s1.n3,S);
                            s1.route((s1.n4.frame),s2);
                            break;
                        case 5:
                            s1.n4.networklayer(s2.n1,S);
                            s1.route((s1.n4.frame),s2);
                            break;
                        case 6:
                            s1.n4.networklayer(s2.n2,S);
                            s1.route((s1.n4.frame),s2);
                            break;
                        case 7:
                            s1.n4.networklayer(s2.n3,S);
                            s1.route((s1.n4.frame),s2);
                        case 8:
                            s1.n4.networklayer(s2.n4,S);
                            s1.route((s1.n4.frame),s2);
                            break;
                        default :
                            System.out.println("Invalid destination or source node");
                    }
                case 5:
                    switch (destination)
                    {
                        case 1:
                            s2.n1.networklayer(s1.n1,S);
                            s2.route((s2.n1.frame),s1);
                            break;
                        case 2:
                            s2.n1.networklayer(s1.n2,S);
                            s2.route((s2.n1.frame),s1);
                            break;
                        case 3:
                            s2.n1.networklayer(s1.n3,S);
                            s2.route((s2.n1.frame),s1);
                            break;
                        case 4:
                            s2.n1.networklayer(s1.n4,S);
                            s2.route((s2.n1.frame),s1);
                            break;
                        case 6:
                            s2.n1.networklayer(s2.n2,S);
                            s2.route((s2.n1.frame),s1);
                            break;
                        case 7:
                            s2.n1.networklayer(s2.n3,S);
                            s2.route((s2.n1.frame),s1);
                            break;
                        case 8:
                            s2.n1.networklayer(s2.n4,S);
                            s2.route((s2.n1.frame),s1);
                            break;
                        default :
                            System.out.println("Invalid destination or source node");
                    }
                    break;
                case 6:
                    switch (destination)
                    {
                        case 1:
                            s2.n2.networklayer(s1.n1,S);
                            s2.route((s2.n2.frame),s1);
                            break;
                        case 2:
                            s2.n2.networklayer(s1.n2,S);
                            s2.route((s2.n2.frame),s1);
                            break;
                        case 3:
                            s2.n2.networklayer(s1.n3,S);
                            s2.route((s2.n2.frame),s1);
                            break;
                        case 4:
                            s2.n2.networklayer(s1.n4,S);
                            s2.route((s2.n2.frame),s1);
                        case 5:
                            s2.n2.networklayer(s2.n1,S);
                            s2.route((s2.n2.frame),s1);
                            break;
                        case 7:
                            s1.n2.networklayer(s2.n3,S);
                            s2.route((s2.n2.frame),s1);
                            break;
                        case 8:
                            s2.n2.networklayer(s2.n4,S);
                            s2.route((s2.n2.frame),s1);
                            break;
                        default :
                            System.out.println("Invalid destination or source node");
                    }
                    break;
                case 7:
                    switch (destination)
                    {
                        case 1:
                            s2.n3.networklayer(s1.n1,S);
                            s2.route((s2.n3.frame),s1);
                            break;
                        case 2:
                            s2.n3.networklayer(s1.n2,S);
                            s2.route((s2.n3.frame),s1);
                            break;
                        case 3:
                            s2.n3.networklayer(s1.n3,S);
                            s2.route((s2.n3.frame),s1);
                            break;
                        case 4:
                            s2.n3.networklayer(s1.n4,S);
                            s2.route((s2.n3.frame),s1);
                        case 5:
                            s2.n3.networklayer(s2.n1,S);
                            s2.route((s2.n3.frame),s1);
                            break;
                        case 6:
                            s1.n3.networklayer(s2.n2,S);
                            s2.route((s2.n3.frame),s1);
                            break;
                        case 8:
                            s2.n3.networklayer(s2.n4,S);
                            s2.route((s2.n3.frame),s1);
                            break;
                        default :
                            System.out.println("Invalid destination or source node");
                    }
                    break;
                case 8:
                    switch (destination)
                    {
                        case 1:
                            s2.n4.networklayer(s1.n1,S);
                            s2.route((s2.n4.frame),s1);
                            break;
                        case 2:
                            s2.n4.networklayer(s1.n2,S);
                            s2.route((s2.n4.frame),s1);
                            break;
                        case 3:
                            s2.n4.networklayer(s1.n3,S);
                            s2.route((s2.n4.frame),s1);
                            break;
                        case 4:
                            s2.n4.networklayer(s1.n4,S);
                            s2.route((s2.n4.frame),s1);
                        case 5:
                            s2.n4.networklayer(s2.n1,S);
                            s2.route((s2.n4.frame),s1);
                            break;
                        case 6:
                            s2.n4.networklayer(s2.n2,S);
                            s2.route((s2.n4.frame),s1);
                            break;
                        case 7:
                            s2.n4.networklayer(s2.n3,S);
                            s2.route((s2.n4.frame),s1);
                            break;
                        default :
                            System.out.println("Invalid destination or source node");
                    }

                    break;
                default :


            }


            System.out.println("message recevied by n1 "+s1.n1.message);
            System.out.println("message recevied by n2 "+s1.n2.message);
            System.out.println("message recevied by n3 "+s1.n3.message);
            System.out.println("message recevied by n4 "+s1.n4.message);
            System.out.println("message recevied by n5 "+s2.n1.message);
            System.out.println("message recevied by n6 "+s2.n2.message);
            System.out.println("message recevied by n7 "+s2.n3.message);
            System.out.println("message recevied by n8 "+s2.n4.message);
        }
        else {
            System.out.println("Enter URL : ");
            Scanner ish = new Scanner(System.in);
            String domain = ish.nextLine();
            Client c1 = new Client();
            c1.setnet(domain);
        }



    }

}