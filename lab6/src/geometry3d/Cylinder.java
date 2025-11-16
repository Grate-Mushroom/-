package geometry3d;

import Exceptions.InvalidBelowZero;
import Exceptions.InvalidZero;
import geometry2d.Circle;
import geometry2d.Figure;

import java.io.IOException;
import java.util.logging.*;

public class Cylinder
{
    private Figure base;
    private double height;

    private static final Logger logger = Logger.getLogger(Cylinder.class.getName());
    private static final String LOG_FILE = "cylinder.log";
    static
    {
        Handler fh = null;
        try {
            fh = new FileHandler(LOG_FILE, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fh.setFormatter(new XMLFormatter());
        fh.setLevel(Level.FINEST);
        logger.addHandler(fh);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.FINEST);
    }
    public Cylinder(Figure base, double height)
    {
        this.base = base;
        if (height == 0)
        {
            logger.log(Level.FINEST, "Error create base with height={0}", height);
            throw new InvalidZero("height = 0");
        }
        if (height < 0)
        {
            logger.log(Level.FINEST, "Error create base with height={0}", height);
            throw new InvalidBelowZero("height < 0");
        }
        this.height = height;
    }

    public double Volume()
    {
        logger.log(Level.FINEST, "Cylinder Volume={0}", base.Area() * height);
        return base.Area() * height;
    }

    public void Show()
    {
        System.out.println("Height = " + height + ", Volume = " + Volume());
        base.Show();
        logger.log(Level.FINEST, "Cylinder shown: area={0}, height={1}, volume={2}", new Object[]{base.Area(), height, Volume()});
    }
}
