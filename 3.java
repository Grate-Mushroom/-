import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.Scanner;


class Codechef
{
    public static double power(double a, int n) 
    {
        double pow=1;
        for (int i = 0;i<Math.abs(n) ;i++ ) 
        {
            pow*=a;
        }
        if (n<0) pow=1/pow;
        return pow;
    }
	public static void main (String[] args) throws java.lang.Exception
	{
	    double a;
	    int n;
		Scanner in = new Scanner(System.in);
		System.out.print("a = ");
		a = in.nextDouble();
		System.out.print("n = ");
		n = in.nextInt();
		a=power(a,n);
        System.out.println("a^n = "+a);
	    
	}
        
}
