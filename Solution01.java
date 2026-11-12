import java.util.*;

interface Shape {
    String getName();
    double getArea();
    double getPerimeter();
}

class Rectangle implements Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public String getName() {
        return "rectangle";
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }
}

class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public String getName() {
        return "circle";
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
}

class Triangle implements Shape {
    private double a;
    private double b;
    private double c;

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        if(a >= b + c || b >= a + c || c >= a + b) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String getName() {
        return "triangle";
    }

    @Override
    public double getArea() {
        double s = getPerimeter() / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double getPerimeter() {
        return a + b + c;
    }
}

public class Solution01 {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        int n;
        try {
            n = Integer.parseInt(scanner.nextLine());
            if(n < 1){
                throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("error: invalid count");
            return;
        }
        Shape[] shapes = new Shape[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            try {
                shapes[count] = parseShape(scanner.nextLine());
                count++;
            }
            catch (IllegalArgumentException e) {
                System.out.println("error: " +e.getMessage());
            }
        }
        scanner.close();

        printReport(Arrays.copyOf(shapes, count));
    }

    public static Shape parseShape(String line) {
        String[] parts = line.split(" ");
        if (parts[0].equals("RECTANGLE")) {
            double width = readMeasure(parts,1);
            double height = readMeasure(parts,2);
            return new Rectangle(width, height);
        } else if (parts[0].equals("CIRCLE")) {
            double radius = readMeasure(parts,1);
            return new Circle(radius);
        } else if (parts[0].equals("TRIANGLE")) {
            double a = readMeasure(parts, 1);
            double b = readMeasure(parts, 2);
            double c = readMeasure(parts, 3);
            try {
                return new Triangle(a, b, c);
            } catch (IllegalArgumentException e) {
                if (a >= b + c) {
                    return new Triangle(a, a, a);
                } else if (b >= a + c) {
                    return new Triangle(b, b, b);
                } else {
                    return new Triangle(c, c, c);
                }
            }
        }
        else{
            throw new IllegalArgumentException("unknown shape");
        }
    }


    public static void printReport(Shape[] shapes) {
        Shape largest = null;
        for (Shape shape : shapes) {
            System.out.printf("%s: area %.2f perimeter %.2f%n", shape.getName(), shape.getArea(), shape.getPerimeter());
            if (largest == null || shape.getArea() > largest.getArea()) {
                largest = shape;
            }
        }
        System.out.printf("largest area: %s%n", largest.getName());
    }

    public static double readMeasure(String[] parts, int index){
        double value = 0;
        try {
            value = Double.parseDouble(parts[index]);
            if (value <= 0) {
                throw new IllegalArgumentException();

            }
            return value;
        }
        catch (ArrayIndexOutOfBoundsException | IllegalArgumentException | NullPointerException e){
            return 1;
        }
    }
}