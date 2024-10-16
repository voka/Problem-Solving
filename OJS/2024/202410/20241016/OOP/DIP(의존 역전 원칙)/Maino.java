interface NotificationService {
    void send(String message);
}

class EmailService implements NotificationService {
    @Override
    public void send(String message) {
        // 이메일 전송 로직
        System.out.println("Sending email: " + message);
    }
}

class UserService {
    private NotificationService notificationService;

    public UserService(NotificationService notificationService) {
        this.notificationService = notificationService; // 생성자를 통한 의존성 주입
    }

    public void registerUser(String username) {
        // 사용자 등록 로직
        notificationService.send("User " + username + " registered.");
    }
}

// 사용 예
public class Maino {
    public static void main(String[] args) {
        NotificationService notificationService = new EmailService();
        UserService userService = new UserService(notificationService);
        userService.registerUser("Alice");
    }
}