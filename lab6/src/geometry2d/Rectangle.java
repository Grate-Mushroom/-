package geometry2d;

import Exceptions.InvalidBelowZero;
import Exceptions.InvalidZero;

import java.io.IOException;
import java.util.logging.*;

public class Rectangle implements Figure
{
    private double width;
    private double length;

    private static Logger logger = Logger.getLogger(Rectangle.class.getName());
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
        fh.setLevel(Level.INFO);
        logger.addHandler(fh);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.INFO);
    }

    public Rectangle(double width, double length)
    {
        if (width == 0)
        {
            logger.log(Level.INFO, "Error create Rectangle with width={0}", width);
            throw new InvalidZero("Width = 0");
        }
        if (width < 0)
        {
            logger.log(Level.INFO, "Error create Rectangle with width={0}", width);
            throw new InvalidBelowZero("Width < 0");
        }
        this.width = width;
        if (length == 0)
        {
            logger.log(Level.INFO, "Error create Rectangle with length={0}", width);
            throw new InvalidZero("Length = 0");
        }
        if (length < 0)
        {
            logger.log(Level.INFO, "Error create Rectangle with length={0}", width);
            throw new InvalidBelowZero("Length < 0");
        }
        this.length = length;
        logger.log(Level.INFO, "Create Rectangle with: width={0}, height={1}", new Object[]{width, length});
    }

    @Override
    public double Area()
    {
        logger.log(Level.INFO, "double Rectangle Area={0}", width * length);
        return width * length;
    }

    @Override
    public void Show()
    {
        System.out.println("Width = " + width + ", Height = " + length + ", Area = " + Area());
        logger.log(Level.INFO, "Rectangle shown: width={0}, height={1}, area={2}", new Object[]{width, length, Area()});
    }
}
