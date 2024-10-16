import java.util.ArrayList;
import java.util.List;

class Person implements Cloneable {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 얕은 복사 (기본 제공 clone 메서드 사용)
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // 깊은 복사 (Person 객체 내부의 필드들도 복사)
    public Person deepClone() {
        return new Person(new String(this.name), this.age);
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}

public class Main {
    public static void main(String[] args) {
        // 기본 자료형 (int)
        int a = 10;
        int shallowA = a;  // 얕은 복사
        shallowA = 20;
        System.out.println("Original int: " + a);  // 10
        System.out.println("Shallow copied int: " + shallowA);  // 20

        // String 객체 (불변 객체이므로 얕은 복사가 깊은 복사처럼 작동)
        String str = "Hello";
        String shallowStr = str;  // 얕은 복사
        shallowStr = "World";
        System.out.println("Original String: " + str);  // "Hello"
        System.out.println("Shallow copied String: " + shallowStr);  // "World"

        // Person 객체 복사
        Person p1 = new Person("Alice", 25);
        try {
            // 얕은 복사
            Person shallowP1 = (Person) p1.clone();
            // 깊은 복사
            Person deepP1 = p1.deepClone();

            // 복사된 객체 수정
            shallowP1.name = "Bob";
            deepP1.name = "Charlie";

            System.out.println("Original Person: " + p1); // Alice
            System.out.println("Shallow copied Person: " + shallowP1); // Bob
            System.out.println("Deep copied Person: " + deepP1); // Charlie
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        // List 객체 복사
        List<String> originalList = new ArrayList<>();
        originalList.add("Apple");
        originalList.add("Banana");

        // 얕은 복사 (원본 리스트의 참조를 공유)
        List<String> shallowList = new ArrayList<>(originalList);
        // 깊은 복사 (각 요소도 새롭게 복사)
        List<String> deepList = new ArrayList<>();
        for (String item : originalList) {
            deepList.add(new String(item));
        }

        // 복사된 리스트 수정
        //shallowList.set(0, "Cherry");
        shallowList.remove(0);
        deepList.set(1, "Date");

        System.out.println("Original List: " + originalList); // Apple, Banana
        System.out.println("Shallow copied List: " + shallowList); // Cherry, Banana
        System.out.println("Deep copied List: " + deepList); // Apple, Date
    }
}