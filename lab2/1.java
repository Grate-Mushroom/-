import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.Scanner;

class Codechef
{
	//num 1
	public static void Num1()
	{
		int tri[] = new int[3];
	    int sum = 0;
	    int max = 0;
		Scanner in = new Scanner(System.in);
        System.out.print("Triangle: ");
        for (int i =0;i<3 ;i++ ) 
			tri[i] = in.nextInt();
        in.close();
        for (int i : tri)
        {
            sum+=i;
            if (i>max){max=i;} 
        }
        if (sum>2*max) System.out.println("Triangle");
        else System.out.println("Not Triangle");
	}
	
	public static void main (String[] args) throws java.lang.Exception
	{
		System.out.println("Number 1");
		Num1();
	}
}
