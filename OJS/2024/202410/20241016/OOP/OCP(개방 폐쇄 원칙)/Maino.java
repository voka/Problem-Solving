// 개방 폐쇄 원칙을 준수하는 예제

// 도형 인터페이스
interface Shape {
    double calculateArea();
}

// 원 클래스
class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// 사각형 클래스
class Rectangle implements Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }
}

// 면적 계산기
class AreaCalculator {
    public double calculateArea(Shape shape) {
        return shape.calculateArea();
    }
}

public class Maino {
    public static void main(String[] args) {
        AreaCalculator calculator = new AreaCalculator();
        Shape circle = new Circle(5);
        Shape rectangle = new Rectangle(5, 10);

        System.out.println("Circle area: " + calculator.calculateArea(circle));
        System.out.println("Rectangle area: " + calculator.calculateArea(rectangle));

        // 새로운 도형 추가 시 기존 코드를 수정하지 않고 새로운 클래스를 추가하면 됨
        Shape triangle = new Triangle(5, 10); // Triangle 클래스는 Shape 인터페이스를 구현
        System.out.println("Triangle area: " + calculator.calculateArea(triangle));
    }
}

// 삼각형 클래스 추가 (새로운 도형)
class Triangle implements Shape {
    private double base;
    private double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
}