import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.Scanner;


class Codechef
{
    public static int trib(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n должен быть неотрицательным");
        }
        if (n < 2) return 0;
        if (n == 2) return 1;
        return trib(n - 1) + trib(n - 2) + trib(n - 3);
    }
	public static void main (String[] args) throws java.lang.Exception
	{
	    int n,a;
		Scanner in = new Scanner(System.in);
		System.out.print("n = ");
		n = in.nextInt();
		a=trib(n);
        System.out.println("Trobanachi number "+n+" = "+a);
	}
}
