// 클라이언트는 자신이 사용하지 않는 메서드에 의존하지 않아야 한다.
// 특정 인터페이스는 클라이언트가 사용하지 않는 메서드에 의존하지 않도록 설계해야 한다.
// Worker가 work와 eat 메서드를 포함하고 있다. human은 둘다 구현하지만 robot에게는 eat이 필요하지 않으므로 인터페이스 분리 원칙에 위배된다.
// 이를 해결하기 위해서는 Worker 인터페이스를 더 작은 크기의 인터페이스로 분리해야 한다.

interface Worker {
    void work();
    void eat();
}

class Human implements Worker {
    @Override
    public void work() {
        System.out.println("Working");
    }

    @Override
    public void eat() {
        System.out.println("Eating");
    }
}

class Robot implements Worker {
    @Override
    public void work() {
        System.out.println("Working");
    }

    @Override
    public void eat() {
        // Robot doesn't eat, violates ISP
    }
}