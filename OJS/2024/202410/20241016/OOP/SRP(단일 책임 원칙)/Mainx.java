
// 한 클래스에서 데이터 저장과 이메일 전송 두 가지 책임을 모두 가지고 있다. 
// (데이터 저장 로직을 수정해야 할때 이메일 전송 로직또한 수정해야 할 수도 있다)
class User { 
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void save() {
        // 사용자 정보를 DB에 저장하는 로직
        System.out.println("Saving user: " + name);
    }

    public void sendEmail(String message) {
        // 이메일을 보내는 로직
        System.out.println("Sending email to " + name + ": " + message);
    }
}

public class Mainx {
    public static void main(String[] args) {
        User u = new User("Java");
        System.out.println("User name: " + u.getName());
        u.save(); 
        u.sendEmail("Hello");
    }
}
