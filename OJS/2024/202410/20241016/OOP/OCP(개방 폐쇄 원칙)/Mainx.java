// 개방 폐쇄 원칙을 준수하지 않는 예제
// -> 새로운 도형을 추가하게 되면 면적계산기 클래스의 코드를 수정해야 함.
//이로 인해 기존 코드를 변경하게 되면 원칙에 위배되고 이로 인해 버그 발생 가능성이 높아지고 코드의 유지보수성은 떨어짐

// 기존 클래스
class Shape {
    public String type;

    public Shape(String type) {
        this.type = type;
    }
}

// 면적 계산기
class AreaCalculator {
    public double calculateArea(Shape shape) {
        if (shape.type.equals("circle")) {
            // 원의 면적 계산
            return Math.PI * 5 * 5; // 임의의 반지름 5
        } else if (shape.type.equals("rectangle")) {
            // 사각형의 면적 계산
            return 5 * 10; // 임의의 가로, 세로
        }
        return 0;
    }
}

public class Mainx{
    public static void main(String[] args) {
        AreaCalculator calculator = new AreaCalculator();
        Shape circle = new Shape("circle");
        Shape rectangle = new Shape("rectangle");

        System.out.println("Circle area: " + calculator.calculateArea(circle));
        System.out.println("Rectangle area: " + calculator.calculateArea(rectangle));
    }
}
