import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.List;

public class lab3 
{
    public static void main(String[] args) throws IOException
    {
        //num 1
        Button b = new Button();
        DataInputStream in = new DataInputStream(System.in);
        System.out.println("Number 1 кликер");
        System.out.print("How many clicks?: ");
        int n = Integer.parseInt(in.readLine());
        for (int i = 0;i<n ;i++ ) 
            b.click();
        n=0;
        
        //num 2
        Balanse bal = new Balanse();
        bal.addRight();
        bal.addLeft();
        bal.result();
        
        //num 3
        Bell bam = new Bell();
        System.out.print("\nHow many bams?: ");
        n = Integer.parseInt(in.readLine());
        for (int i = 0;i<n ;i++ ) 
            bam.sound();
            
        //num 4
        OddEvenSeparator oes = new OddEvenSeparator();
        for (int i = 0; i<10;i++)
            oes.addNumber(i);
        oes.even();
        oes.odd();
        
        //num 5
        Table t = new Table(3, 4);
        t.setValue(0, 0, 5);
        t.setValue(1, 2, 7);
        t.setValue(2, 3, -3);
        System.out.println("Rows: " + t.rows());
        System.out.println("Cols: " + t.cols());
        System.out.println(t.toString());
        System.out.println("Average: " + t.average());
    }
}

//num 1
class Button 
{
    private int clicks = 0;

    public void click() 
    {
        clicks++;
        System.out.println("Button was clicked " + clicks + " times");
    }
}

//num 2
class Balanse
{
    private int Right=0;
    private int Left=0;
    
    public void addRight() throws IOException
    {
        DataInputStream in = new DataInputStream(System.in);
        System.out.print("Right weight: ");
        Right = Integer.parseInt(in.readLine());
    }
    public void addLeft() throws IOException
    {
        DataInputStream in = new DataInputStream(System.in);
        System.out.print("Left weight: ");
        Left = Integer.parseInt(in.readLine());
    }
    public void result()
    {
        if (Right<Left)
            System.out.print("L");
        else if (Right>Left)
            System.out.print("R");
        else if (Right==Left)
            System.out.print("=");
    }
}

//num 3
class Bell 
{
    private boolean bam = true;

    public void sound() 
    {
        if (bam)
            System.out.println("ding");
        else 
            System.out.println("dong");
        bam = !bam;
    }
}

//num 4
class OddEvenSeparator 
{
    private final List<Integer> even = new ArrayList<>();
    private final List<Integer> odd = new ArrayList<>();

    public void addNumber(int n) 
    {
        if (n % 2 == 0)
            even.add(n);
        else 
            odd.add(n);
    }

    public void even() {System.out.println("Evens: "+even);}
    public void odd() {System.out.println("Odds:: "+odd);}
}
    
//num 5
class Table 
{
    private int rows;
    private int cols;
    private int[][] table;

    public Table(int rows, int cols) 
    {
        this.rows = rows;
        this.cols = cols;
        this.table = new int[rows][cols];
    }

    public int getValue(int row, int col) 
    {
        return table[row][col];
    }

    public void setValue(int row, int col, int value) 
    {
        table[row][col] = value;
    }

    public int rows() {return rows;}
    public int cols() {return cols;}

    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) 
        {
            sb.append("[");
            for (int c = 0; c < cols; c++) {
                sb.append(table[r][c]);
                if (c < cols - 1) sb.append(", ");
            }
            sb.append("]");
            if (r < rows - 1) sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public double average() 
    {
        int sum = 0;
        int count = rows * cols;
        for (int r = 0; r < rows; r++) 
        {
            for (int c = 0; c < cols; c++) 
            {
                sum += table[r][c];
            }
        }
        return (double) sum / count;
    }
}
    
   
