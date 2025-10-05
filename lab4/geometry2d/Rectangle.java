package geometry2d;

public class Rectangle implements Figure
{
    private double width;
    private double length;

    public Rectangle(double width, double length)
    {
        this.width = width;
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
