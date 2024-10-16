// 클래스는 오직 하나의 책임만 가져야 하며, 그 책임을 캡슐화 해야 한다.-> 클래스는 하나의 기능에만 집중해야 한다.
// 코드의 유지 보수성과 가독성을 높이고, 변경이 필요할 때 영향을 최소화 한다.
class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class UserRepository {
    public void save(User user) {
        // 사용자 정보를 DB에 저장하는 로직
        System.out.println("Saving user: " + user.getName());
    }
}

class EmailService {
    public void sendEmail(User user, String message) {
        // 이메일을 보내는 로직
        System.out.println("Sending email to " + user.getName() + ": " + message);
    }
}
public class Maino {
    public static void main(String[] args) {
        User u = new User("Java");
        System.out.println("User name: " + u.getName());
        new UserRepository().save(u);
        new EmailService().sendEmail(u, "Hello");
    }
    
    
}
