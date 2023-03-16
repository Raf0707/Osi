package web;

import java.lang.*;
public class Dhcp
{
    public String A,B,C,D;
    public int n,m,i=0;
    public double host;

    public Dhcp(String netid,int subnetm)
    {
        int[] l=new int[3];

        String s = netid;
        m=subnetm;
        char[] a=new char[s.length()];
        for(i=0;i<(s.length());i++)
        {
            a[i]=s.charAt(i);
        }
        i=0;
        while(n<3)
        {
            if (a[i]=='.')
            { l[n]=i;
                n++;
            }
            i++;
        }
        host=Math.pow(2,m);
        A=new String(a,0,l[0]);
        B=new String(a,l[0]+1,(l[1]-l[0]-1));
        C=new String(a,l[1]+1,l[2]-l[1]-1);
        D=new String(a,l[2]+1,s.length()-l[2]-1);

    }


}

