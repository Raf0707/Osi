package utils;

import java.util.Scanner;

public class Dlls {
    public static void main(String args[])
    {
        try{
            try{
                int j=0;
                int k=10/j;

            }
            catch(ArithmeticException a)
            {
                System.out.println("Exception:"+a);
            }

            Scanner s = new Scanner(System.in);
            System.out.println("/---------------AT SENDER SIDE--------------/");
            System.out.println("Enter the Message");
            int x = s.nextInt();
            System.out.println("Enter the carrier");
            int y = s.nextInt();
            int z=x/y;
            System.out.println("The value is:"+z);
        }
        catch(ArithmeticException e)
        {
            System.out.println("Exception:"+e);
        }
    }

}