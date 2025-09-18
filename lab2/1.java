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

	//num 2
	    public static double distance(double x1, double y1, double x2, double y2) 
    {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
	public static void Num2()
	{
	    double x1,x2,y1,y2,answer;
		Scanner in = new Scanner(System.in);
        System.out.print("First Cord x: ");
        x1 = in.nextInt();
        System.out.print("y: ");
        y1 = in.nextInt();
        System.out.print("Second Cord x: ");
        x2 = in.nextInt();
        System.out.print("y: ");
        y2 = in.nextInt();
        answer = distance(x1, y1, x2, y2);
	System.out.print("answer = "+answer);
	}
	public static void main (String[] args) throws java.lang.Exception
	{
		System.out.println("Number 1");
		Num1();
		System.out.println("Number 2");
		Num2();
	}
}

