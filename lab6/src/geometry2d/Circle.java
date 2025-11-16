package geometry2d;

import Exceptions.InvalidZero;
import Exceptions.InvalidBelowZero;

import java.io.IOException;
import java.util.logging.*;

public class Circle implements Figure
{
    private double radius;

    private static Logger logger = Logger.getLogger(Circle.class.getName());
    private static String LOG_FILE = "figures.log";
    static
    {
        Handler fh = null;
        try {
            fh = new FileHandler(LOG_FILE, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fh.setFormatter(new XMLFormatter());
        fh.setLevel(Level.SEVERE);
        logger.addHandler(fh);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.SEVERE);
    }


    public Circle(double radius) {
        if (radius == 0)
        {
            logger.log(Level.SEVERE, "Error create circle with radius={0}", radius);
            throw new InvalidZero("Radius = 0");
        }
        if (radius < 0)
        {
            logger.log(Level.SEVERE, "Error create circle with radius={0}", radius);
            throw new InvalidBelowZero("Radius < 0");
        }
        this.radius = radius;
        logger.log(Level.SEVERE, "Created circle with radius={0}", radius);
    }
    
    public double Area()
    {
        logger.log(Level.SEVERE, "double Circle Area={0}", 3.14 * radius * radius);
        return 3.14 * radius * radius;
    }
    
    public void Show()
    {
        System.out.println("Radius = " + radius + ", Area = " + Area());
        logger.log(Level.SEVERE, "void Show: radius={0}", radius);
    }
}
