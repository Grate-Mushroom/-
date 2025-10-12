package geometry2d;

import Exceptions.InvalidZero;
import Exceptions.InvalidBelowZero;

public class Circle implements Figure
{
    private double radius;

    public Circle(double radius) throws InvalidZero, InvalidBelowZero
    {
        if (radius == 0)
        {
            throw new InvalidZero("Radius = 0");
        }
        if (radius < 0)
        {
            throw new InvalidBelowZero("Radius < 0");
        }
        this.radius = radius;
    }

    
    public double Area()
    {
        return 3.14 * radius * radius;
    }

    
    public void Show()
    {
        System.out.println("Radius = " + radius + ", Area = " + Area());
    }
}
