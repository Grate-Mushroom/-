package test;

import java.io.*;

import geometry3d.Cylinder;
import geometry2d.Figure;
import geometry2d.Circle;
import geometry2d.Rectangle;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        DataInputStream in = new DataInputStream(System.in);
        System.out.print("Print circle radius: ");
        int r = Integer.parseInt(in.readLine());
        Circle circle = new Circle(r);
        System.out.println("Circle:");
        circle.Show();

        System.out.print("\nPrint Rectangle weight: ");
        int w = Integer.parseInt(in.readLine());
        System.out.print("Print Rectangle length: ");
        int l = Integer.parseInt(in.readLine());
        Rectangle rectangle = new Rectangle(w, l);
        System.out.println("Rectangle:");
        rectangle.Show();

        System.out.print("\nPrint Cylinder height: ");
        int h = Integer.parseInt(in.readLine());
        Cylinder cylinder1 = new Cylinder(circle, h);
        Cylinder cylinder2 = new Cylinder(rectangle, h);
        System.out.println("Circle Cylinder:");
        cylinder1.Show();
        System.out.println("Rectangle Cylinder");
        cylinder2.Show();
    }

}
