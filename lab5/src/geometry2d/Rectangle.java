package geometry2d;

import Exceptions.InvalidBelowZero;
import Exceptions.InvalidZero;

public class Rectangle implements Figure
{
    private double width;
    private double length;

    public Rectangle(double width, double length) throws InvalidZero, InvalidBelowZero
    {
        if (width == 0)
        {
            throw new InvalidZero("Width = 0");
        }
        if (width < 0)
        {
            throw new InvalidBelowZero("Width < 0");
        }
        this.width = width;
        if (length == 0)
        {
            throw new InvalidZero("Length = 0");
        }
        if (length < 0)
        {
            throw new InvalidBelowZero("Length < 0");
        }
        this.length = length;
    }

    @Override
    public double Area()
    {
        return width * length;
    }

    @Override
    public void Show()
    {
        System.out.println("Width = " + width + ", Height = " + length + ", Area = " + Area());
    }
}
