package test;

import java.io.*;

import java.lang.NumberFormatException;
import Exceptions.InvalidZero;
import Exceptions.InvalidBelowZero;
import geometry3d.Cylinder;
import geometry2d.Circle;
import geometry2d.Rectangle;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        try
        {
            DataInputStream in = new DataInputStream(System.in);
            System.out.print("Print circle radius: ");
            double r = Double.parseDouble(in.readLine());
            Circle circle = new Circle(r);
            System.out.println("Circle:");
            circle.Show();

            System.out.print("\nPrint Rectangle weight: ");
            double w = Double.parseDouble(in.readLine());
            System.out.print("Print Rectangle length: ");
            double l = Double.parseDouble(in.readLine());
            Rectangle rectangle = new Rectangle(w, l);
            System.out.println("Rectangle:");
            rectangle.Show();

            System.out.print("\nPrint Cylinder height: ");
            double h = Double.parseDouble(in.readLine());
            Cylinder cylinder1 = new Cylinder(circle, h);
            Cylinder cylinder2 = new Cylinder(rectangle, h);
            System.out.println("Circle Cylinder:");
            cylinder1.Show();
            System.out.println("Rectangle Cylinder");
            cylinder2.Show();
        }
        catch (InvalidZero | NumberFormatException | InvalidBelowZero e)
        {
            System.out.println("Exception. " + e.getMessage() + " try another value");
        }
    }
}
