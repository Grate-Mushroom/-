package test;

import java.io.*;

import java.lang.NumberFormatException;
import Exceptions.InvalidZero;
import Exceptions.InvalidBelowZero;
import geometry3d.Cylinder;
import geometry2d.Circle;
import geometry2d.Rectangle;
import java.util.logging.*;
public class Main
{
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    static {
        Logger root = Logger.getLogger("");
        for (Handler h : root.getHandlers()) {
            root.removeHandler(h);
        }
        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new SimpleFormatter());
        ch.setLevel(Level.FINE);
        root.addHandler(ch);
        root.setLevel(Level.FINE);
        logger.setLevel(Level.FINE);
    }

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
        catch (InvalidZero e)
        {
            logger.log(Level.FINE,"Exception wrong value: {0} ", e.getMessage());
        }
        catch (NumberFormatException e)
        {
            logger.log(Level.FINE,"Exception wrong value: {0} ", e.getMessage());
        }
        catch (InvalidBelowZero e)
        {
            logger.log(Level.FINE,"Exception wrong value: {0} ", e.getMessage());
        }
        logger.log(Level.FINE, "End Main");
    }
}
