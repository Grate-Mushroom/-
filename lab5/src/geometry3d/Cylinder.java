package geometry3d;

import Exceptions.InvalidBelowZero;
import Exceptions.InvalidZero;
import geometry2d.Figure;

public class Cylinder
{
    private Figure base;
    private double height;

    public Cylinder(Figure base, double height) throws InvalidZero, InvalidBelowZero
    {
        this.base = base;
        if (height == 0)
        {
            throw new InvalidZero("Height = 0");
        }
        if (height < 0)
        {
            throw new InvalidBelowZero("Height < 0");
        }
        this.height = height;
    }

    public double Volume()
    {
        return base.Area() * height;
    }

    public void Show()
    {
        System.out.println("Height = " + height + ", Volume = " + Volume());
        base.Show();
    }
}
