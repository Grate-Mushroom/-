import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.Scanner;


class Codechef
{
    public static double power(double a, int n) 
    {
        if (n == 0) {
            return 1.0;
        }
        return a * power(a, n - 1);
    }
	public static void main (String[] args) throws java.lang.Exception
	{
	    double a;
	    int n;
		Scanner in = new Scanner(System.in);
		System.out.print("a = ");
		a = in.nextDouble();
		System.out.print("n = ");
		n = Math.abs(in.nextInt());
		a=power(a,n);
        System.out.println("a^n = "+a);
	}
        
}
