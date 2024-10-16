abstract class Bird {
    public abstract void move();
}

class Sparrow extends Bird {
    @Override
    public void move() {
        fly();
    }

    public void fly() {
        System.out.println("Sparrow flying");
    }
}

class Ostrich extends Bird {
    @Override
    public void move() {
        walk();
    }

    public void walk() {
        System.out.println("Ostrich walking");
    }
}

// 사용 예
public class Maino{
    public static void main(String[] args) {
        Bird mySparrow = new Sparrow();
        mySparrow.move(); // Sparrow flying

        Bird myOstrich = new Ostrich();
        myOstrich.move(); // Ostrich walking
    }
}