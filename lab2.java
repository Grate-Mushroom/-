import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.Scanner;

class lab2
{

	//num 2
	    public static double distance(double x1, double y1, double x2, double y2) 
    {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

	
	//num 3
	public static double power(double a, int n) 
    {
        double pow=1;
        for (int i = 0;i<Math.abs(n) ;i++ ) 
            pow*=a;
		
        if (n<0) pow=1/pow;
        return pow;
    }

	//num 4
	    public static double power1(double a, int n) 
    {
        if (n == 0) 
            return 1.0;
        return a * power(a, n - 1);
    }

	//num 5
	public static int trib(int n) 
	{
        if (n < 2) return 0;
        if (n == 2) return 1;
        return trib(n - 1) + trib(n - 2) + trib(n - 3);
    }
    
	public static void main (String[] args) throws java.lang.Exception
	{
	    //num 1 
		System.out.println("Number 1 проверка треугольника на существование");
		int tri[] = new int[3];
	    int sum = 0;
	    int max = 0;
	Scanner in = new Scanner(System.in);
        System.out.print("Triangle: ");
        for (int i =0;i<3 ;i++ ) 
		tri[i] = in.nextInt();
        for (int i : tri)
        {
            sum+=i;
            if (i>max){max=i;} 
        }
        if (sum>2*max) System.out.println("Triangle");
        else System.out.println("Not Triangle");
        
        //num 2
		System.out.println("\n Number 2 расстояние между точками");
		double x1,x2,y1,y2,answer;
        System.out.print("First Cord x: ");
        x1 = in.nextInt();
        System.out.print("y: ");
        y1 = in.nextInt();
        System.out.print("Second Cord x: ");
        x2 = in.nextInt();
        System.out.print("y: ");
        y2 = in.nextInt();
        answer = distance(x1, y1, x2, y2);
	    System.out.println("answer = "+answer);
	    
	    //num 3
		System.out.println("\n Number 3 возведение в степень");
	    double a;
	    int n;
		System.out.print("a = ");
		a = in.nextDouble();
		System.out.print("n = ");
		n = in.nextInt();
		a=power(a,n);
        System.out.println("a^n = "+a); 
		
		//num 4
		System.out.println("\n Number 4 возведение в степень с рекурсией");
		System.out.print("a = ");
		a = in.nextDouble();
		System.out.print("n = ");
		n = Math.abs(in.nextInt());
		a=power1(a,n);
        System.out.println("a^n = "+a);
		
		//num 5
		System.out.println("\n Number 5 трибоначи");
		int n5,a5;
		System.out.print("n = ");
		n5 = in.nextInt();
		a5=trib(n);
        System.out.println("Trobanachi number "+n5+" = "+a5);
	}

}
