package geometry3d;

import geometry2d.Figure;

public class Cylinder
{
    private Figure base;
    private double height;

    public Cylinder(Figure base, double height)
    {
        this.base = base;
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
