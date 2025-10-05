package geometry2d;

public class Circle implements Figure
{
    private double radius;

    public Circle(double radius)
    {
        this.radius = radius;
    }

    @Override
    public double Area()
    {
        return 3.14 * radius * radius;
    }

    @Override
    public void Show()
    {
        System.out.println("Radius = " + radius + ", Area = " + Area());
    }
}
