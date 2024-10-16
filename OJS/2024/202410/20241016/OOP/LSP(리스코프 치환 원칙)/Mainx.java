// 리스코프 치환 원칙 -> 자식 클래스가 부모 클래스를 대체할 수 있어야 한다.
// -> 부모 클래스를 사용하는 모든 코드가 자식 클래스로 대체되어도 프로그램이 정상적으로 동작해야 한다.
// -> 자식 클래스는 부모 클래스의 행동을 그대로 유지해야 하고, 기본적인 부분은 변경되지 않아야 한다.
// 아래 예제에서 Ostrich는 Bird를 부모로 삼고 있지만 fly 메소드를 사용할때 예외를 발생시키므로 리스코프 치환원칙을 지키지 않고 있다.
class Bird {
    public void fly() {
        System.out.println("Flying");
    }
}

class Sparrow extends Bird {
}

class Ostrich extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Ostrich can't fly");
    }
}

// 사용 예
public class Mainx {
    public static void main(String[] args) {
        Bird myBird = new Ostrich();
        myBird.fly(); // 예외 발생
    }
}